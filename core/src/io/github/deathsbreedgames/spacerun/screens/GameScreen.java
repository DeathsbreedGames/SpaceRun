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
	private TextureAtlas pickupAtlas;
	private BitmapFont font;
	
	private Player player;
	private Enemy[] enemies;
	private final int NUM_ENEMIES = 30;
	private int currentMaxEnemies;
	private int currentEnemies;
	private Bullet[] bullets;
	private final int NUM_BULLETS = 100;
	private Pickup pickup;
	private float pickupTimer;
	
	private float rapidTimer;
	private boolean speed;
	private float speedTimer;
	private float invTimer;
	
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
		pickupAtlas = new TextureAtlas("gfx/sprites/pickups.pack");
		font = new BitmapFont();
		font.scale(0.01f);
		
		if(GlobalVars.ship == 0) {
			player = new Player(spaceshipAtlas.findRegion("bluedestroyer"), 160, 50, 1, 200, 250, 0.5f);
		} else if(GlobalVars.ship == 1) {
			player = new Player(spaceshipAtlas.findRegion("bluecarrier"), 160, 50, 0, 400, 250, 0.5f);
		} else {
			player = new Player(spaceshipAtlas.findRegion("bluecruiser"), 160, 50, 0, 200, 500, 0.5f);
		}
		enemies = new Enemy[NUM_ENEMIES];
		for(int i = 0; i < NUM_ENEMIES; i++) { enemies[i] = null; }
		currentMaxEnemies = 1;
		currentEnemies = 0;
		bullets = new Bullet[NUM_BULLETS];
		for(int i = 0; i < NUM_BULLETS; i++) { bullets[i] = null; }
		
		pickup = null;
		pickupTimer = 0;
		
		rapidTimer = 0;
		speed = false;
		speedTimer = 0;
		invTimer = 0;
		
		laserShot = Gdx.audio.newSound(Gdx.files.internal("sfx/laser5.mp3"));
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Player shoot
		if(player.shoot) {
			boolean twoBullets = false;
			int bulletsMade = 0;
			bulletLoop:
			for(int i = 0; i < NUM_BULLETS; i++) {
				if(bullets[i] == null) {
					if(player.getDoubleShot()) {
						if(bulletsMade == 0) {
							bullets[i] = new Bullet(getBulletImg((Ship)player), player.getPosX() - 10, player.getPosY() + 35, getDmg((Ship)player));
							bulletsMade = 1;
						} else if(bulletsMade == 1) {
							bullets[i] = new Bullet(getBulletImg((Ship)player), player.getPosX() + 10, player.getPosY() + 35, getDmg((Ship)player));
							bulletsMade = 2;
							twoBullets = true;
						}
					} else {
						bullets[i] = new Bullet(getBulletImg((Ship)player), player.getPosX(), player.getPosY() + 35, getDmg((Ship)player));
					}
					bullets[i].setVelY(600f);
					if(GlobalVars.soundOn) laserShot.play();
					
					if(!player.getDoubleShot() || twoBullets) break bulletLoop;
				}
			}
		}
		
		// Create the enemies
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
						if(GlobalVars.soundOn) laserShot.play();
						break bulletLoop;
					}
				}
			}
		}
		
		pickupTimer += delta;
		if(pickupTimer >= 15 && pickup == null) {
			RandomXS128 rand = new RandomXS128();
			float probType = rand.nextFloat();
			if(probType < 0.30f) {
				pickup = new Pickup(pickupAtlas.findRegion("shield"), rand.nextFloat() * GlobalVars.width, 480, "repair");
			} else if(probType < 0.4f) {
				pickup = new Pickup(pickupAtlas.findRegion("double-shot"), rand.nextFloat() * GlobalVars.width, 480, "double");
			} else if(probType < 0.5f) {
				pickup = new Pickup(pickupAtlas.findRegion("speed"), rand.nextFloat() * GlobalVars.width, 480, "speed");
			} else if(probType < 0.65f) {
				pickup = new Pickup(pickupAtlas.findRegion("rapid-fire"), rand.nextFloat() * GlobalVars.width, 480, "rapid");
			} else if(probType < 0.75f) {
				pickup = new Pickup(pickupAtlas.findRegion("weapon-upgrade"), rand.nextFloat() * GlobalVars.width, 480, "upgrade");
			} else if(probType < 0.80f) {
				pickup = new Pickup(pickupAtlas.findRegion("full-shield"), rand.nextFloat() * GlobalVars.width, 480, "invincibility");
			}
			if(pickup != null) pickup.setVelY(-100f);
			pickupTimer = 0;
		} else if(pickup != null && pickup.getY() <= 0) {
			pickup = null;
		}
		
		if(rapidTimer <= 0) player.setRapidFire(false);
		else rapidTimer -= delta;
		
		if(speed && speedTimer <= 0) {
			player.incMaxVel(-200);
			speed = false;
		} else if(speedTimer > 0) {
			speedTimer -= delta;
		}
		
		if(invTimer <= 0 && player.isInvincible()) {
			player.setInvincible(false);
			if(GlobalVars.ship == 0) player.setRegion(spaceshipAtlas.findRegion("bluedestroyer"));
			else if(GlobalVars.ship == 1) player.setRegion(spaceshipAtlas.findRegion("bluecarrier"));
			else player.setRegion(spaceshipAtlas.findRegion("bluecruiser"));
		}
		else invTimer -= delta;
		
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
					if(!player.isInvincible()) player.incShields(-100);
					if(!player.isInvincible()) player.setDoubleShot(false);
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
					if(!player.isInvincible()) player.incShields(-bullets[i].getDmg());
					player.setDoubleShot(false);
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
		
		// Update pickups:
		if(pickup != null) {
			pickup.render(batch, delta);
			if(pickup.getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
				if(pickup.getType().equals("repair")) {
					player.incShields(50);
				} else if(pickup.getType().equals("double")) {
					player.setDoubleShot(true);
				} else if(pickup.getType().equals("speed")) {
					player.incMaxVel(200);
					speed = true;
					speedTimer = 10.0f;
				} else if(pickup.getType().equals("rapid")) {
					player.setRapidFire(true);
					rapidTimer = 5.0f;
				} else if(pickup.getType().equals("upgrade")) {
					player.upgradeWeapon();
				} else if(pickup.getType().equals("invincibility")) {
					player.setInvincible(true);
					invTimer = 7.0f;
					if(GlobalVars.ship == 0) player.setRegion(spaceshipAtlas.findRegion("bluedestroyer_normal"));
					else if(GlobalVars.ship == 1) player.setRegion(spaceshipAtlas.findRegion("bluecarrier_normal"));
					else player.setRegion(spaceshipAtlas.findRegion("bluecruiser_normal"));
				}
				pickup = null;
			}
		}
		
		font.draw(batch, "Shields: " + player.getShields(), 10, 470);
		font.draw(batch, "Score: " + player.getScore(), 10, 450);
		batch.end();
		
		mainStage.act();
		mainStage.draw();
	}
	
	private TextureRegion getBulletImg(Ship ship) {
		if(ship.getWeapon() == 0) {
			return bulletAtlas.findRegion("NormalBullet-green");
		} else if(ship.getWeapon() == 1) {
			return bulletAtlas.findRegion("NormalBullet-blue");
		} else if(ship.getWeapon() == 2) {
			return bulletAtlas.findRegion("NormalBullet-red");
		} else {
			return bulletAtlas.findRegion("NormalBullet-green");
		}
	}
	
	private int getDmg(Ship ship) {
		if(ship.getWeapon() == 0) return 25;
		else if(ship.getWeapon() == 1) return 50;
		else if(ship.getWeapon() == 2) return 100;
		else return 25;
	}
	
	private void createEnemy(int n) {
		RandomXS128 rand = new RandomXS128();
		float xPos = rand.nextFloat() * GlobalVars.width;
		float type = rand.nextFloat();
		
		if(player.getScore() < 200) {
			enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
				xPos, 480, 0, 50, 175, 1.5f, "F51");
		} else if(player.getScore() < 400) {
			if(type < 0.8f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, 0, 50, 175, 1.5f, "F51");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, 0, 75, 175, 1.0f, "F52");
			}
		} else if(player.getScore() < 1000) {
			if(type < 0.5f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, 0, 50, 175, 1.5f, "F51");
			} else if(type < 0.75f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, 0, 75, 175, 1.0f, "F52");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S3"),
					xPos, 480, 1, 75, 200, 1.0f, "F53");
			}
		} else {
			if(type < 0.5f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, 0, 50, 175, 1.5f, "F51");
			} else if(type < 0.7f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, 0, 75, 175, 1.0f, "F52");
			} else if(type < 0.9f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S3"),
					xPos, 480, 1, 75, 200, 1.0f, "F53");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S4"),
					xPos, 480, 1, 100, 200, 0.75f, "F54");
			}
		}
		
		enemies[n].setVelY(-enemies[n].getMaxVel());
		enemies[n].setShootTimer(rand.nextFloat() * enemies[n].getShootTime());
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
		pickupAtlas.dispose();
		
		laserShot.dispose();
	}
}
