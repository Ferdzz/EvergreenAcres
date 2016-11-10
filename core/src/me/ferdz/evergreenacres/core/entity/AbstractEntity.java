package me.ferdz.evergreenacres.core.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.core.IRenderable;
import me.ferdz.evergreenacres.core.IUpdatable;

public abstract class AbstractEntity implements IUpdatable, IRenderable, Disposable {
	public abstract Vector2 getPosition();
	
	public void setPosition(Vector2 pos) {
		getPosition().set(pos);
	}
}
