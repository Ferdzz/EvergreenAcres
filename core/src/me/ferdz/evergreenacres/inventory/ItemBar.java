package me.ferdz.evergreenacres.inventory;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.common.eventbus.Subscribe;

import lombok.Getter;
import lombok.ToString;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.item.ItemStack;
import me.ferdz.evergreenacres.item.Items;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.Values;
import me.ferdz.evergreenacres.utils.input.InputEvents;

@ToString
public class ItemBar extends Table implements IItemHolder {
	public static final int ITEMS_COUNT = 9;
	
	@Getter private int selectedIndex;

	public ItemBar() {
		super();
		this.selectedIndex = 0;
		this.setClip(false);
		
		for (int i = 0; i < ITEMS_COUNT; i++) {
			// TODO: This is temporary code to fill in the itemstacks
			if (i == 0) {
				this.add(new ItemSlot(new ItemStack(Items.HOE)));							
			} else if (i == 1) {
				this.add(new ItemSlot(new ItemStack(Items.WATER_CAN)));
			} else if (i == 2) {
				this.add(new ItemSlot(new ItemStack(Items.POTATO_POUCH)));
			} else if (i == 3) {
				this.add(new ItemSlot(new ItemStack(Items.AXE)));
			} else {
				this.add(new ItemSlot());
			}
		}

		Values.bus.register(this);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color previousColor = batch.getColor();
		batch.setColor(this.getColor());
		
		TextureRegion[][] textures = Textures.getUiTextures();
		batch.draw(textures[1][3], getX() - ItemSlot.HEIGHT, getY(), ItemSlot.WIDTH, ItemSlot.HEIGHT);
		for (int i = 0; i < ITEMS_COUNT; i++) {
			// Upper bound
			batch.draw(textures[0][4], getX() + (i * ItemSlot.WIDTH), getY() + ItemSlot.HEIGHT * 1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
			// Lower bound
			batch.draw(textures[2][4], getX() + (i * ItemSlot.WIDTH), getY() + ItemSlot.HEIGHT * -1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		}
		// Selected content
		batch.draw(textures[1][1], getX() + (selectedIndex * ItemSlot.WIDTH), getY(), ItemSlot.WIDTH, ItemSlot.HEIGHT);

		// Render the slots and contents
		super.draw(batch, parentAlpha);
		
		// Render the selected graphics
		// Selected upper bound
		batch.draw(textures[0][0], getX() + ((selectedIndex - 1) * ItemSlot.WIDTH),  getY() + ItemSlot.HEIGHT * 1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		batch.draw(textures[0][1], getX() + (selectedIndex * ItemSlot.WIDTH),  getY() + ItemSlot.HEIGHT * 1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		batch.draw(textures[0][2], getX() + ((selectedIndex + 1) * ItemSlot.WIDTH),  getY() + ItemSlot.HEIGHT * 1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		// Selected lower bound
		batch.draw(textures[2][0], getX() + ((selectedIndex - 1) * ItemSlot.WIDTH), getY() + ItemSlot.HEIGHT * -1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		batch.draw(textures[2][1], getX() + (selectedIndex * ItemSlot.WIDTH), getY() + ItemSlot.HEIGHT * -1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		batch.draw(textures[2][2], getX() + ((selectedIndex + 1) * ItemSlot.WIDTH), getY() + ItemSlot.HEIGHT * -1, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		// Selected left bound
		batch.draw(textures[1][0], getX() + ((selectedIndex - 1) * ItemSlot.WIDTH), getY(), ItemSlot.WIDTH, ItemSlot.HEIGHT);
		// Selected right bound
		batch.draw(textures[1][2], getX() + ((selectedIndex + 1) * ItemSlot.WIDTH), getY(), ItemSlot.WIDTH, ItemSlot.HEIGHT);
		
		batch.setColor(previousColor);
	}
	
	@Subscribe
	public void onScroll(InputEvents.ScrollEvent event) {
		int destinationIndex = this.selectedIndex - event.getAmount();
		if (destinationIndex < 0) {
			destinationIndex = ITEMS_COUNT - 1;
		} else if (destinationIndex > ITEMS_COUNT - 1) {
			destinationIndex = 0;
		}
		this.selectedIndex = destinationIndex;
	}
	
	@Subscribe
	public void onKeyPressed(InputEvents.KeyPressedEvent event) {
		Integer value = Values.KEYS_NUMBER.get(event.getKey());
		if (value != null) {
			this.selectedIndex = value - 1;
		}
	}
	
	public Item getSelectedItem() {
		Actor actor = this.getChildren().items[this.selectedIndex];
		if (actor instanceof ItemSlot) {
			ItemSlot slot = (ItemSlot) actor;
			return slot.getItem();
		}
		return null;
	}
	
	@Override
	public List<ItemSlot> getItemSlots() {
		List<ItemSlot> itemSlots = new ArrayList<>();
		for (Actor actor : this.getChildren()) {
			if (actor instanceof ItemSlot) {
				itemSlots.add((ItemSlot) actor);
			}
		}
		return itemSlots;
	}
}
