package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class allows the user to change the available
 * options (such as sound and music).
 * 
 */
public class OptionsScreen extends BaseScreen {
	private Preferences prefs;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont titleFont;
	private BitmapFont textFont;
	
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	private ImageButton soundControl, musicControl;
	
	public OptionsScreen() {
		super("MainMenu");
		
		prefs = Gdx.app.getPreferences("SpaceRun");
		
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0);
		camera.update();
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.scale(0.75f);
		textFont = new BitmapFont();
		textFont.scale(0.5f);
		
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = new TextureAtlas("gfx/ui/buttons.pack");
		buttonSkin = new Skin(buttonAtlas);
		Gdx.input.setInputProcessor(mainStage);
		
		ImageButtonStyle imageButtonStyle = new ImageButtonStyle();
		imageButtonStyle.imageUp = buttonSkin.getDrawable("Switch-on");
		imageButtonStyle.imageChecked = buttonSkin.getDrawable("Switch-off");
		
		ImageButtonStyle imageButtonStyleInv = new ImageButtonStyle();
		imageButtonStyleInv.imageUp = buttonSkin.getDrawable("Switch-off");
		imageButtonStyleInv.imageChecked = buttonSkin.getDrawable("Switch-on");
		
		if(GlobalVars.soundOn) soundControl = new ImageButton(imageButtonStyle);
		else soundControl = new ImageButton(imageButtonStyleInv);
		soundControl.setPosition(10f, 350f);
		mainStage.addActor(soundControl);
		soundControl.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				if(GlobalVars.soundOn) { GlobalVars.soundOn = false; }
				else { GlobalVars.soundOn = true; }
				prefs.putBoolean("SoundOff", !GlobalVars.soundOn);
				prefs.flush();
			}
		});
		
		if(GlobalVars.musicOn) musicControl = new ImageButton(imageButtonStyle);
		else musicControl = new ImageButton(imageButtonStyleInv);
		musicControl.setPosition(10f, 290f);
		mainStage.addActor(musicControl);
		musicControl.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				if(GlobalVars.musicOn) { GlobalVars.musicOn = false; }
				else { GlobalVars.musicOn = true; }
				prefs.putBoolean("MusicOff", !GlobalVars.musicOn);
				prefs.flush();
			}
		});
		
		buttonFont = new BitmapFont();
		buttonFont.scale(0.3f);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("MainMenu-normal");
		buttonStyle.down = buttonSkin.getDrawable("MainMenu-down");
		buttonStyle.over = buttonSkin.getDrawable("MainMenu-hover");
		buttonStyle.font = buttonFont;
		
		TextButton clearButton = new TextButton("CLEAR DATA", buttonStyle);
		clearButton.setPosition(10f, 160f);
		mainStage.addActor(clearButton);
		clearButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				prefs.putInteger("HighScore", 0);
				prefs.putBoolean("SoundOff", false);
				prefs.putBoolean("MusicOff", false);
				prefs.flush();
				setNextScreen("Splash");
				setDone(true);
			}
		});
		
		TextButton backButton = new TextButton("BACK", buttonStyle);
		backButton.setPosition(GlobalVars.width / 2 - backButton.getWidth() / 2, 10f);
		mainStage.addActor(backButton);
		backButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				setNextScreen("MainMenu");
				setDone(true);
			}
		});
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		titleFont.setColor(1f, 0f, 0f, 1f);
		titleFont.draw(batch, "OPTIONS", 10f, 450f);
		textFont.draw(batch, "SOUND", 100f, 380f);
		textFont.draw(batch, "MUSIC", 100f, 320f);
		textFont.draw(batch, "HIGH SCORE: " + GlobalVars.highScore, 10f, 230f);
		batch.end();
		
		mainStage.act();
		mainStage.draw();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		titleFont.dispose();
		textFont.dispose();
		
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
