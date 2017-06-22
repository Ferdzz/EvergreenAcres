package me.ferdz.evergreenacres.environment;

import lombok.Getter;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.rendering.Textures.CropTexture;

public enum EnumCrop {
	POTATO(1, Textures.IconTexture.POTATO, CropTexture.POTATO_0, CropTexture.POTATO_1, CropTexture.POTATO_2, CropTexture.POTATO_3, CropTexture.POTATO_4, CropTexture.POTATO_5, CropTexture.POTATO_6);
	
	@Getter final private float growthMultiplier;
	@Getter final private CropTexture[] textures;
	@Getter final private Textures.IconTexture icon;
	// TODO: Make crop depend on season
	private EnumCrop(float growthMultiplier, Textures.IconTexture icon, CropTexture... textures) {
		this.growthMultiplier = growthMultiplier;
		this.textures = textures;
		this.icon = icon;
	}
}
