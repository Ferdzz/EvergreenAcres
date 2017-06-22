package me.ferdz.evergreenacres.entity.impl.object;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;

import me.ferdz.evergreenacres.audio.EnumSound;
import me.ferdz.evergreenacres.entity.AbstractHoverTile;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.rendering.Textures.IconTexture;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class DoorObject extends AbstractHoverTile {

	private String destinationKey;
	
	public DoorObject(Rectangle rectangle, MapProperties properties) {
		super(rectangle);
		this.destinationKey = properties.get(Values.KEY_DESTINATION, String.class);
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
	public void render(SpriteBatch batch) { 
		// No rendering needed
	}

	@Override
	public void dispose() {
		// No disposing needed
	}

	@Override
	public IconTexture getCursorIcon() {
		return Textures.IconTexture.DOOR;
	}
}
