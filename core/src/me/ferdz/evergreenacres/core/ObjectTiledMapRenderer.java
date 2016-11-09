package me.ferdz.evergreenacres.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ObjectTiledMapRenderer extends OrthogonalTiledMapRenderer {

	public ObjectTiledMapRenderer(TiledMap map, Batch batch) {
		super(map, batch);
	}
	
	@Override
	public void render() {
		beginRender();
		for (MapLayer layer : map.getLayers()) {
			if (layer.isVisible() && !layer.getName().equalsIgnoreCase("Over")) {
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
		MapLayer layer = map.getLayers().get("Over");
		if(layer != null) {
			renderTileLayer((TiledMapTileLayer) layer);
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
