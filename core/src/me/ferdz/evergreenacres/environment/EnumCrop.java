package me.ferdz.evergreenacres.environment;

import lombok.Getter;
import me.ferdz.evergreenacres.rendering.Textures.ItemTexture;

public enum EnumCrop {
	POTATO(1, ItemTexture.POTATO_GROWTH_0);
	
	@Getter final private float growthMultiplier;
	@Getter final private ItemTexture[] textures;
	// TODO: Make crop depend on season
	private EnumCrop(float growthMultiplier, ItemTexture... textures) {
		this.growthMultiplier = growthMultiplier;
		this.textures = textures;
	}
}
