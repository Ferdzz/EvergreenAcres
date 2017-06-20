package me.ferdz.evergreenacres.environment;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.ferdz.evergreenacres.rendering.Textures;

@ToString
public class Crop {
	@Getter private final EnumCrop cropType;
	@Getter private float growth;
	@Setter @Getter private boolean isDead;
	
	public Crop(EnumCrop cropType) {
		this(cropType, 0);
	}
	
	public Crop(EnumCrop cropType, float growth) {
		this.cropType = cropType;
		this.growth = growth;
	}
	
	public void grow(boolean isWet) {
		if (isWet && !isDead) {
			this.growth += cropType.getGrowthMultiplier();			
		} else {
			this.isDead = true;
		}
	}
	
	public TextureRegion getCurrentTexture(Random random) {
		if (!isDead) {
			int index = (int) Math.floor(this.growth);
			if (index < cropType.getTextures().length) {
				return cropType.getTextures()[index].getTexture();			
			} else {
				return cropType.getTextures()[cropType.getTextures().length - 1].getTexture();
			}
		} else {
			int i = random.nextInt(3);
			return Textures.getCropTextures()[1][i];
		}
	}
}
