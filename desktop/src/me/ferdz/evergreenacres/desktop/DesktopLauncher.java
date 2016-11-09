package me.ferdz.evergreenacres.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.ferdz.evergreenacres.EvergreenAcres;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.resizable = false;
//		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		new LwjglApplication(new EvergreenAcres(), config);
	}
}
