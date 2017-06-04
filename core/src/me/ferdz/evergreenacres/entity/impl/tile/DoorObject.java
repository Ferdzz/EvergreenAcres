package me.ferdz.evergreenacres.entity.impl.tile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.Values;

public class DoorObject extends AbstractEntity {

	private Rectangle rectangle;
	private String destinationKey;
	
	public DoorObject(Rectangle rectangle, MapProperties properties) {
		this.rectangle = rectangle;
		this.destinationKey = properties.get(Values.KEY_DESTINATION, String.class);
	}
	
	@Override
	public void update(float delta) {
		if (rectangle.contains(GameScreen.instance.getCursorPosition())) {
			Gdx.graphics.setCursor(Gdx.graphics.newCursor(Textures.IconTexture.DOOR.getPixmap(), 0, 0));
			if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
				// Enter the destination
				EnumDestination destination = EnumDestination.getDestination(destinationKey);
				try {
					Constructor<? extends AbstractArea> constructor = destination.getArea().getConstructor(Player.class);
					Object obj = constructor.newInstance(GameScreen.instance.getPlayer());
					GameScreen.instance.changeArea((AbstractArea) obj); 
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
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
	public Vector2 getPosition() {
		throw new UnsupportedOperationException();// Position of the door is irrelevant
	}

}
