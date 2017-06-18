package me.ferdz.evergreenacres.item.impl;

import com.badlogic.gdx.math.Vector2;

import lombok.Getter;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.environment.Crop;
import me.ferdz.evergreenacres.environment.EnumCrop;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.rendering.Textures.ItemTexture;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Utils;

public class ItemPouch extends Item {

	@Getter private EnumCrop cropType;
	public ItemPouch(ItemTexture texture, EnumCrop cropType, String name) {
		super(texture, name);
		this.cropType = cropType;
	}
	
	@Override
	public void onItemUse(Player player, AbstractArea area) {
		if (area instanceof FarmArea) {
			Vector2 position = Utils.toTilePos(player.getPosition());
			position = Utils.offsetPos(position, player.getCurrentDirection());
			
			try {
				Tile tile = GameState.get().getSoil()[(int)position.x][(int)position.y];
				if (tile instanceof SoilTile) {
					((SoilTile)tile).setCrop(new Crop(cropType));
				}
			} catch (IndexOutOfBoundsException e) {
				// Ignore
			}
		}
	}
}
