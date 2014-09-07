package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
			player = new Player(spaceshipAtlas.findRegion("bluedestroyer"), 160, 50, "bluelaser", 200, 250);
		} else if(GlobalVars.ship == 1) {
			player = new Player(spaceshipAtlas.findRegion("bluecarrier"), 160, 50, "greenlaser", 400, 250);
		} else {
			player = new Player(spaceshipAtlas.findRegion("bluecruiser"), 160, 50, "greenlaser", 200, 500);
		}
		bullets = new Bullet[NUM_BULLETS];
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
					bullets[i] = new Bullet(getBulletImg(), player.getPosX(),
						player.getPosY() + 35, getDmg());
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
		font.draw(batch, "Shields: " + player.getShields(), 10, 470);
		batch.end();
		
		mainStage.act();
		mainStage.draw();
	}
	
	private TextureRegion getBulletImg() {
		if(player.getWeapon().equals("greenlaser")) {
			return bulletAtlas.findRegion("NormalBullet-green");
		} else if(player.getWeapon().equals("bluelaser")) {
			return bulletAtlas.findRegion("NormalBullet-blue");
		} else if(player.getWeapon().equals("redlaser")) {
			return bulletAtlas.findRegion("NormalBullet-red");
		} else {
			return bulletAtlas.findRegion("NormalBullet-green");
		}
	}
	
	private int getDmg() {
		if(player.getWeapon().equals("greenlaser")) return 25;
		else if(player.getWeapon().equals("bluelaser")) return 50;
		else if(player.getWeapon().equals("redlaser")) return 100;
		else return 25;
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
