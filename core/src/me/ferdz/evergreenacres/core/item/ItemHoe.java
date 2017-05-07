package me.ferdz.evergreenacres.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.utils.Utils;

public class ItemHoe extends Item {

	public ItemHoe(Texture texture, String name) {
		super(texture, name);
	}

	@Override
	public void onItemUse(Player player, AbstractArea area) {
		if (area instanceof FarmArea) {
			Vector2 position = Utils.toTilePos(player.getPosition());
			position = Utils.offsetPos(position, player.getCurrentDirection());
			FarmArea farmArea = (FarmArea) area;
			farmArea.soil[(int)position.x][(int)position.y] = new SoilTile(position);
		}
	}

	@Override
	public void renderInInventory() {
		
	}
}
