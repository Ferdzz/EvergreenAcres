package me.ferdz.evergreenacres.core.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import me.ferdz.evergreenacres.core.entity.AbstractEntity;
import me.ferdz.evergreenacres.core.rendering.AnimationImpl;

public class Particle extends AbstractEntity {

	private Vector2 position;
	private AnimationImpl animation;
	
	/**
	 * Actually heavy on processor, do not call if the texture is already split
	 */
	public Particle(Texture texture, int frameWidth, int frameHeight, float duration) {
		this(TextureRegion.split(texture, frameWidth, frameHeight), duration);
	}
	
	public Particle(TextureRegion[][] texture, float duration) {
		this.animation = new AnimationImpl(duration, texture[0]);
		this.animation.setPlayMode(PlayMode.NORMAL);
	}
	
	@Override
	public void update(float delta) {
		if (this.isFinished())
			this.position = null;
	}

	@Override
	public void render(SpriteBatch batch) {
		float delta = Gdx.graphics.getDeltaTime();
		if (position == null)
			return;
		batch.draw(animation.getKeyFrame(delta), position.x, position.y);
	}

	@Override
	public void dispose() {
		animation.dispose();
	}
	
	public void resetAnimation() {
		this.animation.setElpasedTime(0);
	}

	@Override
	public void setPosition(Vector2 pos) {
		position = pos;
	}
	
	@Override
	public Vector2 getPosition() {
		return position;
	}
	
	public AnimationImpl getAnimation() {
		return this.animation;
	}
	
	public boolean isFinished() {
		return this.animation.isAnimationFinished(this.animation.getElapsedTime());
	}
}
