package me.ferdz.evergreenacres.core.entity.impl.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.AbstractEntity;

public abstract class Tile extends AbstractEntity {

	protected Vector2 position;
	
	public Tile(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Batch batch) {

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

}
