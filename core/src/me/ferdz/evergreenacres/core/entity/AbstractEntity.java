package me.ferdz.evergreenacres.core.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.core.IRenderable;
import me.ferdz.evergreenacres.core.IUpdatable;

public abstract class AbstractEntity implements IUpdatable, IRenderable, Disposable {
	protected Vector2 position;
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
