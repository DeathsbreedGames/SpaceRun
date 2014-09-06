package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.deathsbreedgames.spacerun.GlobalVars;
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
	private OrthographicCamera camera;
	
	private SpriteBatch batch;
	private TextureAtlas spaceshipAtlas;
	private TextureAtlas bulletAtlas;
	
	private Player player;
	private Entity[] bullets;
	private final int NUM_BULLETS = 100;
	
	private Sound laserShot;
	
	// Constructor:
	public GameScreen() {
		super("Splash");
		
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		
		batch = new SpriteBatch();
		spaceshipAtlas = new TextureAtlas("gfx/sprites/spaceships.pack");
		bulletAtlas = new TextureAtlas("gfx/sprites/bullets.pack");
		
		if(GlobalVars.ship == 0) {
			player = new Player(spaceshipAtlas.findRegion("bluedestroyer"), 160, 50, 50, 100, 300);
		} else if(GlobalVars.ship == 1) {
			player = new Player(spaceshipAtlas.findRegion("bluecarrier"), 160, 50, 25, 200, 300);
		} else {
			player = new Player(spaceshipAtlas.findRegion("bluecruiser"), 160, 50, 25, 100, 400);
		}
		bullets = new Entity[NUM_BULLETS];
		for(int i = 0; i < NUM_BULLETS; i++) { bullets[i] = null; }
		
		laserShot = Gdx.audio.newSound(Gdx.files.internal("sfx/laser5.mp3"));
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
					laserShot.play();
					break bulletLoop;
				}
			}
		}
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.render(batch, delta);
		for(int i = 0; i < NUM_BULLETS; i++) {
			if(bullets[i] != null) {
				bullets[i].render(batch, delta);
				if(bullets[i].getPosY() >= Gdx.graphics.getHeight() || bullets[i].getPosY() <= 0) {
					bullets[i] = null;
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
