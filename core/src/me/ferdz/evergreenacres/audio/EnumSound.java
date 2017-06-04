package me.ferdz.evergreenacres.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum EnumSound {
	DOOR_OPEN("sounds/door_open.ogg"),
	DOOR_CLOSE("sounds/door_close.ogg");
	
	private String path;
	private Sound sound;
	EnumSound(String path) {
		this.path = path;
	}
	
	public Sound getSound() {
		if (sound == null)
			sound = Gdx.audio.newSound(Gdx.files.internal(this.path));
		return sound;
	}
}
