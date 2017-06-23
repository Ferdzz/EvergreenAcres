package me.ferdz.evergreenacres.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import lombok.Getter;
import lombok.Setter;
import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.item.ItemStack;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.ui.TooltipLabel;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class ItemSlot extends Widget {
	public static final int WIDTH = 99, HEIGHT = 99;
	
	@Setter @Getter private ItemStack itemStack;
	
	public ItemSlot() {
		this(null);
	}
	
	public ItemSlot(ItemStack itemStack) {
		super();
		this.itemStack = itemStack;
		
		this.addListener(new SlotInputListener());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Textures.getUiTextures()[1][4], getX(), getY(), WIDTH, HEIGHT);

		if (itemStack != null) {
			itemStack.getItem().renderInInventory(batch, (int) getX(), (int) getY(), WIDTH);
			if (itemStack.getStackSize() > 1) {
				Values.tooltipFont.draw(batch, itemStack.getStackSize() + "", getX() + (WIDTH / 16) * 2, getY() + (HEIGHT / 16) * 3);
			}
		}
	}

	@Override
	public float getPrefHeight() {
		return HEIGHT;
	}
	
	@Override
	public float getPrefWidth() {
		return WIDTH;
	}
	
	public Item getItem() {
		if (itemStack != null){
			return itemStack.getItem();			
		}
		return null;
	}
	
	private class SlotInputListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		}
		
		@Override
		public boolean mouseMoved(InputEvent event, float x, float y) {
			if (itemStack != null) {
				TooltipLabel tooltip = GameState.get().getTooltip();
				if (tooltip == null) {
					LabelStyle style = new LabelStyle(Values.tooltipFont, Color.WHITE);
					tooltip = new TooltipLabel(itemStack.getItem().getName(), style);
					GameState.get().setTooltip(tooltip);
				}
				tooltip.setPosition(event.getStageX() - (tooltip.getPrefWidth() / 2), event.getStageY() + 20);
			}
			return true;
		}
		
		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			if (pointer != -1)
				return;
			
			GameState.get().setTooltip(null);
		}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			GameState.get().setTooltip(null);
			
			return true;
		}
	}
}
