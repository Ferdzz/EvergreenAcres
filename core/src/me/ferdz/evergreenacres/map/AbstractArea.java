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

import lombok.Getter;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.IRenderable;
import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.object.BedObject;
import me.ferdz.evergreenacres.entity.impl.object.DoorObject;
import me.ferdz.evergreenacres.entity.impl.object.TreeObject;
import me.ferdz.evergreenacres.entity.impl.object.WarpObject;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.utils.MapBodyBuilder;
import me.ferdz.evergreenacres.utils.Values;

public abstract class AbstractArea implements Disposable, IRenderable, IUpdatable {
	protected TiledMap map;
	@Getter protected World world;
	protected Player player;
	@Getter protected List<AbstractEntity> entities;
	
	public AbstractArea(Player player) {
		this.player = player;
		this.world = new World(Vector2.Zero, true);
		
		this.entities = new ArrayList<AbstractEntity>();
		
		// Load objects in map
		MapBodyBuilder.buildShapes(getMap(), 1, world); // load shapes into world
		
		// Load interactive map objects as entities
		// Look for door type objects
		for (MapObject mapObject: getMap().getLayers().get("Objects").getObjects()) {
			String type = (String) mapObject.getProperties().get(Values.KEY_TYPE);
			if (type == null) {
				continue;
			}

			switch (type) {
				case Values.TYPE_DOOR:
					if (mapObject instanceof RectangleMapObject) {
						RectangleMapObject rectangle = (RectangleMapObject) mapObject;
						this.entities.add(new DoorObject(rectangle.getRectangle(), mapObject.getProperties()));
					}
					break;
				case Values.TYPE_WARP:
					if (mapObject instanceof RectangleMapObject) {
						RectangleMapObject rectangle = (RectangleMapObject) mapObject;
						this.entities.add(new WarpObject(rectangle.getRectangle(), mapObject.getProperties()));
					}
					break;
				case Values.TYPE_BED:
					if (mapObject instanceof RectangleMapObject) {
						RectangleMapObject rectangle = (RectangleMapObject) mapObject;
						this.entities.add(new BedObject(rectangle.getRectangle()));
					}
					break;
				case Values.TYPE_TREE:
					if (mapObject instanceof RectangleMapObject) {
						RectangleMapObject rectangle = (RectangleMapObject) mapObject;
						this.entities.add(new TreeObject(this, rectangle.getRectangle()));
					}
					break;
				default:
					System.out.println("Unknown object type in 'Objects' layer");
					break;
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
		for (AbstractEntity entity : getEntities()) {
			entity.render(batch);
		}

		player.render(batch);
	}
	
	public void renderOver(SpriteBatch batch) {
		// Override this is the area has specifics that needs to be rendered over entities
	}
	
	public void teleportPlayer(EnumDestination destination) {
		// Bind player to this area
		this.player.createBody(world, new Vector2(destination.getX(), destination.getY()));
	}
	
	/**
	 * Returns the area's map using lazy-loading
	 */
	public abstract TiledMap getMap();
}
