package me.ferdz.evergreenacres.core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import me.ferdz.evergreenacres.core.entity.IUpdatable;
import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.rendering.ObjectTiledMapRenderer;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;

public class GameScreen extends ScreenAdapter implements IUpdatable {
	
	public static GameScreen instance;
	
	private ObjectTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Box2DDebugRenderer debugRenderer;
	private Player player;
	private AbstractArea currentArea;
	
	@Override
	public void show() {
		instance = this;
		player = new Player();
		batch = new SpriteBatch();
		
		if(currentArea == null)
			changeArea(new FarmArea(player));
			
		debugRenderer = new Box2DDebugRenderer();	
		camera = new OrthographicCamera();
		camera.zoom = 0.25F;
	}

	@Override
	public void update(float delta) {
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
		camera.position.x = camX;
		camera.position.y = camY;
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
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // 16:9
		camera.update();
	}

	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
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
