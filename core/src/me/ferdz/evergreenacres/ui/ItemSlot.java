package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import me.ferdz.evergreenacres.core.item.Item;
import me.ferdz.evergreenacres.core.rendering.Textures;

public class ItemSlot extends Widget {
	public static final int WIDTH = 100, HEIGHT = 100;
	
	private Item item;
	
	public ItemSlot() {
		super();
	}
	
	public ItemSlot(Item item) {
		this.item = item;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Textures.getUiTextures()[1][4], getX(), getY(), WIDTH, HEIGHT);

		if (item != null) {
			item.renderInInventory(batch, (int) getX(), (int) getY(), WIDTH);
		}
	}

	@Override
	public float getPrefHeight() {
		return HEIGHT;
	}
	
	@Override
	public float getPrefWidth() {
		return WIDTH;
	}
	
	public Item getItem() {
		return item;
	}
}
