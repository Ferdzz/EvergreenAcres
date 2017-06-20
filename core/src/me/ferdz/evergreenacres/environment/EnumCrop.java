package me.ferdz.evergreenacres.environment;

import lombok.Getter;
import me.ferdz.evergreenacres.rendering.Textures.CropTexture;

public enum EnumCrop {
	POTATO(1, CropTexture.POTATO_6, CropTexture.POTATO_1, CropTexture.POTATO_2, CropTexture.POTATO_3, CropTexture.POTATO_4, CropTexture.POTATO_5, CropTexture.POTATO_6);
	
	@Getter final private float growthMultiplier;
	@Getter final private CropTexture[] textures;
	// TODO: Make crop depend on season
	private EnumCrop(float growthMultiplier, CropTexture... textures) {
		this.growthMultiplier = growthMultiplier;
		this.textures = textures;
	}
}
