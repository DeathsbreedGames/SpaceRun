package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
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
 * Description: This class is where the player chooses which ship s/he
 * wants to use.
 * 
 */
public class ShipSelectScreen extends BaseScreen {
	// Image variables
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TextureAtlas shipAtlas;
	private BitmapFont statFont;
	
	// Button variables
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	private ImageButton chooseShip[];
	
	// Constructor:
	public ShipSelectScreen() {
		super("Splash");
		
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
		shipAtlas = new TextureAtlas("gfx/sprites/spaceships.pack");
		statFont = new BitmapFont();
		statFont.scale(0.05f);
		
		// Setup the button variables
		mainStage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));
		buttonAtlas = new TextureAtlas("gfx/ui/buttons.pack");
		buttonSkin = new Skin(buttonAtlas);
		Gdx.input.setInputProcessor(mainStage);
		
		buttonFont = new BitmapFont();
		buttonFont.scale(0.3f);
		
		ImageButtonStyle imageButtonStyle = new ImageButtonStyle();
		imageButtonStyle.imageUp = buttonSkin.getDrawable("UncheckedButton");
		imageButtonStyle.imageChecked = buttonSkin.getDrawable("CheckedButton");
		
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("MainMenu-normal");
		buttonStyle.down = buttonSkin.getDrawable("MainMenu-down");
		buttonStyle.over = buttonSkin.getDrawable("MainMenu-hover");
		buttonStyle.font = buttonFont;
		
		// Create the buttons
		chooseShip = new ImageButton[3];
		
		chooseShip[0] = new ImageButton(imageButtonStyle);
		chooseShip[0].setCenterPosition((float)GlobalVars.width * 0.25f, 200f);
		chooseShip[0].addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				GlobalVars.ship = 0;
			}
		});
		
		chooseShip[1] = new ImageButton(imageButtonStyle);
		chooseShip[1].setCenterPosition((float)GlobalVars.width * 0.5f, 200f);
		chooseShip[1].addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				GlobalVars.ship = 1;
			}
		});
		
		chooseShip[2] = new ImageButton(imageButtonStyle);
		chooseShip[2].setCenterPosition((float)GlobalVars.width * 0.75f, 200f);
		chooseShip[2].addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				GlobalVars.ship = 2;
			}
		});
		
		for(int i = 0; i < chooseShip.length; i++) mainStage.addActor(chooseShip[i]);
		chooseShip[0].setChecked(true);
		
		TextButton playButton = new TextButton("PLAY", buttonStyle);
		playButton.setPosition(GlobalVars.width / 2 - playButton.getWidth() / 2, 60f);
		mainStage.addActor(playButton);
		playButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent e, Actor a) {
				// Switch to the game
				setNextScreen("Game");
				setDone(true);
			}
		});
		
		TextButton backButton = new TextButton("BACK", buttonStyle);
		backButton.setPosition(GlobalVars.width / 2 - backButton.getWidth() / 2,
			playButton.getY() - (backButton.getHeight() + 5f));
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
		
		TextureRegion destroyer = (TextureRegion)shipAtlas.findRegion("bluedestroyer");
		TextureRegion carrier = (TextureRegion)shipAtlas.findRegion("bluecarrier");
		TextureRegion cruiser = (TextureRegion)shipAtlas.findRegion("bluecruiser");
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(destroyer, GlobalVars.width * 0.25f - 22.5f, 225f);
		statFont.drawMultiLine(batch, "ATK 2\nDEF 1\nSPD 1", GlobalVars.width * 0.25f - 22.5f, 375f);
		batch.draw(carrier, GlobalVars.width * 0.5f - 22.5f, 225f);
		statFont.drawMultiLine(batch, "ATK 1\nDEF 2\nSPD 1", GlobalVars.width * 0.5f - 22.5f, 375f);
		batch.draw(cruiser, GlobalVars.width * 0.75f - 12f, 225f);
		statFont.drawMultiLine(batch, "ATK 1\nDEF 1\nSPD 2", GlobalVars.width * 0.75f - 24f, 375f);
		batch.end();
		
		for(int i = 0; i < chooseShip.length; i++) {
			if(i != GlobalVars.ship) chooseShip[i].setChecked(false);
			else chooseShip[i].setChecked(true);
		}
		
		mainStage.act();
		mainStage.draw();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
		shipAtlas.dispose();
		statFont.dispose();
		
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
