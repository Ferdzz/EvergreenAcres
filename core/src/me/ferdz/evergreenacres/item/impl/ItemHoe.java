package me.ferdz.evergreenacres.item.impl;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Utils;
import me.ferdz.evergreenacres.utils.Values;

public class ItemHoe extends Item {

	public ItemHoe() {
		super(Textures.ItemTexture.HOE, "Hoe");
	}

	@Override
	public void onItemUse(Player player, AbstractArea area) {
		if (area instanceof FarmArea) {
			Vector2 position = Utils.toTilePos(player.getPosition());
			position = Utils.offsetPos(position, player.getCurrentDirection());
			TiledMapTileLayer layer = (TiledMapTileLayer) area.getMap().getLayers().get(Values.LAYER_GROUND);
			Cell cell = layer.getCell((int)position.x, (int)position.y);
			if (cell != null) {
				String type = (String) cell.getTile().getProperties().get(Values.KEY_TYPE);
				// Only add the tile if the ground is dirt
				if (!Values.TYPE_DIRT.equals(type)) {
					return;
				}
				GameState.get().getSoil()[(int)position.x][(int)position.y] = new SoilTile(position);
			}
		}
	}
}
