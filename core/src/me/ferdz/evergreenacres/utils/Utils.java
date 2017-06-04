package me.ferdz.evergreenacres.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import me.ferdz.evergreenacres.entity.EnumDirection;
import me.ferdz.evergreenacres.screen.GameScreen;

public class Utils {
	
	/**
	 * Opposite of toWorldPos
	 * @param position position to translate
	 * @return a tile position according to the world position
	 */
	public static Vector2 toTilePos(Vector2 position) {
		int x = Math.round(position.x) / Values.TILE_WIDTH;
		int y = Math.round(position.y) / Values.TILE_HEIGHT;
		return new Vector2(x, y);
	}
	
	/**
	 * Opposite of toTilePos
	 * @param position position to translate
	 * @return a world position according to the tile position
	 */
	public static Vector2 toWorldPos(Vector2 position) {
		float x = position.x * Values.TILE_WIDTH;
		float y = position.y * Values.TILE_WIDTH;
		return new Vector2(x, y);
	}
	
	public static Vector2 cursorToWorldPos() {
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		Vector3 unprojected = GameScreen.instance.getCamera().unproject(new Vector3(x, y, 0));
		return new Vector2(unprojected.x, unprojected.y);
	}
	
	/**
	 * Offsets the world position according to direction provided
	 */
	public static Vector2 offsetPos(Vector2 position, EnumDirection direction) {
		int x = Math.round(position.x);
		int y = Math.round(position.y);
		
		switch (direction) {
		case UP:
			y++;
			break;
		case LEFT:
			x--;
			break;
		case DOWN:
			y--;
			break;
		case RIGHT:
			x++;
			break;
		}
		
		return new Vector2(x, y);
	}
	
}
