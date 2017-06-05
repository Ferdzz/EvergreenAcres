package me.ferdz.evergreenacres.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.rendering.EnumHumanAnimationType;

public class HouseArea extends AbstractArea {

	public HouseArea(Player player) {
		super(player, 88, 56);
		player.setCurrentAnimation(EnumHumanAnimationType.STILL_UP);
		player.setCurrentDirection(EnumDirection.UP);
	}

	@Override
	public TiledMap getMap() {
		if (map == null) 
			map = new TmxMapLoader().load("maps/house.tmx");
		return map;
	}

	@Override
	public void teleportPlayer() {
		super.teleportPlayer();
		this.player.setCurrentAnimation(EnumHumanAnimationType.STILL_UP);
		this.player.setCurrentDirection(EnumDirection.UP);
	}
}
