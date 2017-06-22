package me.ferdz.evergreenacres.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.entity.impl.tile.Tile;

public abstract class AbstractHoverTile extends Tile implements IHoverObject {

	protected Rectangle rectangle;	
	
	public AbstractHoverTile(Rectangle rectangle) {
		this(null, rectangle);
	}
	
	public AbstractHoverTile(Vector2 position, Rectangle rectangle) {
		super(position);
		this.rectangle = rectangle;
	}
	
	@Override
	public void update(float delta) {
		IHoverObject.super.update(delta);
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}
}
