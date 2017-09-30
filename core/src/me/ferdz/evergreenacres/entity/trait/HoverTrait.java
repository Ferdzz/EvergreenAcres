package me.ferdz.evergreenacres.entity.trait;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class HoverTrait extends AbstractTrait {
	private IHoverTraitDelegate delegate;
	
	public HoverTrait(IHoverTraitDelegate delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		Rectangle rectangle = this.delegate.getRectangle();
		if (!GameState.get().isChangingArea() && rectangle.contains(GameState.get().getCursorPosition()) && 
				GameState.get().getPlayer().getPosition().dst(rectangle.getCenter(new Vector2())) < Values.INTERACTION_DISTANCE) {
			Textures.IconTexture icon = this.delegate.getCursorIcon();
			if (icon != null) {
				Pixmap pixmap = icon.getPixmap();
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, pixmap.getWidth() / 2, pixmap.getHeight() / 2));				
				if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
					this.delegate.onInteract();
				}
			}
		}
	}
	
	public interface IHoverTraitDelegate {
		Rectangle getRectangle();
		Textures.IconTexture getCursorIcon();
		void onInteract();	
	}
}


