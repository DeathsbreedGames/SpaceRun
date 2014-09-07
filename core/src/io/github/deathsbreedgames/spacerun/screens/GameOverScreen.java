package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This screen is shown when the player dies.
 * 
 */
public class GameOverScreen extends BaseScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	
	// Constructor:
	public GameOverScreen() {
		super("MainMenu");
		
		if(GlobalVars.score > GlobalVars.highScore) {
			GlobalVars.highScore = GlobalVars.score;
			Preferences prefs = Gdx.app.getPreferences("SpaceRun");
			prefs.putInteger("HighScore", GlobalVars.highScore);
			prefs.flush();
		}
		
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.scale(0.5f);
		
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = new TextureAtlas("gfx/ui/buttons.pack");
		buttonSkin = new Skin(buttonAtlas);
		Gdx.input.setInputProcessor(mainStage);
		
		buttonFont = new BitmapFont();
		buttonFont.scale(0.3f);
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("MainMenu-normal");
		buttonStyle.down = buttonSkin.getDrawable("MainMenu-down");
		buttonStyle.over = buttonSkin.getDrawable("MainMenu-hover");
		buttonStyle.font = buttonFont;
		
		TextButton menuButton = new TextButton("MAIN MENU", buttonStyle);
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
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		float textXPos;
		textXPos = GlobalVars.width / 2 - font.getBounds("SCORE: " + GlobalVars.score).width / 2;
		font.draw(batch, "SCORE: " + GlobalVars.score, textXPos, 350);
		textXPos = GlobalVars.width / 2 - font.getBounds("HIGH SCORE: " + GlobalVars.highScore).width / 2;
		font.draw(batch, "HIGH SCORE: " + GlobalVars.highScore, textXPos, 300);
		batch.end();
		
		mainStage.act();
		mainStage.draw();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
