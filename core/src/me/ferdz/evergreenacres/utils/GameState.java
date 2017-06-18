package me.ferdz.evergreenacres.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import lombok.Getter;
import lombok.Setter;
import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.entity.impl.tile.Tile;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.map.navigation.EnumDestination;
import me.ferdz.evergreenacres.ui.ItemBar;
import me.ferdz.evergreenacres.ui.TooltipLabel;

public class GameState implements IUpdatable, Disposable {
	
	@Getter private Player player;
	@Setter @Getter private AbstractArea currentArea;
	@Getter private ItemBar itemBar;
	@Setter @Getter private TooltipLabel tooltip;
	@Setter @Getter private Vector2 cursorPosition;
	@Setter @Getter private boolean isChangingArea; // TODO: Change this to a more relevant paused/unpaused state
	@Getter private Tile[][] soil;

	private static GameState instance;
	private GameState() {
		this.player = new Player();
		this.currentArea = new FarmArea(player);
		this.player.createBody(currentArea.getWorld(), new Vector2(EnumDestination.FARM_SPAWN.getX(), EnumDestination.FARM_SPAWN.getY()));
		this.itemBar = new ItemBar();
		this.soil = new Tile[Values.FARM_WIDTH][Values.FARM_HEIGHT];
	}
	
	@Override
	public void update(float delta) {
		this.cursorPosition = Utils.cursorToWorldPos();
	}
	
	@Override
	public void dispose() {
		player.dispose();
		currentArea.dispose();		
	}
	
	public static GameState get() {
		if (GameState.instance == null) 
			GameState.instance = new GameState();
		return GameState.instance;
	}
}
