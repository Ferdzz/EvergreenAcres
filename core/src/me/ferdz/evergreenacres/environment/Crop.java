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
	
	public void grow() {
		this.growth += cropType.getGrowthMultiplier();
	}
	
	public TextureRegion getCurrentTexture() {
		int index = (int) Math.floor(this.growth);
		if (index < cropType.getTextures().length) {
			return cropType.getTextures()[index].getTexture();			
		} else {
			return cropType.getTextures()[cropType.getTextures().length - 1].getTexture();
		}
	}
}
