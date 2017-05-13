package me.ferdz.evergreenacres.core.item;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.rendering.Textures;
import me.ferdz.evergreenacres.map.AbstractArea;

public class ItemWaterCan extends Item {

	public ItemWaterCan() {
		super(Textures.ItemTexture.WATERING_CAN, "Water can");
	}

	@Override
	public void onItemUse(Player player, AbstractArea area) {
		
	}
}
