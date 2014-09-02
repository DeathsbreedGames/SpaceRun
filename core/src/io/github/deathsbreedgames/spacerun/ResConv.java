package io.github.deathsbreedgames.spacerun;

import com.badlogic.gdx.Gdx;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class contains methods to convert variables for
 * different resolutions.
 * 
 */
public class ResConv {
	// normVar is what the variable would be at a resolution of 320x480
	public static float getRelX(float normVar) {
		float relVar = Gdx.graphics.getWidth() * (normVar / 320f);
		return relVar;
	}
	
	public static float getRelY(float normVar) {
		float relVar = Gdx.graphics.getHeight() * (normVar / 480f);
		return relVar;
	}
}
