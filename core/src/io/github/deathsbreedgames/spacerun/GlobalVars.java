/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Global variables used throughout the game
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
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
