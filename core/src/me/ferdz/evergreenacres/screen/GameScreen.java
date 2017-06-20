package me.ferdz.evergreenacres.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceActionImpl;

import lombok.Getter;
import me.ferdz.evergreenacres.audio.EnumSound;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.ui.AnimationFactory;
import me.ferdz.evergreenacres.utils.GameState;

public class GameScreen extends ScreenAdapter {
		
	public static GameScreen instance;
	
	@Getter private GameRenderer gameRenderer;
	
	@Override
	public void show() {
		instance = this;
		
		this.gameRenderer = new GameRenderer();
	}

	@Override
	public void render(float delta) {
		gameRenderer.render(delta);
	}

	/**
	 * Switches the area rendered through a fadin/fadout action
	 * @param area Area to change to
	 * @param playSound Whether a close door sound should be played
	 */
	public void changeArea(AbstractArea area, boolean playSound, EnumDestination destination) {
		GameState.get().setChangingArea(true);

		RunnableAction runAction = new RunnableAction();
		runAction.setRunnable(new Runnable() {
			@Override
			public void run() {
				AbstractArea currentArea = GameState.get().getCurrentArea();
				currentArea.dispose();
				area.teleportPlayer(destination);
				gameRenderer.getMapRenderer().updateMap(area.getMap());
				GameState.get().setCurrentArea(area);
				if (playSound) {
					EnumSound.DOOR_CLOSE.getSound().play();
				}
				GameState.get().setChangingArea(false);
			}
		});

		gameRenderer.getMapRenderer().setAction(new SequenceActionImpl(
				AnimationFactory.getDarkenAction(),
				Actions.delay(0.5f), 
				runAction,
				AnimationFactory.getLightenAction()));
	}
			
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		gameRenderer.resize(width, height);
	}

	@Override
	public void dispose() {
		gameRenderer.dispose();
		GameState.get().dispose();
	}
}
