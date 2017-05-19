package me.ferdz.evergreenacres.item;

import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.Utils;

public class ItemWaterCan extends Item {

	public ItemWaterCan() {
		super(Textures.ItemTexture.WATERING_CAN, "Water can");
	}

	@Override
	public void onItemUse(Player player, AbstractArea area) {
		if (area instanceof FarmArea) {
			FarmArea farmArea = (FarmArea) area;
			Vector2 position = Utils.toTilePos(player.getPosition());
			position = Utils.offsetPos(position, player.getCurrentDirection());
			
			try {
				Tile tile = farmArea.soil[(int)position.x][(int)position.y];
				if (tile instanceof SoilTile) {
					((SoilTile)tile).setWet(true);
				}
			} catch (IndexOutOfBoundsException e) {
				// Ignore
			}
		}
	}
}
