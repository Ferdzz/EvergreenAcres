package me.ferdz.evergreenacres.rendering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;

public class ObjectTiledMapRenderer extends OrthogonalTiledMapRenderer {

	private List<MapLayer> underLayers, overLayers;
	
	public ObjectTiledMapRenderer(TiledMap map, Batch batch) {
		super(map, batch);

		underLayers = new ArrayList<MapLayer>();
		overLayers = new ArrayList<MapLayer>();
		
		for (MapLayer m : map.getLayers()) {
			if(m.getName().startsWith("Over")) {
				overLayers.add(m);
			} else {
				underLayers.add(m);
			}
			
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
	}
	
	@Override
	public void render() {
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
	
//	@Override
//	public void renderObject(MapObject object) {
//		if(object instanceof TiledMapTileMapObject) {
//			TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
//			batch.draw(tileObject.getTextureRegion(), tileObject.getX(), tileObject.getY());
//		}
//	}
}
