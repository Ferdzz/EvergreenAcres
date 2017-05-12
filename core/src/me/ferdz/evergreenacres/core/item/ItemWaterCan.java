package me.ferdz.evergreenacres.core.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;

public class ItemWaterCan extends Item {

	public ItemWaterCan(Texture texture, String name) {
		super(texture, "Water can");
	}

	@Override
	public void renderInInventory(SpriteBatch batch, int x, int y, float scale) {
		TextureRegion region = TextureRegion.split(new Texture(Gdx.files.internal("ui/homegrown/items.png")), 16, 16)[0][0];
		batch.draw(region, x, y, scale, scale);
	}

	@Override
	public void onItemUse(Player player, AbstractArea area) {
		
	}
}
