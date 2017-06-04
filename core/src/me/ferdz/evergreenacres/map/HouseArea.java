package me.ferdz.evergreenacres.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import me.ferdz.evergreenacres.entity.impl.Player;

public class HouseArea extends AbstractArea {

	public HouseArea(Player player) {
		super(player, 56, 40);
	}

	@Override
	public TiledMap getMap() {
		if (map == null) 
			map = new TmxMapLoader().load("maps/house.tmx");
		return map;
	}

	
}
