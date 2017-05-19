package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.common.eventbus.Subscribe;

import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.item.ItemHoe;
import me.ferdz.evergreenacres.item.ItemWaterCan;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.Values;
import me.ferdz.evergreenacres.utils.input.InputEvents;

public class ItemBar extends Table {
	public static final int ITEMS_COUNT = 9;
	
	private int selectedIndex;

	public ItemBar() {
		super();
		
		this.selectedIndex = ITEMS_COUNT / 2;
		
		Values.bus.register(this);
		this.setClip(false);

		for (int i = 0; i < ITEMS_COUNT; i++) {
			if (i == 0) {
				this.add(new ItemSlot(new ItemHoe()));							
			} else if (i == 1) {
				this.add(new ItemSlot(new ItemWaterCan()));
			} else {
				this.add(new ItemSlot());
			}
		}
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
	
	public Item getSelectedItem() {
		Actor actor = this.getChildren().items[this.selectedIndex];
		if (actor instanceof ItemSlot) {
			ItemSlot slot = (ItemSlot) actor;
			return slot.getItem();
		}
		return null;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}
}
