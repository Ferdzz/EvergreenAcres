package me.ferdz.evergreenacres.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

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
