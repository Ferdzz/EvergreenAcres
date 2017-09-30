package me.ferdz.evergreenacres.entity.impl.object;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.audio.EnumSound;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.trait.HoverTrait;
import me.ferdz.evergreenacres.entity.trait.HoverTrait.IHoverTraitDelegate;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.rendering.Textures.IconTexture;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class DoorObject extends AbstractEntity implements IHoverTraitDelegate {

	private String destinationKey;
	private Rectangle rectangle;
	
	public DoorObject(Rectangle rectangle, MapProperties properties) {
		super();
		this.destinationKey = properties.get(Values.KEY_DESTINATION, String.class);
		this.rectangle = rectangle;
	}
	
	@Override
	protected void setupTraits() {
		super.setupTraits();
		
		this.traits.add(new HoverTrait(this));
	}
	
	@Override
	public void onInteract() {
		EnumDestination destination = EnumDestination.getDestination(destinationKey);
		try {
			Constructor<? extends AbstractArea> constructor = destination.getArea().getConstructor(Player.class);
			Object obj = constructor.newInstance(GameState.get().getPlayer());
			EnumSound.DOOR_OPEN.getSound().play();
			GameScreen.instance.changeArea((AbstractArea) obj, true, destination);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	@Override
	public IconTexture getCursorIcon() {
		return Textures.IconTexture.DOOR;
	}
	
	@Override
	public void dispose() {
		// No disposing needed
	}
	
	@Override
	public Vector2 getPosition() {
		return null;
	}
}
