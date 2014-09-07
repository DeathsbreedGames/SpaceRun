package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This screen is shown when the player dies.
 * 
 */
public class GameOverScreen extends BaseScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	// Constructor:
	public GameOverScreen() {
		super("MainMenu");
		
		if(GlobalVars.score > GlobalVars.highScore) {
			GlobalVars.highScore = GlobalVars.score;
			Preferences prefs = Gdx.app.getPreferences("SpaceRun");
			prefs.putInteger("HighScore", GlobalVars.highScore);
			prefs.flush();
		}
		
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.scale(0.5f);
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		float textXPos;
		textXPos = GlobalVars.width / 2 - font.getBounds("Score: " + GlobalVars.score).width / 2;
		font.draw(batch, "Score: " + GlobalVars.score, textXPos, 350);
		textXPos = GlobalVars.width / 2 - font.getBounds("High Score: " + GlobalVars.highScore).width / 2;
		font.draw(batch, "High Score: " + GlobalVars.highScore, textXPos, 300);
		batch.end();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		
	}
}
