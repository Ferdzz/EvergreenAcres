package me.ferdz.evergreenacres;

import com.badlogic.gdx.Game;

import me.ferdz.evergreenacres.core.GameScreen;
import me.ferdz.evergreenacres.utils.Values;

public class EvergreenAcres extends Game {
	
	@Override
	public void create () {
		Values.game = this;
		
		setScreen(new GameScreen());
	}
}
