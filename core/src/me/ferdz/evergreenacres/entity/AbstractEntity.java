package me.ferdz.evergreenacres.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.entity.trait.ITrait;

public abstract class AbstractEntity implements IUpdatable, IRenderable, Disposable {
	protected ArrayList<ITrait> traits;
	
	public AbstractEntity() {
		this.traits = new ArrayList<>();
		this.setupTraits();
	}
	
	protected void setupTraits() {
		
	}
	
	@Override
	public void update(float delta) {
		for (ITrait trait : this.traits) {
			trait.update(delta);
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		for (ITrait trait : this.traits) {
			trait.render(batch);
		}
	}
	
	public void setPosition(Vector2 pos) {
		getPosition().set(pos);
	}
	
	public abstract Vector2 getPosition();
	
}
