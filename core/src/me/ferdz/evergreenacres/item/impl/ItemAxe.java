package me.ferdz.evergreenacres.item.impl;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.object.TreeObject;
import me.ferdz.evergreenacres.item.AbstractItemInteraction;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.Utils;


public class ItemAxe extends AbstractItemInteraction{

    public ItemAxe() {
        super(Textures.ItemTexture.HOE, "Axe");
    }

    @Override
    protected void onItemUsedOnWorld(Player player, AbstractArea area, Vector2 position) {
        for (AbstractEntity entity: area.getEntities()) {
            if (entity instanceof TreeObject) {
                TreeObject tree = (TreeObject) entity;
                if (tree.getRectangle().contains(Utils.toWorldPos(position))) {
                    tree.onAxeHit(player, area);
                    break;
                }
            }
        }
    }
}
