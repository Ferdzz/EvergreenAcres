package me.ferdz.evergreenacres.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceActionImpl;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import me.ferdz.evergreenacres.audio.EnumSound;
import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.ObjectTiledMapRenderer;
import me.ferdz.evergreenacres.ui.ItemBar;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class GameScreen extends ScreenAdapter implements IUpdatable {
	
	public static final float ZOOM = 0.26F;
	
	public static GameScreen instance;
	
	private ObjectTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SpriteBatch uiBatch;
	private Box2DDebugRenderer debugRenderer;
	private Stage stage;
	private Table table;
	
	@Override
	public void show() {
		instance = this;
		
		this.batch = new SpriteBatch();
		this.batch.setColor(Color.WHITE);
		this.uiBatch = new SpriteBatch();
		this.mapRenderer = new ObjectTiledMapRenderer(GameState.get().getCurrentArea().getMap(), batch);
		this.debugRenderer = new Box2DDebugRenderer();	
		this.camera = new OrthographicCamera();
		this.camera.zoom = ZOOM;
		
		this.stage = new Stage();
		this.table = new Table();
		this.table.setFillParent(true);
		this.stage.addActor(table);
		this.table.add(GameState.get().getItemBar());
		this.table.bottom().padBottom(40);
//		this.table.setDebug(true);
		
		// Set the stage as an input processor
		Values.multiplexer.addProcessor(this.stage);
	}

	@Override
	public void update(float delta) {
		// Update the current cursor position
		GameState.get().update(delta);
		Gdx.graphics.setSystemCursor(SystemCursor.Arrow);

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
        float viewPortWidth = (camera.viewportWidth / 2) * ZOOM;
        float viewPortHeight= (camera.viewportHeight/ 2) * ZOOM;
        float mapWidth = (props.get(Values.KEY_WIDTH, Integer.class) * Values.TILE_WIDTH);
        float mapHeight = (props.get(Values.KEY_HEIGHT, Integer.class) * Values.TILE_HEIGHT);
        float maxWidth = mapWidth - viewPortWidth;
        float maxHeight = mapHeight - viewPortHeight;
        if (mapWidth < camera.viewportWidth * ZOOM) { // If the map is smaller than the viewport able to be displayed
        	camera.position.x = mapWidth / 2;
        } else {
            camera.position.x = MathUtils.clamp(camX, viewPortWidth, maxWidth);
        }
        if (mapHeight < camera.viewportHeight * ZOOM) { // If the map is smaller than the viewport able to be displayed
        	camera.position.y = mapHeight / 2;
        } else {
        	camera.position.y = MathUtils.clamp(camY, viewPortHeight, maxHeight);        	
        }
        camera.update();
        
        // Depending on the position of the camera - manipulate the alpha of the item bar
        ItemBar itemBar = GameState.get().getItemBar();
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
	
	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined); // set camera views
		mapRenderer.setView(camera);
		
		mapRenderer.render(); // render under the entities
		GameState.get().getCurrentArea().render(batch); // render the entities
		mapRenderer.renderOver(); // render over the entities

//		debugRenderer.render(currentArea.getWorld(), camera.combined);

		stage.draw();
		// Draw the tooltip over everything
		if (GameState.get().getTooltip() != null) {
			stage.getBatch().begin();
			GameState.get().getTooltip().draw(stage.getBatch(), 1);
			stage.getBatch().end();
		}
	}

	/**
	 * Switches the area rendered through a fadin/fadout action
	 * @param area Area to change to
	 * @param playSound Whether a close door sound should be played
	 */
	public void changeArea(AbstractArea area, boolean playSound) {
		GameState.get().setChangingArea(true);

		ColorAction startColor = new ColorAction();
		startColor.setColor(new Color(0xffffffff));
		startColor.setEndColor(new Color(0, 0, 0, 1));
		startColor.setDuration(1f);
		
		RunnableAction runAction = new RunnableAction();
		runAction.setRunnable(new Runnable() {
			@Override
			public void run() {
				AbstractArea currentArea = GameState.get().getCurrentArea();
				currentArea.dispose();
				area.teleportPlayer();
				mapRenderer.updateMap(area.getMap());
				GameState.get().setCurrentArea(area);
				if (playSound) {
					EnumSound.DOOR_CLOSE.getSound().play();
				}
				GameState.get().setChangingArea(false);
			}
		});
		ColorAction endColor = new ColorAction();
		endColor.setColor(new Color(0, 0, 0, 1));
		endColor.setEndColor(new Color(0xffffffff));
		endColor.setDuration(1f);

		mapRenderer.setAction(new SequenceActionImpl(startColor, Actions.delay(0.5f), runAction, endColor));
	}
			
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
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
		
		GameState.get().dispose();
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
