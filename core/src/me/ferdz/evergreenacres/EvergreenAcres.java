package me.ferdz.evergreenacres;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import me.ferdz.evergreenacres.core.screen.GameScreen;
import me.ferdz.evergreenacres.utils.Values;
import me.ferdz.evergreenacres.utils.input.InputBusProcessor;

public class EvergreenAcres extends Game {
	
	@Override
	public void create () {
		Values.game = this;
		
		setScreen(new GameScreen());
		
		// Instantiate the input processor
		Gdx.input.setInputProcessor(Values.multiplexer);
		Values.multiplexer.addProcessor(new InputBusProcessor());
	}
}
