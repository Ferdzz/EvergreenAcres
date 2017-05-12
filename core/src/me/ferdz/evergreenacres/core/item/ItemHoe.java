package me.ferdz.evergreenacres.core.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.impl.Player;
import me.ferdz.evergreenacres.core.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.utils.Utils;
import me.ferdz.evergreenacres.utils.Values;

public class ItemHoe extends Item {

	public ItemHoe(Texture texture, String name) {
		super(texture, name);
	}

	@Override
	public void onItemUse(Player player, AbstractArea area) {
		if (area instanceof FarmArea) {
			FarmArea farmArea = (FarmArea) area;
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
				farmArea.soil[(int)position.x][(int)position.y] = new SoilTile(position);
			}
		}
	}

	@Override
	public void renderInInventory(SpriteBatch batch, int x, int y, float scale) {
		
	}
}
