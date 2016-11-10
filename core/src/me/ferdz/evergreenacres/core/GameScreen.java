package me.ferdz.evergreenacres.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import me.ferdz.evergreenacres.core.entity.Player;
import me.ferdz.evergreenacres.utils.MapBodyBuilder;

public class GameScreen extends ScreenAdapter implements IUpdatable {
	
	private TiledMap map;
	private ObjectTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	private Player player;
	
	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/farm.tmx");
		batch = new SpriteBatch();
		mapRenderer = new ObjectTiledMapRenderer(map, batch);
		debugRenderer = new Box2DDebugRenderer();	
		world = new World(Vector2.Zero, true);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 720, 405); // 16:9
		camera.update();
		
		MapBodyBuilder.buildShapes(map, 1, world); // load shapes into world
		
		player = new Player(world, new Vector2(300, 1300));
	}

	@Override
	public void update(float delta) {
		world.step(1 / 30F, 8, 8);
		
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
		
		debugRenderer.setDrawInactiveBodies(true);
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
		mapRenderer.dispose();
		map.dispose();
	}
}
