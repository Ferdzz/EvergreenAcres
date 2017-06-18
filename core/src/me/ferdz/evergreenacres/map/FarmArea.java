package me.ferdz.evergreenacres.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.utils.GameState;

public class FarmArea extends AbstractArea {
	
	public FarmArea(Player player) {
		super(player);
		// possibly initialize interactions, ai, dialogs etc
	}
	
	@Override
	public void render(SpriteBatch batch) {		
		for (int i = 0; i < GameState.get().getSoil().length; i++) {
			Tile[] row = GameState.get().getSoil()[i];
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
