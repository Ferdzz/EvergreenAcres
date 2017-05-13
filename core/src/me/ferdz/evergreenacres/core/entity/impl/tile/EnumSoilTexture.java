package me.ferdz.evergreenacres.core.entity.impl.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.ferdz.evergreenacres.utils.Values;

public enum EnumSoilTexture {
	NO_CONNECTION(3, 3, false, false, false, false),
	DOWN(3, 0, 			false, false, true, false),
	UP_DOWN(3, 1, 		true, false, true, false),
	UP(3, 2, 			true, false, false, false),
	RIGHT(0, 3, 		false, false, false, true),
	LEFT_RIGHT(1, 3, 	false, true, false, true),
	LEFT(2, 3,			false, true, false, false),
	DOWN_RIGHT(0, 0,	false, false, true, true),
	LEFT_DOWN_RIGHT(1, 0,false, true, true, true),
	LEFT_DOWN(2, 0,		false, true, true, false),
	UP_DOWN_RIGHT(0, 1,	true, false, true, true),
	UP_LEFT_DOWN_RIGHT(1, 1, true, true, true, true),
	UP_LEFT_DOWN(2, 1, 	true, true, true, false),
	UP_RIGHT(0, 2,		true, false, false, true),
	UP_LEFT_RIGHT(1, 2, true, true, false, true),
	UP_LEFT(2, 2,		true, true, false, false);
	
	private int x, y;
	public boolean up, left, down, right;
	private EnumSoilTexture(int x, int y, boolean up, boolean left, boolean down, boolean right) {
		this.x = x;
		this.y = y;
		this.up = up;
		this.left = left;
		this.down = down;
		this.right = right;
	}
	
	public TextureRegion getTextureRegion(boolean isWet) {
		return EnumSoilTexture.getSoilTexture()[isWet? y + 4: y][x];
	}
	
	public static TextureRegion getConnectedTextureRegion(boolean up, boolean left, boolean down, boolean right, boolean isWet) {
		for (EnumSoilTexture texture : EnumSoilTexture.values()) {
			if (texture.up == up && texture.left == left && texture.down == down && texture.right == right) {
				return texture.getTextureRegion(isWet);
			}
		}
		throw new RuntimeException("No connected texture found for this configurations: " + up + ", " + left + ", " + down + ", " + right);
	}
	
	private static TextureRegion[][] soilTexture;
	private static TextureRegion[][] getSoilTexture() {
		if (soilTexture == null)
			soilTexture = TextureRegion.split(new Texture(Gdx.files.internal("homegrown/environment/soil.png")), Values.TILE_WIDTH, Values.TILE_HEIGHT);
		return soilTexture;
	}
}
