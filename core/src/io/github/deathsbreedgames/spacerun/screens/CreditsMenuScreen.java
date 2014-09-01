package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
 * Description: This class takes care of the Credits Menu for the game.
 * 
 */
public class CreditsMenuScreen extends BaseScreen {
	// Credits variables
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
		batch = new SpriteBatch();
		titleFont = new BitmapFont();
		titleFont.scale(0.75f);
		textFont = new BitmapFont();
		textFont.scale(0.1f);
		
		// Setup the button variables
		mainStage = new Stage();
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
		TextButton backButton = new TextButton("BACK", buttonStyle);
		backButton.setPosition(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2, 10);
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
		batch.begin();
		titleFont.setColor(1f, 0f, 0f, 1f);
		titleFont.draw(batch, "Credits", 10, 450);
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, "Programming:", 10, 400);
		if(mouseCollides(textFont, "DeathsbreedGames", 20f, 380f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://deathsbreedgames.github.io/");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "DeathsbreedGames", 20, 380);
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, "Graphics:", 10, 350);
		if(mouseCollides(textFont, "MillionthVector", 20f, 330f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("http://millionthvector.blogspot.de");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "MillionthVector", 20, 330);
		textFont.setColor(1f, 1f, 1f, 1f);
		textFont.draw(batch, "This game is Free Software", 10, 200);
		if(mouseCollides(textFont, "Fork me on Github!", 10f, 180f)) {
			textFont.setColor(1f, 1f, 0f, 1f);
			if(mousePressed && !oldMousePressed) Gdx.net.openURI("https://github.com/DeathsbreedGames/SpaceRun");
		} else { textFont.setColor(0f, 0f, 1f, 1f); }
		textFont.draw(batch, "Fork me on Github!", 10, 180);
		batch.end();
		
		// Draw the button
		mainStage.act();
		mainStage.draw();
		
		oldMousePressed = mousePressed;
	}
	
	private boolean mouseCollides(BitmapFont font, String text, float posX, float posY) {
		float mouseX = (float)Gdx.input.getX();
		float mouseY = (float)(Gdx.graphics.getHeight() - Gdx.input.getY());
		
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
