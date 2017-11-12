package me.ferdz.evergreenacres.item.impl;

import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.item.AbstractItemInteraction;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.GameState;

public class ItemWaterCan extends AbstractItemInteraction {

	public ItemWaterCan() {
		super(Textures.ItemTexture.WATERING_CAN, "Water can");
	}

	@Override
	protected void onItemUsedOnWorld(Player player, AbstractArea area, Vector2 position) {
		try {
			Tile tile = GameState.get().getSoil()[(int)position.x][(int)position.y];
			if (tile instanceof SoilTile) {
				((SoilTile)tile).setWet(true);
			}
		} catch (IndexOutOfBoundsException e) {
			// Ignore
		}
	}
}
