package io.github.deathsbreedgames.spacerun;

import com.badlogic.gdx.Gdx;

import io.github.deathsbreedgames.spacerun.GlobalVars;

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
	public static final int width = 320;
	public static final int height = 480;
	
	public static float getTouchX() {
		float tX = (float)GlobalVars.width / (float)Gdx.graphics.getWidth() * (float)Gdx.input.getX();
		return tX;
	}
	
	public static float getTouchY() {
		float tY = (float)GlobalVars.height / (float)Gdx.graphics.getHeight() * (float)Gdx.input.getY();
		return tY;
	}
}
