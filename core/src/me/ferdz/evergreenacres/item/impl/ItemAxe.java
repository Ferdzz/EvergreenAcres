package me.ferdz.evergreenacres.item.impl;

import com.badlogic.gdx.math.Vector2;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.item.AbstractItemInteraction;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.rendering.Textures;

public class ItemAxe extends AbstractItemInteraction{

    public ItemAxe() {
        super(Textures.ItemTexture.HOE, "Axe");
    }

    @Override
    protected void onItemUsedOnWorld(Player player, AbstractArea area, Vector2 position) {
        System.out.println("axed the ground");
    }
}
