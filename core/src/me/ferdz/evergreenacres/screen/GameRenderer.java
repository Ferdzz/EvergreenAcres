package me.ferdz.evergreenacres.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Queue;

import lombok.Getter;
import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.inventory.ItemBar;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.ObjectTiledMapRenderer;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

/**
 * Responsible for rendering the different objects in the map
 * Takes care of the Y axis dependent rendering
 */
public class GameRenderer implements Disposable, IUpdatable {
	@Getter private ObjectTiledMapRenderer mapRenderer;
	@Getter private OrthographicCamera camera;
	private Queue<Runnable> renderQueue;
	private SpriteBatch batch;
	private Stage stage;
	@SuppressWarnings("FieldCanBeLocal")
	private Table table;
	private SpriteBatch uiBatch;
	@SuppressWarnings({"unused", "FieldCanBeLocal"})
	private Box2DDebugRenderer debugRenderer;

	public GameRenderer() {
		this.batch = new SpriteBatch();
		this.batch.setColor(Color.WHITE);
		this.uiBatch = new SpriteBatch();
		this.mapRenderer = new ObjectTiledMapRenderer(GameState.get().getCurrentArea().getMap(), batch);
		this.camera = new OrthographicCamera();
		this.camera.zoom = Values.ZOOM;
		this.debugRenderer = new Box2DDebugRenderer();
		
		this.stage = new Stage();
		this.table = new Table();
		this.table.setFillParent(true);
		this.stage.addActor(table);
		this.table.add(GameState.get().getInventoryManager().getItemBar());
		this.table.add(GameState.get().getPauseMenu());
		this.table.bottom().padBottom(40);
//		this.table.setDebug(true);
		this.renderQueue = new Queue<>();
		
		// Set the stage as an input processor
		Values.multiplexer.addProcessor(this.stage);
	}
	
	@Override
	public void update(float delta) {		
		// Update the current cursor position
		GameState.get().update(delta);
		Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
		// Update the tile entities
		for (int i = 0; i < GameState.get().getSoil().length; i++) {
			Tile[] row = GameState.get().getSoil()[i];
			for (Tile tile : row) {
				if (tile != null) {
					tile.update(delta);
				}
			}
		}
		
		AbstractArea currentArea = GameState.get().getCurrentArea();
		currentArea.update(delta);
		Player player = GameState.get().getPlayer();
		player.update(delta);
		// Update camera position
		float camXTarget = player.getPosition().x;
		float camYTarget = player.getPosition().y;
		float camX = camera.position.x;
		float camY = camera.position.y;
		float mx = (camXTarget - camX) / 1.5f; // 5F would make camera take 1sec to reach player
        float my = (camYTarget - camY) / 1.5f;
        camX += mx;
        camY += my;

        // Limit the camera bounds to the map
        MapProperties props = currentArea.getMap().getProperties();
        float viewPortWidth = (camera.viewportWidth / 2) * Values.ZOOM;
        float viewPortHeight= (camera.viewportHeight/ 2) * Values.ZOOM;
        float mapWidth = (props.get(Values.KEY_WIDTH, Integer.class) * Values.TILE_WIDTH);
        float mapHeight = (props.get(Values.KEY_HEIGHT, Integer.class) * Values.TILE_HEIGHT);
        float maxWidth = mapWidth - viewPortWidth;
        float maxHeight = mapHeight - viewPortHeight;
        if (mapWidth < camera.viewportWidth * Values.ZOOM) { // If the map is smaller than the viewport able to be displayed
        	camera.position.x = mapWidth / 2;
        } else {
            camera.position.x = MathUtils.clamp(camX, viewPortWidth, maxWidth);
        }
        if (mapHeight < camera.viewportHeight * Values.ZOOM) { // If the map is smaller than the viewport able to be displayed
        	camera.position.y = mapHeight / 2;
        } else {
        	camera.position.y = MathUtils.clamp(camY, viewPortHeight, maxHeight);        	
        }
        camera.update();
        
        // Depending on the position of the camera - manipulate the alpha of the item bar
        ItemBar itemBar = GameState.get().getInventoryManager().getItemBar();
        if (camera.position.y <= viewPortHeight + 20) {
        	if (itemBar.getColor().a == 1) {
        		itemBar.clearActions();
        		itemBar.addAction(Actions.alpha(0.7f, 0.4f));        		
        	}
        } else {
        	if (itemBar.getColor().a != 1) {
        		itemBar.clearActions();
        		itemBar.addAction(Actions.alpha(1f, 0.4f));        		
        	}
        }
        
        stage.act(delta);
	}
	
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined); // set camera views
		mapRenderer.setView(camera);
		
		mapRenderer.render(); // render under the entities
		GameState.get().getCurrentArea().render(batch); // render the entities & the player
		for (Runnable runnable : renderQueue) { // Render the delayed runnables here, on top of the player
			runnable.run();
		}
		renderQueue.clear();
		mapRenderer.renderOver(); // render over the entities
		GameState.get().getCurrentArea().renderOver(batch);
		
		debugRenderer.render(GameState.get().getCurrentArea().getWorld(), camera.combined);

		stage.draw();
		// Draw the tooltip over everything
		if (GameState.get().getTooltip() != null) {
			stage.getBatch().begin();
			GameState.get().getTooltip().draw(stage.getBatch(), 1);
			stage.getBatch().end();
		}
	}
	
	/**
	 * Queues a runnable to be executed after the rendering of the entities
	 * Used to render an object on top of the player depending on his Y position
	 */
	public void queueRender(Runnable runnable) {
		this.renderQueue.addLast(runnable);
	}

	public void resize(int width, int height) {
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		uiBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		stage.getViewport().update(width, height);		
	}

	@Override
	public void dispose() {
		batch.dispose();
		uiBatch.dispose();
		mapRenderer.dispose();		
	}
}
