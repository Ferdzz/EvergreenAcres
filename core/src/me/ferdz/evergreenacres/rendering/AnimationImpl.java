package me.ferdz.evergreenacres.rendering;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class AnimationImpl extends Animation<TextureRegion> implements Disposable {

	private float elapsedTime;

	public AnimationImpl(float frameDuration, TextureRegion... keyFrames) {
		super(frameDuration, keyFrames);
		this.elapsedTime = 0;
	}

	@Override
	public int getKeyFrameIndex(float stateTime) {
		this.elapsedTime += stateTime;
		return super.getKeyFrameIndex(elapsedTime);
	}

	@Override
	public void dispose() {
		for (TextureRegion texture : this.getKeyFrames()) {
			texture.getTexture().dispose();
		}
	}
	
	public float getElapsedTime() {
		return this.elapsedTime;
	}
	
	public void setElpasedTime(float elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
}
