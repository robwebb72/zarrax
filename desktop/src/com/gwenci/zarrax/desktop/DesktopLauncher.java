package com.gwenci.zarrax.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gwenci.zarrax.Zarrax;

public class DesktopLauncher {
	public static final int SCREEN_WIDTH = 672;
	public static final int SCREEN_HEIGHT = 768;
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SCREEN_WIDTH;
		config.height = SCREEN_HEIGHT;
		config.backgroundFPS = 30;
		config.foregroundFPS = 60;
		config.title = "Zarrax";
		config.resizable = false;   // window should not be resizable
		config.forceExit = false;   // prevent non zero exit error

		new LwjglApplication(new Zarrax(), config);
	}
}
