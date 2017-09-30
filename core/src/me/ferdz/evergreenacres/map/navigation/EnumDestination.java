package me.ferdz.evergreenacres.map.navigation;

import lombok.Getter;
import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.map.HouseArea;

import java.util.Arrays;

public enum EnumDestination {
	HOUSE("house", HouseArea.class, 88, 56, EnumDirection.UP),
	FARM_SPAWN("farm_spawn", FarmArea.class, 30, 100, EnumDirection.DOWN),
	FARM_01("farm_01", FarmArea.class, 48, 328, EnumDirection.DOWN);
	
	@Getter private String key;
	@Getter private Class<? extends AbstractArea> area;
	@Getter private int x, y;
	@Getter private EnumDirection direction;
	EnumDestination(String key, Class<? extends AbstractArea> area, int x, int y, EnumDirection direction) {
		this.key = key;
		this.area = area;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public static EnumDestination getDestination(String key) {
		return Arrays.stream(EnumDestination.values())
				.filter(destination -> destination.key.equals(key))
				.findFirst()
				.orElse(null);
	}
}
