package me.ferdz.evergreenacres.core.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Player extends AbstractEntity {

	private Texture texture;
	private static final float SPEED = 45F;
	
	public Player() {
		texture = new Texture(Gdx.files.internal("player.png"));
	}
	
	@Override
	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.A)) {
			position.x = position.x - (delta * SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			position.x = position.x + (delta * SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.W)) {
			position.y = position.y + (delta * SPEED);
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			position.y = position.y - (delta * SPEED);
		}
	}

	@Override
	public void render(Batch batch) {
		batch.draw(texture, position.x, position.y);
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
}
