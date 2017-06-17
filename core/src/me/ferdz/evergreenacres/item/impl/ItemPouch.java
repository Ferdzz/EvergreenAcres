package me.ferdz.evergreenacres.item.impl;

import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures.ItemTexture;

public class ItemPouch extends Item {

	public ItemPouch(ItemTexture texture, String name) {
		super(texture, name);
	}
	
	@Override
	public void onItemUse(Player player, AbstractArea area) {
		
	}
}
