package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

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
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas spaceshipAtlas;
	private TextureAtlas bulletAtlas;
	private BitmapFont font;
	
	private Player player;
	private Enemy[] enemies;
	private final int NUM_ENEMIES = 30;
	private int currentMaxEnemies;
	private int currentEnemies;
	private Bullet[] bullets;
	private final int NUM_BULLETS = 100;
	
	private Sound laserShot;
	
	// Constructor:
	public GameScreen() {
		super("Splash");
		
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = new TextureAtlas("gfx/ui/buttons.pack");
		buttonSkin = new Skin(buttonAtlas);
		Gdx.input.setInputProcessor(mainStage);
		
		ImageButtonStyle imgBtnStyle = new ImageButtonStyle();
		imgBtnStyle.imageUp = buttonSkin.getDrawable("ExitButton");
		
		ImageButton exitButton = new ImageButton(imgBtnStyle);
		exitButton.setPosition(GlobalVars.width - (exitButton.getWidth() + 5f), GlobalVars.height - (exitButton.getHeight() + 5f));
		mainStage.addActor(exitButton);
		exitButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				setNextScreen("MainMenu");
				setDone(true);
			}
		});
		
		// Setup entities
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		
		batch = new SpriteBatch();
		spaceshipAtlas = new TextureAtlas("gfx/sprites/spaceships.pack");
		bulletAtlas = new TextureAtlas("gfx/sprites/bullets.pack");
		font = new BitmapFont();
		font.scale(0.01f);
		
		if(GlobalVars.ship == 0) {
			player = new Player(spaceshipAtlas.findRegion("bluedestroyer"), 160, 50, "bluelaser", 200, 250, 0.5f);
		} else if(GlobalVars.ship == 1) {
			player = new Player(spaceshipAtlas.findRegion("bluecarrier"), 160, 50, "greenlaser", 400, 250, 0.5f);
		} else {
			player = new Player(spaceshipAtlas.findRegion("bluecruiser"), 160, 50, "greenlaser", 200, 500, 0.5f);
		}
		enemies = new Enemy[NUM_ENEMIES];
		for(int i = 0; i < NUM_ENEMIES; i++) { enemies[i] = null; }
		currentMaxEnemies = 1;
		currentEnemies = 0;
		bullets = new Bullet[NUM_BULLETS];
		for(int i = 0; i < NUM_BULLETS; i++) { bullets[i] = null; }
		
		laserShot = Gdx.audio.newSound(Gdx.files.internal("sfx/laser5.mp3"));
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Player shoot
		if(player.shoot) {
			bulletLoop:
			for(int i = 0; i < NUM_BULLETS; i++) {
				if(bullets[i] == null) {
					bullets[i] = new Bullet(getBulletImg((Ship)player), player.getPosX(),
						player.getPosY() + 35, getDmg((Ship)player));
					bullets[i].setVelY(600f);
					laserShot.play();
					break bulletLoop;
				}
			}
		}
		
		currentMaxEnemies = MathUtils.ceil(player.getScore() / 100f);
		if(currentMaxEnemies > NUM_ENEMIES) currentMaxEnemies = NUM_ENEMIES;
		else if(currentMaxEnemies == 0) currentMaxEnemies = 1;
		// Create enemies
		if(currentEnemies < currentMaxEnemies) {
			enemyLoop:
			for(int i = 0; i < currentMaxEnemies; i++) {
				if(enemies[i] == null) {
					createEnemy(i);
					break enemyLoop;
				}
			}
		}
		
		// Enemies shoot
		for(int i = 0; i < NUM_ENEMIES; i++) {
			if(enemies[i] != null && enemies[i].shoot) {
				bulletLoop:
				for(int j = 0; j < NUM_BULLETS; j++) {
					if(bullets[j] == null) {
						bullets[j] = new Bullet(getBulletImg((Ship)enemies[i]), enemies[i].getPosX(),
							enemies[i].getPosY() - 35, getDmg((Ship)enemies[i]));
						bullets[j].setVelY(-600f);
						laserShot.play();
						break bulletLoop;
					}
				}
			}
		}
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Update player
		player.render(batch, delta);
		if(player.getShields() <= 0) {
			GlobalVars.score = player.getScore();
			setNextScreen("GameOver");
			setDone(true);
		}
		// Update enemies
		for(int i = 0; i < NUM_ENEMIES; i++) {
			if(enemies[i] != null) {
				enemies[i].render(batch, delta);
				
				// Check for collisions
				if(enemies[i].getPosY() <= 0) {
					enemies[i] = null;
				} else if(enemies[i].getShields() <= 0) {
					score(enemies[i]);
					enemies[i] = null;
				} else if(enemies[i].getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
					player.incShields(-100);
					enemies[i] = null;
				}
			}
		}
		// Update bullets
		for(int i = 0; i < NUM_BULLETS; i++) {
			if(bullets[i] != null) {
				bullets[i].render(batch, delta);
				
				// Check for collisions
				if(bullets[i].getPosY() >= Gdx.graphics.getHeight() || bullets[i].getPosY() <= 0) {
					bullets[i] = null;
				} else if(bullets[i].getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
					player.incShields(-bullets[i].getDmg());
					bullets[i] = null;
				} else if(bullets[i].getVelY() > 0) {
					enemyCollideLoop:
					for(int j = 0; j < NUM_ENEMIES; j++) {
						if(enemies[j] != null) {
							if(bullets[i].getBoundingRectangle().overlaps(enemies[j].getBoundingRectangle())) {
								enemies[j].incShields(-bullets[i].getDmg());
								bullets[i] = null;
								break enemyCollideLoop;
							}
						}
					}
				}
			}
		}
		font.draw(batch, "Shields: " + player.getShields(), 10, 470);
		font.draw(batch, "Score: " + player.getScore(), 10, 450);
		batch.end();
		
		mainStage.act();
		mainStage.draw();
	}
	
	private TextureRegion getBulletImg(Ship ship) {
		if(ship.getWeapon().equals("greenlaser")) {
			return bulletAtlas.findRegion("NormalBullet-green");
		} else if(ship.getWeapon().equals("bluelaser")) {
			return bulletAtlas.findRegion("NormalBullet-blue");
		} else if(ship.getWeapon().equals("redlaser")) {
			return bulletAtlas.findRegion("NormalBullet-red");
		} else {
			return bulletAtlas.findRegion("NormalBullet-green");
		}
	}
	
	private int getDmg(Ship ship) {
		if(ship.getWeapon().equals("greenlaser")) return 25;
		else if(ship.getWeapon().equals("bluelaser")) return 50;
		else if(ship.getWeapon().equals("redlaser")) return 100;
		else return 25;
	}
	
	private void createEnemy(int n) {
		RandomXS128 rand = new RandomXS128();
		float xPos = rand.nextFloat() * GlobalVars.width;
		float type = rand.nextFloat();
		
		if(player.getScore() < 200) {
			enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
				xPos, 480, "greenlaser", 50, 175, 1.5f, "F51");
		} else if(player.getScore() < 400) {
			if(type < 0.8f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, "greenlaser", 50, 175, 1.5f, "F51");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, "greenlaser", 75, 175, 1.0f, "F52");
			}
		} else if(player.getScore() < 1000) {
			if(type < 0.5f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, "greenlaser", 50, 175, 1.5f, "F51");
			} else if(type < 0.75f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, "greenlaser", 75, 175, 1.0f, "F52");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S3"),
					xPos, 480, "bluelaser", 75, 200, 1.0f, "F53");
			}
		} else {
			if(type < 0.5f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, "greenlaser", 50, 175, 1.5f, "F51");
			} else if(type < 0.7f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, "greenlaser", 75, 175, 1.0f, "F52");
			} else if(type < 0.9f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S3"),
					xPos, 480, "bluelaser", 75, 200, 1.0f, "F53");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S4"),
					xPos, 480, "bluelaser", 100, 200, 0.75f, "F54");
			}
		}
		
		enemies[n].setVelY(-enemies[n].getMaxVel());
	}
	
	private void score(Enemy enemy) {
		if(enemy.getType().equals("F51")) player.incScore(10);
		else if(enemy.getType().equals("F52")) player.incScore(25);
		else if(enemy.getType().equals("F53")) player.incScore(50);
		else if(enemy.getType().equals("F54")) player.incScore(100);
	}
	
	// Dispose:
	@Override
	public void dispose() {
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		
		batch.dispose();
		spaceshipAtlas.dispose();
		bulletAtlas.dispose();
		
		laserShot.dispose();
	}
}
