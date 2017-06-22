package me.ferdz.evergreenacres.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public interface IHoverObject extends IUpdatable {
	default public void update(float delta) {
		if (!GameState.get().isChangingArea() && getRectangle().contains(GameState.get().getCursorPosition()) && 
				GameState.get().getPlayer().getPosition().dst(getRectangle().getCenter(new Vector2())) < Values.INTERACTION_DISTANCE) {
			Textures.IconTexture icon = getCursorIcon();
			if (icon != null) {
				Pixmap pixmap = icon.getPixmap();
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2));				
				if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
					this.onInteract();
				}
			}
		}
	}
	
	public void onInteract();
	public Rectangle getRectangle();
	Textures.IconTexture getCursorIcon();
}
