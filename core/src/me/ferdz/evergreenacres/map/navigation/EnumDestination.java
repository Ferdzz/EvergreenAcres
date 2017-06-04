package me.ferdz.evergreenacres.map.navigation;

import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.map.HouseArea;

public enum EnumDestination {
	HOUSE("house", HouseArea.class),
	FARM("farm", FarmArea.class);
	
	private String key;
	private Class<? extends AbstractArea> area;
	
	EnumDestination(String key, Class<? extends AbstractArea> area) {
		this.key = key;
		this.area = area;
	}
	
	public Class<? extends AbstractArea> getArea() {
		return area;
	}
	
	public String getKey() {
		return key;
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
