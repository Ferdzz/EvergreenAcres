package me.ferdz.evergreenacres.item;

import me.ferdz.evergreenacres.environment.EnumCrop;
import me.ferdz.evergreenacres.item.impl.ItemHoe;
import me.ferdz.evergreenacres.item.impl.ItemPouch;
import me.ferdz.evergreenacres.item.impl.ItemWaterCan;
import me.ferdz.evergreenacres.rendering.Textures.ItemTexture;

public class Items {
	public static final ItemHoe HOE = new ItemHoe();
	public static final ItemPouch POTATO_POUCH = new ItemPouch(ItemTexture.POTATO_POUCH, EnumCrop.POTATO, "Potato seeds");
	public static final ItemWaterCan WATER_CAN = new ItemWaterCan();	
	public static final Item POTATO = new Item(ItemTexture.POTATO, "Potato");
}
