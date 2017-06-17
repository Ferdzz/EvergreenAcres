package me.ferdz.evergreenacres.rendering;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import lombok.Getter;
import lombok.Setter;

public class AnimationImpl extends Animation<TextureRegion> implements Disposable {

	@Setter @Getter private float elapsedTime;

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
}
