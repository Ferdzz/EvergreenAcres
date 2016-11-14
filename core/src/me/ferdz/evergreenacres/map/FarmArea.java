package me.ferdz.evergreenacres.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import me.ferdz.evergreenacres.core.entity.impl.Player;

public class FarmArea extends AbstractArea {

	public FarmArea(Player player) {
		super(player);
		// possibly initialize interactions, ai, dialogs etc
	}

	@Override
	public void dispose() {
		super.dispose();
		// if there is some fancy stuff to dispose of, do it here
	}
	
	@Override
	public TiledMap getMap() {
		if(map != null)
			map = new TmxMapLoader().load("maps/farm.tmx");
		return map;
	}
}
