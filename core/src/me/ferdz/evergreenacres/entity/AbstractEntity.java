package me.ferdz.evergreenacres.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class AbstractEntity implements IUpdatable, IRenderable, Disposable {
	public abstract Vector2 getPosition();
	
	public void setPosition(Vector2 pos) {
		getPosition().set(pos);
	}
}
