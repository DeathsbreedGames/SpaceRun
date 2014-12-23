/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * Screen used when the player dies.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.23
 */
public class GameOverScreen extends BaseScreen {
	// Drawing variables
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	// Button variables
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	
	// Constructor:
	public GameOverScreen(AssetManager manager) {
		super("MainMenu", manager);
		
		Preferences prefs = Gdx.app.getPreferences("SpaceRun");
		
		// Set high score
		if(GlobalVars.score > GlobalVars.highScore) {
			GlobalVars.highScore = GlobalVars.score;
			prefs.putInteger("HighScore", GlobalVars.highScore);
			if(Gdx.app.getType() == ApplicationType.Android) {
				GlobalVars.actionResolver.submitScoreGPGS(GlobalVars.highScore);
			}
		}
		prefs.putInteger("KillCount", GlobalVars.killCount);
		prefs.flush();
		
		// Setup draw
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.scale(0.5f);
		
		// Setup buttons
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = manager.get("gfx/ui/buttons.pack", TextureAtlas.class);
		buttonSkin = new Skin(buttonAtlas);
		Gdx.input.setInputProcessor(mainStage);
		
		buttonFont = new BitmapFont();
		buttonFont.scale(0.3f);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("MainMenu-normal");
		buttonStyle.down = buttonSkin.getDrawable("MainMenu-down");
		buttonStyle.over = buttonSkin.getDrawable("MainMenu-hover");
		buttonStyle.font = buttonFont;
		
		TextButton menuButton = new TextButton(GlobalVars.gameBundle.get("mainmenu"), buttonStyle);
		menuButton.setPosition(GlobalVars.width / 2 - menuButton.getWidth() / 2, 10f);
		mainStage.addActor(menuButton);
		menuButton.addListener(new ChangeListener() {
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
		
		// Draw stuff
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		float textXPos;
		textXPos = GlobalVars.width / 2 - font.getBounds(GlobalVars.gameBundle.format("score", GlobalVars.score)).width / 2;
		font.draw(batch, GlobalVars.gameBundle.format("score", GlobalVars.score), textXPos, 350);
		textXPos = GlobalVars.width / 2 - font.getBounds(GlobalVars.gameBundle.format("highscore", GlobalVars.highScore)).width / 2;
		font.draw(batch, GlobalVars.gameBundle.format("highscore", GlobalVars.highScore), textXPos, 300);
		batch.end();
		
		// Draw buttons
		mainStage.act();
		mainStage.draw();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		
		mainStage.dispose();
		buttonFont.dispose();
	}
}
