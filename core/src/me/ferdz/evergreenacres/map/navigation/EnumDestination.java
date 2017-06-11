package me.ferdz.evergreenacres.map.navigation;

import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.map.HouseArea;

public enum EnumDestination {
	HOUSE("house", HouseArea.class, 88, 56, EnumDirection.UP),
	FARM_SPAWN("farm_spawn", FarmArea.class, 30, 100, EnumDirection.DOWN),
	FARM_01("farm_01", FarmArea.class, 48, 328, EnumDirection.DOWN);
	
	private String key;
	private Class<? extends AbstractArea> area;
	private int x, y;
	private EnumDirection direction;
	EnumDestination(String key, Class<? extends AbstractArea> area, int x, int y, EnumDirection direction) {
		this.key = key;
		this.area = area;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Class<? extends AbstractArea> getArea() {
		return area;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public EnumDirection getDirection() {
		return direction;
	}
	
	public static EnumDestination getDestination(String key) {
		for (EnumDestination destination: EnumDestination.values()) {
			if (destination.key.equals(key)) {
				return destination;
			}
		}
		return null;
	}
}
