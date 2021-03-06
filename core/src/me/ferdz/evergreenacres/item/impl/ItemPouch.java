package me.ferdz.evergreenacres.item.impl;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import lombok.val;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.environment.Crop;
import me.ferdz.evergreenacres.environment.EnumCrop;
import me.ferdz.evergreenacres.item.AbstractItemInteraction;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures.ItemTexture;
import me.ferdz.evergreenacres.utils.GameState;

public class ItemPouch extends AbstractItemInteraction {

	@Getter private EnumCrop cropType;
	public ItemPouch(ItemTexture texture, EnumCrop cropType, String name) {
		super(texture, name);
		this.cropType = cropType;
	}

	@Override
	protected void onItemUsedOnWorld(Player player, AbstractArea area, Vector2 position) {
		try {
			Tile tile = GameState.get().getSoil()[(int)position.x][(int)position.y];
			if (tile instanceof SoilTile) {
				val soilTile = (SoilTile) tile;
				if (soilTile.getCrop() == null) {
					soilTile.setCrop(new Crop(cropType));
				}
			}
		} catch (IndexOutOfBoundsException e) {
			// Ignore
		}
	}
}
