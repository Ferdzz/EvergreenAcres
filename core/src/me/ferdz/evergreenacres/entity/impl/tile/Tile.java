package me.ferdz.evergreenacres.entity.impl.tile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.AbstractEntity;

public abstract class Tile extends AbstractEntity {

	protected Vector2 position;
	
	public Tile(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void update(float delta) {

	}

	@Override
	public void render(SpriteBatch batch) {

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

}
