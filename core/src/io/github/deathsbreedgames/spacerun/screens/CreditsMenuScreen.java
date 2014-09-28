package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
 * Description: This class takes care of the Credits Menu for the game.
 * 
 */
public class CreditsMenuScreen extends BaseScreen {
	// Credits variables
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont titleFont;
	private BitmapFont textFont;
	
	// Button variables
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	
	private boolean oldMousePressed = false;
	
	// Constructor:
	public CreditsMenuScreen() {
		super("MainMenu");
		
		// Setup the credits variables
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.scale(0.75f);
		textFont = new BitmapFont();
		textFont.scale(0.1f);
		
		// Setup the button variables
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
		
		// Create the fonts
		TextButton backButton = new TextButton(GlobalVars.gameBundle.get("back"), buttonStyle);
		backButton.setPosition(GlobalVars.width / 2 - backButton.getWidth() / 2, 10f);
		mainStage.addActor(backButton);
		backButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				// Switch back to the main menu
				setNextScreen("MainMenu");
				setDone(true);
			}
		});
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		boolean mousePressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
		
		// Draw the credits
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		titleFont.setColor(1f, 0f, 0f, 1f);
		titleFont.draw(batch, GlobalVars.gameBundle.get("credits"), 10f, 450f);
		
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, GlobalVars.gameBundle.get("programmingcredits"), 10f, 400f);
		if(mouseCollides(textFont, "DeathsbreedGames", 20f, 380f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://deathsbreedgames.github.io/");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "DeathsbreedGames", 20f, 380f);
		
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, GlobalVars.gameBundle.get("graphicscredits"), 10f, 350f);
		if(mouseCollides(textFont, "MillionthVector", 20f, 330f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://millionthvector.blogspot.de");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "MillionthVector", 20f, 330f);
		
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, GlobalVars.gameBundle.get("soundcredits"), 10f, 300f);
		if(mouseCollides(textFont, "dklon", 20f, 280f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://opengameart.org/users/dklon");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "dklon", 20f, 280f);
		
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, GlobalVars.gameBundle.get("musiccredits"), 10f, 250f);
		if(mouseCollides(textFont, "Android128", 20f, 230f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://opengameart.org/users/android128");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "Android128", 20f, 230f);
		
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, GlobalVars.gameBundle.get("freesoftware"), 10f, 150f);
		if(mouseCollides(textFont, "Fork me on Github!", 10f, 130f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("https://github.com/DeathsbreedGames/SpaceRun");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "Fork me on Github!", 10f, 130f);
		
		textFont.setColor(1f, 1f, 1f, 1f);
		String version = "v0.6";
		float versionX = GlobalVars.width - (textFont.getBounds(version).width + 5);
		textFont.draw(batch, version, versionX, 465f);
		batch.end();
		
		// Draw the button
		mainStage.act();
		mainStage.draw();
		
		oldMousePressed = mousePressed;
	}
	
	// Check if the mouse collides with a certain text
	private boolean mouseCollides(BitmapFont font, String text, float posX, float posY) {
		float mouseX = GlobalVars.getTouchX();
		float mouseY = (float)GlobalVars.height - GlobalVars.getTouchY();
		
		if(mouseX > posX && mouseX < font.getBounds(text).width + posX &&
			mouseY > posY - font.getBounds(text).height && mouseY < posY) {

			return true;
		} else {
			return false;
		}
	}
	
	// Dispose:
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
