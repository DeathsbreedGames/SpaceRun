package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
		batch = new SpriteBatch();
		logo = new Texture("gfx/space-run.png");
		
		// Setup TextButtonStyle
		mainStage = new Stage();
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
		buttonStyle.fontColor = Color.WHITE;
		
		// Create the TextButtons
		TextButton playButton = new TextButton("PLAY", buttonStyle);
		playButton.setPosition(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, 250);
		mainStage.addActor(playButton);
		playButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				// Change to the game
				setNextScreen("Game");
				setDone(true);
			}
		});
		
		TextButton quitButton = new TextButton("EXIT", buttonStyle);
		quitButton.setPosition(Gdx.graphics.getWidth() / 2 - quitButton.getWidth() / 2, 200);
		mainStage.addActor(quitButton);
		quitButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				Gdx.app.exit();
			}
		});
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Draw logo
		batch.begin();
		batch.draw(logo, Gdx.graphics.getWidth() / 2 - 80, 310, 160, 160);
		batch.end();
		
		// Update the stage
		mainStage.act();
		mainStage.draw();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
