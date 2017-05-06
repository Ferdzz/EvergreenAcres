package me.ferdz.evergreenacres.core.entity.impl.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum EnumSoilTexture {
	NO_CONNECTION(3, 3),
	DOWN(3, 0),
	UP_DOWN(3, 1),
	UP(3, 2),
	RIGHT(0, 3),
	LEFT_RIGHT(1, 3),
	LEFT(2, 3),
	DOWN_RIGHT(0, 0),
	LEFT_DOWN_RIGHT(0, 1),
	LEFT_DOWN(0, 2),
	UP_DOWN_RIGHT(1, 0),
	UP_LEFT_DOWN_RIGHT(1, 1),
	UP_LEFT_DOWN(1, 2),
	UP_RIGHT(2, 0),
	UP_LEFT_RIGHT(2, 1),
	UP_LEFT(2, 2);
	
	private int x, y;
	private EnumSoilTexture(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public TextureRegion getTextureRegion() {
		return EnumSoilTexture.getSoilTexture()[y][x];
	}
	
	private static TextureRegion[][] soilTexture;
	private static TextureRegion[][] getSoilTexture() {
		if (soilTexture == null)
			soilTexture = TextureRegion.split(new Texture(Gdx.files.internal("environment/homegrown/soil.png")), 16, 16);
		return soilTexture;
	}
}
