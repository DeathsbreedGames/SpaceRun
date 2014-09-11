package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
 * Description: This class allows the user to change the available
 * options (such as sound and music).
 * 
 */
public class OptionsScreen extends BaseScreen {
	private Stage mainStage;
	private TextureAtlas buttonAtlas;
	private Skin buttonSkin;
	private BitmapFont buttonFont;
	
	public OptionsScreen() {
		super("MainMenu");
		
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
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		mainStage.act();
		mainStage.draw();
	}
	
	@Override
	public void dispose() {
		mainStage.dispose();
		buttonAtlas.dispose();
		buttonSkin.dispose();
		buttonFont.dispose();
	}
}
