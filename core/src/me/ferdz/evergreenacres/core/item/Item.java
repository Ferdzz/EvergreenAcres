package me.ferdz.evergreenacres.core.item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.rendering.Textures;
import me.ferdz.evergreenacres.map.AbstractArea;

public abstract class Item {
	
	private Textures.ItemTexture texture;
	private String name;
	public Item(Textures.ItemTexture texture, String name) {
		this.texture = texture;
		this.name = name;
	}
	
	// TODO: Inventories
	public void renderInInventory(Batch batch, int x, int y, float scale) {
		TextureRegion region = Textures.getTextureRegion(texture);
		batch.draw(region, x, y, scale, scale);
	}
	
	public void onItemUse(Player player, AbstractArea area) { }
}
