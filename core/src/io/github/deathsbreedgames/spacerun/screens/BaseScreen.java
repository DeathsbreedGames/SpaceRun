/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

/**
 * A template for all the screens (so as not to repeat the same
 * thing over and over again.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class BaseScreen implements Screen {
	// Common screen variables for this game
	private boolean done;
	private String nextScreen;

	protected AssetManager manager;
	
	// Constructor:
	public BaseScreen(String nextScreen) {
		this.nextScreen = nextScreen;
		done = false;
	}

	public BaseScreen(String nextScreen, AssetManager manager) {
		this.nextScreen = nextScreen;
		done = false;
		this.manager = manager;
	}
	
	// Implemented methods:
	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void resize(int width, int height) { }
	
	@Override
	public void show() { }
	
	@Override
	public void hide() { }
	
	@Override
	public void pause() { }
	
	@Override
	public void resume() { }
	
	@Override
	public void dispose() { }
	
	// Getter methods:
	public boolean isDone() { return done; }
	public String getNextScreen() { return nextScreen; }
	
	// Setter methods:
	public void setDone(boolean done) { this.done = done; }
	public void setNextScreen(String nextScreen) { this.nextScreen = nextScreen; }
}
