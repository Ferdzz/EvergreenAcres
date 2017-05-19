package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import me.ferdz.evergreenacres.item.Item;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.Values;

public class ItemSlot extends Widget {
	public static final int WIDTH = 100, HEIGHT = 100;
	
	private Item item;
	
	public ItemSlot() {
		this(null);
	}
	
	public ItemSlot(Item item) {
		super();
		this.item = item;
		
		this.addListener(new SlotInputListener());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(Textures.getUiTextures()[1][4], getX(), getY(), WIDTH, HEIGHT);

		if (item != null) {
			item.renderInInventory(batch, (int) getX(), (int) getY(), WIDTH);
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
		return item;
	}
	
	private class SlotInputListener extends InputListener {
		@Override
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		}
		
		@Override
		public boolean mouseMoved(InputEvent event, float x, float y) {
			if (item != null) {
				TooltipLabel tooltip = GameScreen.instance.getTooltip();
				if (tooltip == null) {
					LabelStyle style = new LabelStyle(Values.tooltipFont, Color.WHITE);
					tooltip = new TooltipLabel(item.getName(), style);
					GameScreen.instance.setTooltip(tooltip);
				}
				tooltip.setPosition(event.getStageX() - (tooltip.getPrefWidth() / 2), event.getStageY() + 20);
			}
			return true;
		}
		
		@Override
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			if (pointer != -1)
				return;
			
			GameScreen.instance.setTooltip(null);
		}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			GameScreen.instance.setTooltip(null);
			
			return true;
		}
	}
}
