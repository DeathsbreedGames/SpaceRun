package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
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
	// Button variables
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	
	// Draw variables
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas spaceshipAtlas;
	private TextureAtlas bulletAtlas;
	private TextureAtlas pickupAtlas;
	private BitmapFont font;
	
	// Entity variables
	private Player player;
	private Enemy[] enemies;
	private final int NUM_ENEMIES = 30;
	private int currentMaxEnemies;
	private int currentEnemies;
	private Bullet[] bullets;
	private final int NUM_BULLETS = 100;
	private Pickup pickup;
	private float pickupTimer;
	
	// Pickup variables
	private float rapidTimer;
	private boolean speed;
	private float speedTimer;
	private float invTimer;
	
	// Effect pools
	private ParticleEffectPool explosionEffectPool;
	private Array<PooledEffect> effects = new Array<PooledEffect>();
	
	// Stars
	private Star[] stars;
	private final int NUM_STARS = 100;
	private ShapeRenderer shapeRenderer;
	
	// Audio variables
	private Sound laserShot;
	private Sound explode;
	
	// Constructor:
	public GameScreen(AssetManager manager) {
		super("Splash", manager);
		
		// Create buttons
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = manager.get("gfx/ui/buttons.pack", TextureAtlas.class);
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
		
		// Setup draw stuff
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		
		batch = new SpriteBatch();
		spaceshipAtlas = manager.get("gfx/sprites/spaceships.pack", TextureAtlas.class);
		bulletAtlas = manager.get("gfx/sprites/bullets.pack", TextureAtlas.class);
		pickupAtlas = manager.get("gfx/sprites/pickups.pack", TextureAtlas.class);
		font = new BitmapFont();
		font.scale(0.01f);
		
		// Create ship
		if(GlobalVars.ship == 0) {
			player = new Player(spaceshipAtlas.findRegion("bluedestroyer"), 160, 50, 1, 1000, 250, 0.5f);
		} else if(GlobalVars.ship == 1) {
			player = new Player(spaceshipAtlas.findRegion("bluecarrier"), 160, 50, 0, 2000, 250, 0.5f);
		} else {
			player = new Player(spaceshipAtlas.findRegion("bluecruiser"), 160, 50, 0, 1000, 500, 0.5f);
		}
		// Setup enemies
		enemies = new Enemy[NUM_ENEMIES];
		for(int i = 0; i < NUM_ENEMIES; i++) { enemies[i] = null; }
		currentMaxEnemies = 1;
		currentEnemies = 0;
		// Setup bullets
		bullets = new Bullet[NUM_BULLETS];
		for(int i = 0; i < NUM_BULLETS; i++) { bullets[i] = null; }
		
		// Setup pickups
		pickup = null;
		pickupTimer = 0;
		
		rapidTimer = 0;
		speed = false;
		speedTimer = 0;
		invTimer = 0;
		
		// Setup Particle Effects
		ParticleEffect explosionEffect = new ParticleEffect();
		explosionEffect.load(Gdx.files.internal("gfx/particles/Explosion.p"), Gdx.files.internal("gfx/particles/"));
		explosionEffectPool = new ParticleEffectPool(explosionEffect, 1, 2);
		
		// Setup stars
		stars = new Star[NUM_STARS];
		for(int i = 0; i < NUM_STARS; i++) {
			RandomXS128 rand = new RandomXS128();
			stars[i] = new Star(rand.nextFloat() * GlobalVars.width, rand.nextFloat() * GlobalVars.height);
		}
		shapeRenderer = new ShapeRenderer();
		
		// Setup sound
		laserShot = Gdx.audio.newSound(Gdx.files.internal("sfx/laser5.mp3"));
		explode = Gdx.audio.newSound(Gdx.files.internal("sfx/explosion.mp3"));
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
		
		// Pickup creation
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
			} else if(probType < 0.7f) {
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
		// Update stars
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		for(int i = 0; i < NUM_STARS; i++) stars[i].update(shapeRenderer, delta);
		shapeRenderer.end();
		
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
					PooledEffect effect = explosionEffectPool.obtain();
					effect.setPosition(enemies[i].getPosX(), enemies[i].getPosY());
					effects.add(effect);
					if(GlobalVars.soundOn) explode.play();
					enemies[i] = null;
					GlobalVars.killCount++;
				} else if(enemies[i].getBoundingRectangle().overlaps(player.getBoundingRectangle())) {
					if(!player.isInvincible()) player.incShields(-100);
					if(!player.isInvincible()) player.setDoubleShot(false);
					PooledEffect effect = explosionEffectPool.obtain();
					effect.setPosition(enemies[i].getPosX(), enemies[i].getPosY());
					effects.add(effect);
					if(GlobalVars.soundOn) explode.play();
					enemies[i] = null;
					GlobalVars.killCount++;
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
					player.incShields(100);
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
		
		// Update particles
		for(int i = 0; i < effects.size; i++) {
			PooledEffect effect = effects.get(i);
			effect.draw(batch, delta);
			if(effect.isComplete()) {
				effect.free();
				effects.removeIndex(i);
			}
		}
		
		font.draw(batch, GlobalVars.gameBundle.format("shields", player.getShields()), 10, 470);
		font.draw(batch, GlobalVars.gameBundle.format("score", player.getScore()), 10, 450);
		batch.end();
		
		mainStage.act();
		mainStage.draw();
	}
	
	// Gets TextureRegion for bullet
	private TextureRegion getBulletImg(Ship ship) {
		if(ship.getWeapon() == 0) {
			return bulletAtlas.findRegion("NormalBullet-green");
		} else if(ship.getWeapon() == 1) {
			return bulletAtlas.findRegion("NormalBullet-blue");
		} else if(ship.getWeapon() >= 2) {
			return bulletAtlas.findRegion("NormalBullet-red");
		} else {
			return bulletAtlas.findRegion("NormalBullet-green");
		}
	}
	
	// Gets the damage depending on weapon
	private int getDmg(Ship ship) {
		if(ship.getWeapon() == 0) return 50;
		else if(ship.getWeapon() == 1) return 100;
		else if(ship.getWeapon() >= 2) return 150;
		else return 100;
	}
	
	// Creates an enemy
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
					xPos, 480, 0, 100, 175, 1.0f, "F52");
			}
		} else if(player.getScore() < 1000) {
			if(type < 0.5f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S1"),
					xPos, 480, 0, 50, 175, 1.5f, "F51");
			} else if(type < 0.75f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S2"),
					xPos, 480, 0, 100, 175, 1.0f, "F52");
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
					xPos, 480, 0, 100, 175, 1.0f, "F52");
			} else if(type < 0.9f) {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S3"),
					xPos, 480, 1, 100, 200, 1.0f, "F53");
			} else {
				enemies[n] = new Enemy(spaceshipAtlas.findRegion("F5S4"),
					xPos, 480, 1, 300, 200, 0.75f, "F54");
			}
		}
		
		overlapLoop:
		for(int i = 0; i < NUM_ENEMIES; i++) {
			if(i != n && enemies[i] != null) {
				if(enemies[n].getBoundingRectangle().overlaps(enemies[i].getBoundingRectangle())) {
					enemies[n] = null;
					break overlapLoop;
				}
			}
		}
		
		if(enemies[n] != null) {
			enemies[n].setVelY(-enemies[n].getMaxVel());
			enemies[n].setShootTimer(rand.nextFloat() * enemies[n].getShootTime());
		}
	}
	
	// Add to the score of the player
	private void score(Enemy enemy) {
		if(enemy.getType().equals("F51")) player.incScore(10);
		else if(enemy.getType().equals("F52")) player.incScore(25);
		else if(enemy.getType().equals("F53")) player.incScore(50);
		else if(enemy.getType().equals("F54")) player.incScore(100);
	}
	
	// The star class used earlier
	public class Star {
		// Position variables
		private float x;
		private float y;
		
		// Constructor:
		public Star(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		// Update:
		public void update(ShapeRenderer renderer, float delta) {
			y -= 200f * delta;
			if(getY() <= 0) {
				RandomXS128 rand = new RandomXS128();
				setY(GlobalVars.height);
				setX(rand.nextFloat() * GlobalVars.width);
			}
			
			renderer.setColor(1f, 1f, 1f, 1f);
			renderer.rect(x, y, 1, 1);
		}
		
		// Getter methods:
		public float getX() { return x; }
		public float getY() { return y; }
		
		// Setter methods:
		public void setX(float x) { this.x = x; }
		public void setY(float y) { this.y = y; }
	}
	
	// Dispose:
	@Override
	public void dispose() {
		mainStage.dispose();
		
		batch.dispose();
		
		shapeRenderer.dispose();
		
		for(int i = 0; i < effects.size; i++) {
			effects.get(i).free();
			effects.get(i).dispose();
		}
		effects.clear();
		
		laserShot.dispose();
	}
}
