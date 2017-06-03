package me.ferdz.evergreenacres.entity.impl.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.AbstractEntity;
import me.ferdz.evergreenacres.rendering.Textures;
import me.ferdz.evergreenacres.utils.Utils;

public class DoorObject extends AbstractEntity {

	private Rectangle rectangle;
	
	public DoorObject(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	@Override
	public void update(float delta) {
		if (rectangle.contains(Utils.cursorToWorldPos())) {
			Gdx.graphics.setCursor(Gdx.graphics.newCursor(Textures.IconTexture.DOOR.getPixmap(), 0, 0));
		} else { // TODO: Handle the default cursor properly
			Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
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
		// Position of the door is irrelevant
		return null;
	}

}
