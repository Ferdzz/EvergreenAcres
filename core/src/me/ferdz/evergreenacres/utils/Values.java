package me.ferdz.evergreenacres.utils;

import com.badlogic.gdx.Gdx;
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
	public static final int TILE_WIDTH = 16;
	public static final int TILE_HEIGHT = 16;

	public static final String LAYER_GROUND = "Ground";
	public static final String TYPE_DIRT = "dirt";
	public static final String TYPE_DOOR = "door";
	public static final String TYPE_WARP = "warp";
	public static final String KEY_TYPE = "type";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_DESTINATION = "destination";
}