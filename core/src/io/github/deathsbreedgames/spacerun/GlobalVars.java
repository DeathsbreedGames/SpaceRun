package io.github.deathsbreedgames.spacerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class contains variables that are global and apply
 * to multiple classes in multiple game instances.
 * 
 */
public class GlobalVars {
	// Viewport dimensions
	public static final int width = 320;
	public static final int height = 480;
	
	// Locale information
	public static I18NBundle gameBundle;

	// Audio variables
	public static boolean soundOn = true;
	public static boolean musicOn = true;
	
	// Game variables
	public static int ship = 0;
	public static int score = 0;
	public static int highScore = 0;
	public static int killCount = 0;
	
	public static ActionResolver actionResolver;
	
	// Adjust location of mouse/touch to viewport
	public static float getTouchX() {
		float tX = (float)width / (float)Gdx.graphics.getWidth() * (float)Gdx.input.getX();
		return tX;
	}
	
	public static float getTouchY() {
		float tY = (float)height / (float)Gdx.graphics.getHeight() * (float)Gdx.input.getY();
		return tY;
	}
}
