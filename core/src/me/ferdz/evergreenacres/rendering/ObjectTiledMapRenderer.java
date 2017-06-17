package me.ferdz.evergreenacres.rendering;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceActionImpl;

import lombok.Setter;

public class ObjectTiledMapRenderer extends OrthogonalTiledMapRenderer {

	@Setter private List<MapLayer> underLayers, overLayers;
	@Setter private Action action;
	
	public ObjectTiledMapRenderer(TiledMap map, Batch batch) {
		super(map, batch);
		this.updateMap(map);
			// Below code to randomize the lenght of the animations
//			if (m instanceof TiledMapTileLayer) {
//				TiledMapTileLayer layer = (TiledMapTileLayer) m;
//				for (int i = 0; i < layer.getWidth(); i++) {
//					for (int j = 0; j < layer.getHeight(); j++) {
//						Cell cell = layer.getCell(i, j);
//						if (cell != null) {
//							if (cell.getTile() instanceof AnimatedTiledMapTile) {
//								AnimatedTiledMapTile tile = (AnimatedTiledMapTile) cell.getTile();
////								System.out.println(tile.getAnimationIntervals()[0]);
//								IntArray animationIntervals = new IntArray();
//								for (int k = 0; k < tile.getAnimationIntervals().length; k++) {
//									animationIntervals.add((int) (ThreadLocalRandom.current().nextFloat() * (1200 - 800) + 800));
//								}
//								AnimatedTiledMapTile newTile = new AnimatedTiledMapTile(
//										animationIntervals, 
//										new Array(tile.getFrameTiles()));
////								newTile.getAnimationIntervals()[0] = (int) (ThreadLocalRandom.current().nextFloat() * 1000);
//								cell.setTile(newTile);
//							}
//						}
//					}
//				}
//			}
	}
	
	public void updateMap(TiledMap map) {
		
		underLayers = new ArrayList<MapLayer>();
		overLayers = new ArrayList<MapLayer>();
		
		for (MapLayer m : map.getLayers()) {
			if(m.getName().startsWith("Over")) {
				overLayers.add(m);
			} else {
				underLayers.add(m);
			}
		}
	}
	
	@Override
	public void render() {
		if (this.action != null) {
			if (action.act(Gdx.graphics.getDeltaTime())) {
				action = null;
			} else {
				if (action instanceof SequenceActionImpl) {
					Action currentAction = ((SequenceActionImpl) action).getCurrentAction();
					if (currentAction instanceof ColorAction) {
						this.batch.setColor(((ColorAction)currentAction).getColor());
					}
				}
			}
		}
		
		beginRender();
		for (MapLayer layer : underLayers) {
			if (layer.isVisible()) {
				if (layer instanceof TiledMapTileLayer) {
					renderTileLayer((TiledMapTileLayer) layer);
				} else if (layer instanceof TiledMapImageLayer) {
					renderImageLayer((TiledMapImageLayer) layer);
				} else {
					renderObjects(layer);
				}
			}
		}
	}
	
	public void renderOver() {
		for (MapLayer m : overLayers) {
			if(m != null) {
				renderTileLayer((TiledMapTileLayer) m);
			}			
		}
		endRender();
	}

	@Override
	public void renderObjects(MapLayer layer) {
		// let's not use the processor power for nothing
	}
}
