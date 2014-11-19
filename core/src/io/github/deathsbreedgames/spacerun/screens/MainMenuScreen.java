package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class takes care of the Main Menu.
 * 
 */
public class MainMenuScreen extends BaseScreen {
	// SpaceRun logo variables:
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture logo;
	
	// TextButton variables
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	
	// Constructor:
	public MainMenuScreen() {
		super("Splash");
		
		// Setup logo
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		logo = new Texture("gfx/space-run.png");
		
		// Setup TextButtonStyle
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = new TextureAtlas("gfx/ui/buttons.pack");
		buttonSkin = new Skin(buttonAtlas);
		Gdx.input.setInputProcessor(mainStage);
		
		buttonFont = new BitmapFont();
		buttonFont.scale(0.5f);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("MainMenu-normal");
		buttonStyle.down = buttonSkin.getDrawable("MainMenu-down");
		buttonStyle.over = buttonSkin.getDrawable("MainMenu-hover");
		buttonStyle.font = buttonFont;
		
		// Create the TextButtons
		TextButton playButton = new TextButton(GlobalVars.gameBundle.get("play"), buttonStyle);
		playButton.setPosition(GlobalVars.width / 2 - playButton.getWidth() / 2, 250f);
		mainStage.addActor(playButton);
		playButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				// Change to the Game
				setNextScreen("ShipSelect");
				setDone(true);
			}
		});
		
		TextButton helpButton = new TextButton(GlobalVars.gameBundle.get("help"), buttonStyle);
		helpButton.setPosition(GlobalVars.width / 2 - helpButton.getWidth() / 2, 210f);
		mainStage.addActor(helpButton);
		helpButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				setNextScreen("Help");
				setDone(true);
			}
		});
		
		TextButton optionsButton = new TextButton(GlobalVars.gameBundle.get("options"), buttonStyle);
		optionsButton.setPosition(GlobalVars.width / 2 - optionsButton.getWidth() / 2, 170f);
		mainStage.addActor(optionsButton);
		optionsButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				setNextScreen("Options");
				setDone(true);
			}
		});
		
		TextButton creditsButton = new TextButton(GlobalVars.gameBundle.get("credits"), buttonStyle);
		creditsButton.setPosition(GlobalVars.width / 2 - creditsButton.getWidth() / 2, 130f);
		mainStage.addActor(creditsButton);
		creditsButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				// Change to the Credits Menu
				setNextScreen("CreditsMenu");
				setDone(true);
			}
		});
		
		// Exit button cannot be used for WebGL
		if(Gdx.app.getType() != ApplicationType.WebGL) {
			TextButton quitButton = new TextButton(GlobalVars.gameBundle.get("exit"), buttonStyle);
			quitButton.setPosition(GlobalVars.width / 2 - quitButton.getWidth() / 2, 90f);
			mainStage.addActor(quitButton);
			quitButton.addListener(new ChangeListener() {
				public void changed(ChangeEvent e, Actor a) {
					// Quit
					Gdx.app.exit();
				}
			});
		}

		if(!GlobalVars.actionResolver.getSignedInGPGS()) GlobalVars.actionResolver.loginGPGS();
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Draw logo
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(logo, GlobalVars.width / 2 - 80, 310f, 160f, 160f);
		batch.end();
		
		// Update the stage
		mainStage.act();
		mainStage.draw();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		logo.dispose();
		
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
