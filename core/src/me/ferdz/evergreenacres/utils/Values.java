package me.ferdz.evergreenacres.utils;

import com.google.common.eventbus.EventBus;

import me.ferdz.evergreenacres.EvergreenAcres;

public class Values {
	public static EvergreenAcres game;
	public static EventBus bus = new EventBus();
	
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;
}