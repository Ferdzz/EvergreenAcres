package me.ferdz.evergreenacres.utils;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.google.common.eventbus.EventBus;

import me.ferdz.evergreenacres.EvergreenAcres;

public class Values {
	/*
	 * ---------- Game state properties
	 */
	public static EvergreenAcres game;
	public static final EventBus bus = new EventBus();
	
	/*
	 * ---------- Gameplay properties
	 */
	public static final int INTERACTION_DISTANCE = 35;
	
	/*
	 * ---------- Util properties
	 */
	public static final InputMultiplexer multiplexer = new InputMultiplexer();
	
	/*
	 * ---------- Control properties
	 */
	public static final HashMap<Integer, Integer> KEYS_NUMBER;
	static {
		KEYS_NUMBER = new HashMap<>();
		KEYS_NUMBER.put(Keys.NUM_1, 1);
		KEYS_NUMBER.put(Keys.NUM_2, 2);
		KEYS_NUMBER.put(Keys.NUM_3, 3);
		KEYS_NUMBER.put(Keys.NUM_4, 4);
		KEYS_NUMBER.put(Keys.NUM_5, 5);
		KEYS_NUMBER.put(Keys.NUM_6, 6);
		KEYS_NUMBER.put(Keys.NUM_7, 7);
		KEYS_NUMBER.put(Keys.NUM_8, 8);
		KEYS_NUMBER.put(Keys.NUM_9, 9);
		KEYS_NUMBER.put(Keys.NUMPAD_1, 1);
		KEYS_NUMBER.put(Keys.NUMPAD_2, 2);
		KEYS_NUMBER.put(Keys.NUMPAD_3, 3);
		KEYS_NUMBER.put(Keys.NUMPAD_4, 4);
		KEYS_NUMBER.put(Keys.NUMPAD_5, 5);
		KEYS_NUMBER.put(Keys.NUMPAD_6, 6);
		KEYS_NUMBER.put(Keys.NUMPAD_7, 7);
		KEYS_NUMBER.put(Keys.NUMPAD_8, 8);
		KEYS_NUMBER.put(Keys.NUMPAD_9, 9);
	}
	
	/*
	 * ---------- Rendering / UI properties
	 */
	public static final Color BACKGROUND_COLOR = new Color(0.0392156862745098f, 0.0392156862745098f, 0.0392156862745098f, 255);
	public static final BitmapFont tooltipFont;
	static {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LCD_Solid.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 22;
		tooltipFont = generator.generateFont(parameter);
		generator.dispose();
	}
	
	/*
	 * ---------- Tile map properties
	 */
	public static final int FARM_WIDTH = 29;
	public static final int FARM_HEIGHT = 27;
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;

	public static final String LAYER_GROUND = "Ground";
	public static final String TYPE_DIRT = "dirt";
	public static final String TYPE_DOOR = "door";
	public static final String TYPE_WARP = "warp";
	public static final String TYPE_BED = "bed";
	public static final String KEY_TYPE = "type";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_DESTINATION = "destination";
}