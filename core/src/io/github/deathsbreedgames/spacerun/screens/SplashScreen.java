package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	private SpriteBatch batch;
	private Texture splash;
	
	private float a;
	private float timer;
	
	private boolean oldMousePressed = false;
	
	// Constructor:
	public SplashScreen() {
		super("MainMenu");
		
		batch = new SpriteBatch();
		splash = new Texture("gfx/deathsbreedgames/logo.png");
		
		a = 0f;
		timer = 0f;
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
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
		
		batch.begin();
		batch.setColor(1f, 1f, 1f, a);
		batch.draw(splash, 0f, 204f, 320f, 72f);
		batch.end();
		
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
