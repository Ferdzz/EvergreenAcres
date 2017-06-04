package me.ferdz.evergreenacres.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.rendering.ObjectTiledMapRenderer;
import me.ferdz.evergreenacres.ui.ItemBar;
import me.ferdz.evergreenacres.ui.TooltipLabel;
import me.ferdz.evergreenacres.utils.Utils;
import me.ferdz.evergreenacres.utils.Values;

public class GameScreen extends ScreenAdapter implements IUpdatable {
	
	public static final float ZOOM = 0.25F;
	
	public static GameScreen instance;
	
	private ObjectTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private SpriteBatch uiBatch;
	private Box2DDebugRenderer debugRenderer;
	private Player player;
	private AbstractArea currentArea;
	private Stage stage;
	private Table table;
	private ItemBar itemBar;
	private TooltipLabel tooltip;
	private Vector2 cursorPosition;
	
	@Override
	public void show() {
		instance = this;
		this.player = new Player();
		this.batch = new SpriteBatch();
		this.uiBatch = new SpriteBatch();
		this.currentArea = new FarmArea(player);
		this.currentArea.teleportPlayer();
		this.mapRenderer = new ObjectTiledMapRenderer(currentArea.getMap(), batch);
		this.debugRenderer = new Box2DDebugRenderer();	
		this.camera = new OrthographicCamera();
		this.camera.zoom = ZOOM;
		
		this.stage = new Stage();
		this.table = new Table();
		this.table.setFillParent(true);
		this.stage.addActor(table);
		this.itemBar = new ItemBar();
		this.table.add(this.itemBar);
		this.table.bottom().padBottom(40);
//		this.table.setDebug(true);
		
		// Set the stage as an input processor
		Values.multiplexer.addProcessor(this.stage);
	}

	@Override
	public void update(float delta) {
		// Update the current cursor position
		cursorPosition = Utils.cursorToWorldPos();
		Gdx.graphics.setSystemCursor(SystemCursor.Arrow);

		currentArea.update(delta);	
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
        if (camera.position.y <= viewPortHeight + 20) {
        	if (itemBar.getColor().a == 1) {
        		this.itemBar.clearActions();
        		this.itemBar.addAction(Actions.alpha(0.7f, 0.4f));        		
        	}
        } else {
        	if (itemBar.getColor().a != 1) {
        		this.itemBar.clearActions();
        		this.itemBar.addAction(Actions.alpha(1f, 0.4f));        		
        	}
        }
        
        stage.act(delta);
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(Values.BACKGROUND_COLOR.r, 
							Values.BACKGROUND_COLOR.g, 
							Values.BACKGROUND_COLOR.b,
							Values.BACKGROUND_COLOR.a);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined); // set camera views
		mapRenderer.setView(camera);
		
		mapRenderer.render(); // render under the entities
		currentArea.render(batch); // render the entities
		mapRenderer.renderOver(); // render over the entities

		debugRenderer.render(currentArea.getWorld(), camera.combined);

		stage.draw();
		// Draw the tooltip over everything
		if (tooltip != null) {
			stage.getBatch().begin();
			tooltip.draw(stage.getBatch(), 1);
			stage.getBatch().end();
		}
	}

	public void changeArea(AbstractArea area) {
		if (currentArea != null)
			currentArea.dispose();
		
//		inTransition = true;
//		mapRenderer.setAction(Actions.color(Color.CLEAR, 1));
//		destinationArea = area;
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
		player.dispose();
		batch.dispose();
		uiBatch.dispose();
		
		mapRenderer.dispose();
		currentArea.dispose();
	}
	
	public AbstractArea getCurrentArea() {
		return currentArea;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public ItemBar getItemBar() {
		return itemBar;
	}
	
	public TooltipLabel getTooltip() {
		return tooltip;
	}
	
	public void setTooltip(TooltipLabel tooltip) {
		this.tooltip = tooltip;
	}
	
	public Vector2 getCursorPosition() {
		return cursorPosition;
	}
	
	public Player getPlayer() {
		return player;
	}
}
