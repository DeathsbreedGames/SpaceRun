package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.deathsbreedgames.spacerun.entities.*;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class takes care of the Game.
 * 
 */
public class GameScreen extends BaseScreen {
	private SpriteBatch batch;
	private Player player;
	
	// Constructor:
	public GameScreen() {
		super("Splash");
		
		batch = new SpriteBatch();
		player = new Player(new Texture("gfx/sprites/bluedestroyer.png"), 160, 50);
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		batch.begin();
		player.render(batch, delta);
		batch.end();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
	}
}
