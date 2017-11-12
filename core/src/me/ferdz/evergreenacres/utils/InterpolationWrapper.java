package me.ferdz.evergreenacres.utils;

import com.badlogic.gdx.math.Interpolation;
import lombok.Getter;
import lombok.Setter;

public class InterpolationWrapper {

    private Interpolation interpolation;
    private float elapsedTime;
    @Getter @Setter private boolean isBackwards;
    @Getter @Setter private boolean isReversed;
    @Getter @Setter private boolean comesBack;
    @Getter private boolean isComingBack;
    @Getter private boolean isDone;

    public InterpolationWrapper(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    public float progress(float delta) {
        if (this.isDone) {
            if (this.isReversed) {
                return 0;
            } else {
                return 1;
            }
        }

        this.elapsedTime += delta;

        float value = this.interpolation.apply(elapsedTime);
        if (this.isReversed) {
            value = 1 - value;
        }
        if (this.isBackwards) {
            value = -value;
        }

        if (elapsedTime >= 1 && this.comesBack && !this.isDone && !this.isComingBack) { // We should head back
            this.isComingBack = true;
            this.isReversed = !this.isReversed;
            this.elapsedTime = 0;
        } else if (elapsedTime >= 1) {
            this.isDone = true;
        }

        return value;
    }
}
