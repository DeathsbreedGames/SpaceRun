package io.github.deathsbreedgames.spacerun.screens;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class handles the splash at the beginning of the
 * game.
 * 
 */
public class SplashScreen extends BaseScreen {
	// Splash variables
	private SpriteBatch batch;
	private Texture splash;
	private float splashWidth;
	private float splashHeight;
	
	// Alpha calculating variables
	private float a;
	private float timer;
	
	// Link opening variable
	private boolean oldMousePressed = false;
	
	// Constructor:
	public SplashScreen() {
		super("MainMenu");
		
		// Splash
		batch = new SpriteBatch();
		splash = new Texture("gfx/deathsbreedgames/logo.png");
		splashWidth = (float)Gdx.graphics.getWidth();
		splashHeight = splashWidth * 2f / 9.5f;
		
		// Alpha
		a = 0f;
		timer = 0f;
		
		// Set the preferences
		Preferences prefs = Gdx.app.getPreferences("SpaceRun");
		GlobalVars.gameBundle = I18NBundle.createBundle(Gdx.files.internal("i18n/GameBundle"), Locale.getDefault());
		GlobalVars.highScore = prefs.getInteger("HighScore");
		GlobalVars.killCount = prefs.getInteger("KillCount");
		GlobalVars.soundOn = !prefs.getBoolean("SoundOff");
		GlobalVars.musicOn = !prefs.getBoolean("MusicOff");
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Get alpha for splash
		if(timer < 2f) {
			if(a < 1f) {
				if(a > 0.98f) a = 1f;
				else a += 0.01f;
			} else if(a == 1f) {
				timer += delta;
			}
		} else {
			if(a > 0f) {
				if(a < 0.02) a = 0f;
				else a -= 0.01f;
			} else if(a == 0f) {
				setDone(true);
			}
		}
		
		// Draw splash
		batch.begin();
		batch.setColor(1f, 1f, 1f, a);
		batch.draw(splash, 0f, Gdx.graphics.getHeight() / 2 - splashHeight / 2, splashWidth, splashHeight);
		batch.end();
		
		// Open DeathsbreedGames website
		boolean mousePressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
		if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://deathsbreedgames.github.io");
		oldMousePressed = mousePressed;
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		splash.dispose();
	}
}
