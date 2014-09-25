package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
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
 * Description: This class shows how to play the game. Just some simple
 * useful information.
 * 
 */
public class HelpScreen extends BaseScreen {
	// Help variables
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont titleFont;
	private BitmapFont textFont;
	private TextureAtlas pickupAtlas;
	
	// Button variables
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	
	// Constructor:
	public HelpScreen() {
		super("MainMenu");
		
		// Help stuff
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0);
		camera.update();
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.scale(0.75f);
		textFont = new BitmapFont();
		textFont.scale(0.1f);
		pickupAtlas = new TextureAtlas("gfx/sprites/pickups.pack");
		
		// Button stuff
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
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// Draw help stuff
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		titleFont.setColor(1f, 0f, 0f, 1f);
		titleFont.draw(batch, "HELP", 10f, 450f);
		textFont.drawWrapped(batch, "Your objective is to get the highest score possible. You can " +
			"see your shield health in the upper-left corner of the screen (along with your score)." +
			"To quit the game, just click/touch the 'X' button in the upper-right corner of the screen.", 10f, 400f, 300f);
		
		batch.draw(pickupAtlas.findRegion("shield"), 10f, 235f);
		textFont.draw(batch, "Repairs", 45f, 257f);
		batch.draw(pickupAtlas.findRegion("full-shield"), 10f, 200f);
		textFont.draw(batch, "Invincibility", 45f, 222f);
		batch.draw(pickupAtlas.findRegion("double-shot"), 10f, 165f);
		textFont.draw(batch, "Double-Shot", 45f, 187f);
		batch.draw(pickupAtlas.findRegion("rapid-fire"), 144f, 235f);
		textFont.draw(batch, "Rapid Fire", 179f, 257f);
		batch.draw(pickupAtlas.findRegion("weapon-upgrade"), 144, 200f);
		textFont.draw(batch, "Upgrade Weapon", 179f, 222f);
		batch.draw(pickupAtlas.findRegion("speed"), 144f, 165f);
		textFont.draw(batch, "Speed Boost", 179f, 187f);
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
		pickupAtlas.dispose();
		
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
