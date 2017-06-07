package me.ferdz.evergreenacres.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import me.ferdz.evergreenacres.entity.IUpdatable;
import me.ferdz.evergreenacres.entity.impl.Player;
import me.ferdz.evergreenacres.map.AbstractArea;
import me.ferdz.evergreenacres.map.FarmArea;
import me.ferdz.evergreenacres.ui.ItemBar;
import me.ferdz.evergreenacres.ui.TooltipLabel;

public class GameState implements IUpdatable, Disposable {
	
	private Player player;
	private AbstractArea currentArea;
	private ItemBar itemBar;
	private TooltipLabel tooltip;
	private Vector2 cursorPosition;
	private boolean isChangingArea;

	private static GameState instance;
	private GameState() {
		this.player = new Player();
		this.currentArea = new FarmArea(player);
		this.currentArea.teleportPlayer();
		this.itemBar = new ItemBar();
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

	public Player getPlayer() {
		return player;
	}
	
	public AbstractArea getCurrentArea() {
		return currentArea;
	}
	
	public void setCurrentArea(AbstractArea currentArea) {
		this.currentArea = currentArea;
	}
	
	public ItemBar getItemBar() {
		return itemBar;
	}
	
	public TooltipLabel getTooltip() {
		return tooltip;
	}
	
	public void setTooltip(TooltipLabel tooltip) {
		this.tooltip = tooltip;
	}
	
	public Vector2 getCursorPosition() {
		return cursorPosition;
	}
	
	public void setCursorPosition(Vector2 cursorPosition) {
		this.cursorPosition = cursorPosition;
	}
	
	public boolean isChangingArea() {
		return isChangingArea; // TODO: Change this to a more relevant paused/unpaused state
	}
	
	public void setChangingArea(boolean isChangingArea) {
		this.isChangingArea = isChangingArea;
	}
}
