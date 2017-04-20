package me.ferdz.evergreenacres.core.rendering;

public enum EnumHumanAnimationType {
	
	CAST_UP(7),
	CAST_LEFT(7),
	CAST_DOWN(7),
	CAST_RIGHT(7),
	THRUST_UP(8),
	THRUST_LEFT(8),
	THRUST_DOWN(8),
	THRUST_RIGHT(8),
	WALK_UP(8),
	WALK_LEFT(9),
	WALK_DOWN(8),
	WALK_RIGHT(9),
	SLASH_UP(6),
	SLASH_LEFT(6),
	SLASH_DOWN(6),
	SLASH_RIGHT(6),
	BOW_UP(13),
	BOW_LEFT(13),
	BOW_DOWN(13),
	BOW_RIGHT(13),
	HURT(6);
	
	private int length;
	private EnumHumanAnimationType(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
}
