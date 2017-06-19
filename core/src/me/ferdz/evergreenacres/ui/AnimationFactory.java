package me.ferdz.evergreenacres.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;

public class AnimationFactory {
	public static ColorAction getDarkenAction() {
		ColorAction startColor = new ColorAction();
		startColor.setColor(new Color(0xffffffff));
		startColor.setEndColor(new Color(0, 0, 0, 1));
		startColor.setDuration(1f);
		return startColor;
	}
	
	public static ColorAction getLightenAction() {
		ColorAction endColor = new ColorAction();
		endColor.setColor(new Color(0, 0, 0, 1));
		endColor.setEndColor(new Color(0xffffffff));
		endColor.setDuration(1f);
		return endColor;
	}
}
