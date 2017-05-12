package me.ferdz.evergreenacres.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;

public abstract class Item {
	
	private Texture texture;
	private String name;
	public Item(Texture texture, String name) {
		this.texture = texture;
		this.name = name;
	}
	
	// TODO: Inventories
	public abstract void renderInInventory(SpriteBatch batch, int x, int y, float scale);
	
	public void onItemUse(Player player, AbstractArea area) { }
}
