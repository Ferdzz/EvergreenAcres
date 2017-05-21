package me.ferdz.evergreenacres.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import me.ferdz.evergreenacres.EvergreenAcres;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
//		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
//		config.resizable = false;
		config.useVsync(true);
		config.setMaximized(true);
		config.setTitle("Evergreen Acres");
		new Lwjgl3Application(new EvergreenAcres(), config);

//		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
//		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.vSyncEnabled = true;
//		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
//		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
//		config.title = "Evergreen Acres";
//		config.fullscreen = true;
		
//		new LwjglApplication(new EvergreenAcres(), config);
	}
}
