package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import me.ferdz.evergreenacres.rendering.Textures;

public class TooltipLabel extends Label {

	public TooltipLabel(CharSequence text, LabelStyle style) {
		super(text, style);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Draw the background
		float contentPadding = 10;
		float x = getX() - ItemSlot.WIDTH;
		float y = getY() - 50;
		// Draw left bound
		batch.draw(Textures.getUiTextures()[3][1], x - contentPadding, y, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		// Draw the background
		float contentWidth = getPrefWidth() + contentPadding * 2;
		batch.draw(Textures.getUiTextures()[3][2], x + ItemSlot.WIDTH - contentPadding, y, contentWidth, ItemSlot.HEIGHT);
		// Draw the right bound
		batch.draw(Textures.getUiTextures()[3][3], x + ItemSlot.WIDTH - contentPadding + contentWidth, y, ItemSlot.WIDTH, ItemSlot.HEIGHT);
		
		super.draw(batch, parentAlpha);
	}
}
