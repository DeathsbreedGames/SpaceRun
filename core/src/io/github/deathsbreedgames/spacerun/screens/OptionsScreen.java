/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
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
 * A screen to edit options such as sound effects, music,
 * data storage, etc.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class OptionsScreen extends BaseScreen {
	// Variable to access preferences
	private Preferences prefs;
	
	// Camera and drawing stuff
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont titleFont;
	private BitmapFont textFont;
	
	// Buttons
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	private ImageButton soundControl, musicControl;
	
	// Constructor:
	public OptionsScreen(AssetManager manager) {
		super("MainMenu", manager);
		
		// Get the preferences from file
		prefs = Gdx.app.getPreferences("SpaceRun");
		
		// Drawing stuff
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0);
		camera.update();
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.scale(0.75f);
		textFont = new BitmapFont();
		textFont.scale(0.5f);
		
		// Button stuff
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = manager.get("gfx/ui/buttons.pack", TextureAtlas.class);
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
		
		TextButton clearButton = new TextButton(GlobalVars.gameBundle.get("cleardata"), buttonStyle);
		clearButton.setPosition(10f, 145f);
		mainStage.addActor(clearButton);
		clearButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				prefs.clear();
				prefs.flush();
				setNextScreen("Splash");
				setDone(true);
			}
		});
		
		TextButton backButton = new TextButton(GlobalVars.gameBundle.get("back"), buttonStyle);
		backButton.setPosition(GlobalVars.width / 2 - backButton.getWidth() / 2, 10f);
		mainStage.addActor(backButton);
		backButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				setNextScreen("MainMenu");
				setDone(true);
			}
		});
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Draw
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		titleFont.setColor(1f, 0f, 0f, 1f);
		titleFont.draw(batch, GlobalVars.gameBundle.get("options"), 10f, 450f);
		textFont.draw(batch, GlobalVars.gameBundle.get("sound"), 100f, 380f);
		textFont.draw(batch, GlobalVars.gameBundle.get("music"), 100f, 320f);
		textFont.draw(batch, GlobalVars.gameBundle.format("highscore", GlobalVars.highScore), 10f, 230f);
		textFont.draw(batch, GlobalVars.gameBundle.format("kills", GlobalVars.killCount), 10, 205f);
		batch.end();
		
		// Draw buttons
		mainStage.act();
		mainStage.draw();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		titleFont.dispose();
		textFont.dispose();
		
		mainStage.dispose();
		buttonFont.dispose();
	}
}
