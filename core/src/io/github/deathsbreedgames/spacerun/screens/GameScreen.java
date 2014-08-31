package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
	private TextureAtlas spriteAtlas;
	
	private Player player;
	
	// Constructor:
	public GameScreen() {
		super("Splash");
		
		batch = new SpriteBatch();
		spriteAtlas = new TextureAtlas("gfx/sprites/sprites.pack");
		player = new Player(spriteAtlas.findRegion("bluedestroyer"), 160, 50);
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
		spriteAtlas.dispose();
	}
}
