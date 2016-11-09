package me.ferdz.evergreenacres.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.Player;

public class GameScreen extends ScreenAdapter implements IUpdatable {
	
	private TiledMap map;
	private int[] underLayers;
	private ObjectTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Player player;
	
	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/farm.tmx");
		batch = new SpriteBatch();
		mapRenderer = new ObjectTiledMapRenderer(map, batch);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 720, 405); // 16:9
		camera.update();

		player = new Player();
		player.setPosition(new Vector2(300, 1300));
	}

	@Override
	public void update(float delta) {
		player.update(delta);
		
		camera.position.x = player.getPosition().x;
		camera.position.y = player.getPosition().y;
		camera.update();		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);
		
		mapRenderer.render(); // start map rendering
			// render entities
		player.render(batch);
		
		mapRenderer.renderOver(); // end map rendering
	}

	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
		mapRenderer.dispose();
		map.dispose();
	}
}
