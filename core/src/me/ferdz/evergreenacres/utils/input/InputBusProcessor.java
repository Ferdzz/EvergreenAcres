package me.ferdz.evergreenacres.utils.input;

import com.badlogic.gdx.Input.Buttons;

import me.ferdz.evergreenacres.utils.Values;

public class InputBusProcessor implements com.badlogic.gdx.InputProcessor {
	
	@Override
	public boolean keyDown(int keycode) {
		Values.bus.post(new InputEvents.KeyPressedEvent(keycode));
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT) {
			Values.bus.post(new InputEvents.LeftClickEvent(screenX, screenY));
		}
		else if (button == Buttons.RIGHT) {
			Values.bus.post(new InputEvents.RightClickEvent(screenX, screenY));
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		Values.bus.post(new InputEvents.ScrollEvent(amount));
		return true;
	}
}

