package me.ferdz.evergreenacres.environment;

import lombok.Getter;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.item.Items;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.rendering.Textures.CropTexture;

public enum EnumCrop {
	POTATO(6, Textures.IconTexture.POTATO, 6, Items.POTATO, CropTexture.POTATO_0, CropTexture.POTATO_1, CropTexture.POTATO_2, CropTexture.POTATO_3, CropTexture.POTATO_4, CropTexture.POTATO_5, CropTexture.POTATO_6);
	
	@Getter final private float growthMultiplier;
	@Getter final private CropTexture[] textures;
	@Getter final private Textures.IconTexture icon;
	@Getter final private float ripeGrowth;
	@Getter final private Item item;

	// TODO: Make crop depend on season
	private EnumCrop(float growthMultiplier, Textures.IconTexture icon, float ripeGrowth, Item item, CropTexture... textures) {
		this.growthMultiplier = growthMultiplier;
		this.icon = icon;
		this.ripeGrowth = ripeGrowth;
		this.textures = textures;
		this.item = item;
	}
}
