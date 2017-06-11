package me.ferdz.evergreenacres.entity.impl;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.audio.EnumSound;
import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.screen.GameScreen;
import me.ferdz.evergreenacres.utils.GameState;
import me.ferdz.evergreenacres.utils.Values;

public class WarpObject extends AbstractEntity {

	private Rectangle rectangle;
	private String destinationKey;
	public WarpObject(Rectangle rectangle, MapProperties properties) {
		this.rectangle = rectangle;
		this.destinationKey = properties.get(Values.KEY_DESTINATION, String.class);
	}
	
	@Override
	public void update(float delta) {
//		Body body = GameScreen.instance.getPlayer().getBody();
		Player player = GameState.get().getPlayer();
		Rectangle playerRect = new Rectangle(player.getPosition().x, player.getPosition().y, 7.5f, 7.5f);
		if (!GameState.get().isChangingArea() && rectangle.contains(playerRect)) {
			// If the player is inside the warp zone
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
	}

	@Override
	public void render(SpriteBatch batch) {

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Vector2 getPosition() {
		throw new UnsupportedOperationException();
	}
}
