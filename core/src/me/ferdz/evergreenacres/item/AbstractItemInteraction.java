package me.ferdz.evergreenacres.item;

import com.badlogic.gdx.math.Vector2;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.SoilTile;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Utils;

public abstract class AbstractItemInteraction extends Item {

    public AbstractItemInteraction(Textures.ItemTexture texture, String name) {
        super(texture, name);
    }

    @Override
    public void onItemUse(Player player, AbstractArea area) {
        Vector2 position = Utils.toTilePos(player.getPosition());
        position = Utils.offsetPos(position, player.getCurrentDirection());
        this.onItemUsedOnWorld(player, area, position);
    }

    /**
     * Called when the player interacts with the item, given a specific world position in front of it
     */
    protected abstract void onItemUsedOnWorld(Player player, AbstractArea area, Vector2 position);
}
