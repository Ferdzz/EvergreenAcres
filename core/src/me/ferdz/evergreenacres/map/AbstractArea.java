package me.ferdz.evergreenacres.map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.IRenderable;
import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.DoorObject;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.WarpObject;
import me.ferdz.evergreenacres.utils.MapBodyBuilder;
import me.ferdz.evergreenacres.utils.Values;

public abstract class AbstractArea implements Disposable, IRenderable, IUpdatable {
	protected TiledMap map;
	protected World world;
	protected Player player;
	protected List<AbstractEntity> entities;
	protected int startX, startY;
	
	public AbstractArea(Player player, int startX, int startY) {
		this.player = player;
		this.world = new World(Vector2.Zero, true);
		this.startX = startX;
		this.startY = startY;
		
		this.entities = new ArrayList<AbstractEntity>();
		
		// Load objects in map
		MapBodyBuilder.buildShapes(getMap(), 1, world); // load shapes into world
		
		// Load interactive map objects as entities
		// Look for door type objects
		for (MapObject mapObject: getMap().getLayers().get("Objects").getObjects()) {
			if (Values.TYPE_DOOR.equals(mapObject.getProperties().get(Values.KEY_TYPE))) {
				if (mapObject instanceof RectangleMapObject) {
					RectangleMapObject rectangle = (RectangleMapObject) mapObject;
					this.entities.add(new DoorObject(rectangle.getRectangle(), mapObject.getProperties()));
				}
			} else if (Values.TYPE_WARP.equals(mapObject.getProperties().get(Values.KEY_TYPE))) {
				if (mapObject instanceof RectangleMapObject) {
					RectangleMapObject rectangle = (RectangleMapObject) mapObject;
					this.entities.add(new WarpObject(rectangle.getRectangle(), mapObject.getProperties()));
				}
			}
		}
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
	public void render(SpriteBatch batch) {
		player.render(batch);
		
		for (AbstractEntity entity : getEntities()) {
			entity.render(batch);
		}
	}
	
	public void renderOver(SpriteBatch batch) {
		// Override this is the area has specifics that needs to be rendered over entities
	}
	
	public void teleportPlayer() {
		// Bind player to this area
		this.player.createBody(world, new Vector2(startX, startY));
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
