package me.ferdz.evergreenacres.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.core.entity.AbstractEntity;
import me.ferdz.evergreenacres.core.entity.IRenderable;
import me.ferdz.evergreenacres.core.entity.IUpdatable;
import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.utils.MapBodyBuilder;

public abstract class AbstractArea implements Disposable, IRenderable, IUpdatable {
	protected TiledMap map;
	protected World world;
	protected Player player;
	protected List<AbstractEntity> entities;
	
	public AbstractArea(Player player) {
		this.player = player;
		this.world = new World(Vector2.Zero, true);
		
		this.entities = new ArrayList<AbstractEntity>();
		
		// Bind player to this area
		this.player.createBody(world, new Vector2(300, 1300));
		
		// Load objects in map
		MapBodyBuilder.buildShapes(getMap(), 1, world); // load shapes into world
	}
	
	@Override
	public void dispose() {
		map.dispose();
		world.dispose();
		
		for (AbstractEntity entity : getEntities()) {
			entity.dispose();
		}
	}
	
	@Override
	public void update(float delta) {
		world.step(1 / 60F, 60, 60);
		
		for (AbstractEntity entity : getEntities()) {
			entity.update(delta);
		}
	}

	@Override
	public void render(Batch batch) {
		player.render(batch);
		
		for (AbstractEntity entity : getEntities()) {
			entity.render(batch);
		}
	}
	
	public World getWorld() {
		return this.world;
	}

	public List<AbstractEntity> getEntities() {
		return entities;
	}
	
	/**
	 * Returns the area's map using lazy-loading
	 */
	public abstract TiledMap getMap();
}
