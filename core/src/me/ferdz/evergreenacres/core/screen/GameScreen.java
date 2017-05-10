package me.ferdz.evergreenacres.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import me.ferdz.evergreenacres.core.entity.IUpdatable;
import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.rendering.ObjectTiledMapRenderer;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.ui.ItemBar;
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
	private ItemBar itemBar;
	@Override
	public void show() {
		instance = this;
		this.player = new Player();
		this.batch = new SpriteBatch();
		this.uiBatch = new SpriteBatch();
		this.itemBar = new ItemBar();
		
		if(this.currentArea == null)
			this.changeArea(new FarmArea(player));
			
		this.debugRenderer = new Box2DDebugRenderer();	
		this.camera = new OrthographicCamera();
		this.camera.zoom = ZOOM;
	}

	@Override
	public void update(float delta) {
		itemBar.update(delta);
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
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined); // set camera views
		mapRenderer.setView(camera);

		mapRenderer.render(); // render under the entities
		currentArea.render(batch); // render the entities
		mapRenderer.renderOver(); // render over the entities

		debugRenderer.render(currentArea.getWorld(), camera.combined);

		uiBatch.begin();
		itemBar.render(uiBatch);
		uiBatch.end();
	}

	public void changeArea(AbstractArea area) {
		if (currentArea != null)
			currentArea.dispose();

		currentArea = new FarmArea(player);
		mapRenderer = new ObjectTiledMapRenderer(currentArea.getMap(), batch);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
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
}
