package me.ferdz.evergreenacres.item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.Getter;
import lombok.ToString;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures;

@ToString
public class Item {
	
	@Getter final private Textures.ItemTexture texture;
	@Getter private String name;
	
	public Item(Textures.ItemTexture texture, String name) {
		this.texture = texture;
		this.name = name;
	}
	
	// TODO: Inventories
	public void renderInInventory(Batch batch, int x, int y, float scale) {
		TextureRegion region = texture.getTexture();
		batch.draw(region, x, y, scale, scale);
	}
	
	public void onItemUse(Player player, AbstractArea area) { }
}
