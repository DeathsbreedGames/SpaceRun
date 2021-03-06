/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.screens;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * Screen used to display DeathsbreedGames logo and provide a
 * quick link to the DeathsbreedGames website.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class SplashScreen extends BaseScreen {
	// Splash variables
	private OrthographicCamera camera;
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
	public SplashScreen(AssetManager manager) {
		super("MainMenu", manager);
		
		// Splash
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		splash = manager.get("gfx/deathsbreedgames/logo.png", Texture.class);
		splashWidth = (float)GlobalVars.width;
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
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.setColor(1f, 1f, 1f, a);
		batch.draw(splash, 0f, GlobalVars.height / 2 - splashHeight / 2, splashWidth, splashHeight);
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
	}
}
