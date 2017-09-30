package me.ferdz.evergreenacres.entity.trait;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.ferdz.evergreenacres.entity.IRenderable;
import me.ferdz.evergreenacres.entity.IUpdatable;

public interface ITrait extends IUpdatable, IRenderable {
	@Override
	default void render(SpriteBatch batch) {
		// Nothing to do - default to avoid forcing empty implementations in childs
	}
}
