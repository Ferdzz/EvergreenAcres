package me.ferdz.evergreenacres.core.entity.impl.tile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.AbstractEntity;

public class Tile extends AbstractEntity {

	private Vector2 position;
	
	public Tile(Vector2 position) {
		this.position = position;
	}
	
	@Override
	public void update(float delta) {

	}

	@Override
	public void render(Batch batch) {
		TextureRegion texture = EnumSoilTexture.NO_CONNECTION.getTextureRegion();
		batch.draw(texture, position.x * texture.getRegionWidth(), position.y * texture.getRegionHeight());
	}

	@Override
	public void dispose() {

	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

}
