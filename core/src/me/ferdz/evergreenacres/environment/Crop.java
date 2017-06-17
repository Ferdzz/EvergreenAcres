package me.ferdz.evergreenacres.environment;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Crop {
	@Getter private final EnumCrop cropType;
	@Getter private float growth;
	
	public Crop(EnumCrop cropType) {
		this(cropType, 0);
	}
	
	public Crop(EnumCrop cropType, float growth) {
		this.cropType = cropType;
		this.growth = growth;
	}
	
	public TextureRegion getCurrentTexture() {
		return cropType.getTextures()[(int) Math.floor(this.growth)].getTexture();
	}
}
