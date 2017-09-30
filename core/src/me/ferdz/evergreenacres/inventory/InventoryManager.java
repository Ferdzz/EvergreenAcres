package me.ferdz.evergreenacres.inventory;

import java.util.List;

import lombok.Getter;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.item.ItemStack;

public class InventoryManager {
	@Getter private ItemBar itemBar;
	
	public InventoryManager() {
		this.itemBar = new ItemBar();
	}
	
	public void addItem(Item item) {
		this.addItemStack(new ItemStack(item, 1));
	}
	
	public void addItemStack(ItemStack itemStack) {
		boolean hadItemStack = false;
		ItemSlot firstEmptyItemSlot = null;
		for (ItemSlot itemSlot : this.getItemSlots()) {
			if (itemSlot.getItemStack() == null && firstEmptyItemSlot == null) {
				firstEmptyItemSlot = itemSlot;
				continue;
			}
			if (itemSlot.getItem() == itemStack.getItem()) {
				itemSlot.getItemStack().setStackSize(itemSlot.getItemStack().getStackSize() + itemStack.getStackSize());
				hadItemStack = true;
			}
		}
		
		if (!hadItemStack) {
			if (firstEmptyItemSlot != null) {
				firstEmptyItemSlot.setItemStack(itemStack);
			} else {
				// This means the user doesn't have any empty slots
				// TODO: drop the item
			}
		}
	}
	
	private List<ItemSlot> getItemSlots() {
		// TODO: Add itemBar itemSlots and Inventory itemSlots
		return this.itemBar.getItemSlots();
	}
}
