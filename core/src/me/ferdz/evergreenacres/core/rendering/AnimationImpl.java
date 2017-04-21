package me.ferdz.evergreenacres.core.rendering;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationImpl extends Animation<TextureRegion> {

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
}
