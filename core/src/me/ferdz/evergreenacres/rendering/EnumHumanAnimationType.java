package me.ferdz.evergreenacres.rendering;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

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
	HURT(6),
	/* custom animations */
	STILL_UP(1),
	STILL_LEFT(1),
	STILL_DOWN(1),
	STILL_RIGHT(1);
	
	@Getter private int length;
	private EnumHumanAnimationType(int length) {
		this.length = length;
	}
	
	public static List<EnumHumanAnimationType> getActionTypes() {
		return Arrays.asList(
				CAST_UP, 	CAST_LEFT, 	CAST_DOWN, 	CAST_RIGHT,
				THRUST_UP, 	THRUST_LEFT,THRUST_DOWN,THRUST_RIGHT,
				WALK_UP, 	WALK_LEFT, 	WALK_DOWN, 	WALK_RIGHT,
				SLASH_UP, 	SLASH_LEFT, SLASH_DOWN, SLASH_RIGHT,
				BOW_UP,		BOW_LEFT, 	BOW_DOWN, 	BOW_RIGHT,
				HURT);
	}
}
