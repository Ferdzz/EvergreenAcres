package me.ferdz.evergreenacres.item;

import lombok.Getter;
import lombok.Setter;

public class ItemStack {
	@Getter @Setter private int stackSize;
	@Getter final private Item item;
	
	/**
	 * Creates an instance with the Item provided and a stacksize of 0
	 */
	public ItemStack(Item item) {
		this(item, 1);
	}
	
	public ItemStack(Item item, int stackSize) {
		this.item = item;
		this.stackSize = stackSize;
	}
}
