package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
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
	private TextureAtlas spaceshipAtlas;
	private TextureAtlas bulletAtlas;
	
	private Player player;
	private Entity[] bullets;
	private final int NUM_BULLETS = 200;
	
	// Constructor:
	public GameScreen() {
		super("Splash");
		
		batch = new SpriteBatch();
		spaceshipAtlas = new TextureAtlas("gfx/sprites/spaceships.pack");
		bulletAtlas = new TextureAtlas("gfx/sprites/bullets.pack");
		
		player = new Player(spaceshipAtlas.findRegion("bluedestroyer"), 160, 50);
		bullets = new Entity[NUM_BULLETS];
		for(int i = 0; i < NUM_BULLETS; i++) { bullets[i] = null; }
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if(player.shoot) {
			bulletLoop:
			for(int i = 0; i < NUM_BULLETS; i++) {
				if(bullets[i] == null) {
					bullets[i] = new Entity(bulletAtlas.findRegion("NormalBullet-red"),
					  player.getPosX(), player.getPosY() + 35);
					bullets[i].setVelY(600f);
					break bulletLoop;
				}
			}
		}
		
		batch.begin();
		player.render(batch, delta);
		for(int i = 0; i < NUM_BULLETS; i++) {
			if(bullets[i] != null) {
				if(bullets[i].getY() > Gdx.graphics.getHeight() + 50f ||
				  bullets[i].getY() < -50f) {
					bullets[i] = null;
				} else {
					bullets[i].render(batch, delta);
				}
			}
		}
		batch.end();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		spaceshipAtlas.dispose();
		bulletAtlas.dispose();
	}
}
