package me.ferdz.evergreenacres.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.entity.impl.tile.Tile;

public class FarmArea extends AbstractArea {

	public Tile[][] soil;
	
	public FarmArea(Player player) {
		super(player);
		MapProperties props = getMap().getProperties();
		soil = new Tile[props.get("width", Integer.class)][props.get("height", Integer.class)];
		soil[0][0] = new Tile(new Vector2(0, 0));
		// possibly initialize interactions, ai, dialogs etc
	}
	
	@Override
	public void render(Batch batch) {		
		for (int i = 0; i < soil.length; i++) {
			Tile[] row = soil[i];
			for (int j = 0; j < row.length; j++) {
				Tile tile = row[j];
				if (tile == null)
					continue;
				
				tile.render(batch);
			}
		}
		super.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		// if there is some fancy stuff to dispose of, do it here
	}
	
	@Override
	public TiledMap getMap() {
		if(map == null)
			map = new TmxMapLoader().load("maps/farm.tmx");
		return map;
	}
}
