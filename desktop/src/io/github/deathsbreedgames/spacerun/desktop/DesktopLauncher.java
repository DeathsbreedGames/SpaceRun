package io.github.deathsbreedgames.spacerun.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.deathsbreedgames.spacerun.SpaceRun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Space Run";
		config.width = 400;
		config.height = 600;
		config.foregroundFPS = 45;
		config.backgroundFPS = 20;
		config.addIcon("gfx/icon.png", Files.FileType.Internal);
		new LwjglApplication(new SpaceRun(), config);
	}
}
