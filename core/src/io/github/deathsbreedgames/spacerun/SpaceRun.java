package io.github.deathsbreedgames.spacerun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import io.github.deathsbreedgames.spacerun.GlobalVars;
import io.github.deathsbreedgames.spacerun.screens.*;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This is the starting game class.
 * 
 */
public class SpaceRun extends Game {
	// Create:
	@Override
	public void create () {
		setScreen(new SplashScreen());
		Preferences prefs = Gdx.app.getPreferences("SpaceRun");
		GlobalVars.highScore = prefs.getInteger("HighScore");
	}
	
	// Update:
	@Override
	public void render () {
		BaseScreen currentScreen = (BaseScreen)super.getScreen();
		
		currentScreen.render(Gdx.graphics.getDeltaTime());
		
		if(currentScreen.isDone()) {
			currentScreen.dispose();
			if(currentScreen.getNextScreen().equals("Splash")) {
				setScreen(new SplashScreen());
			} else if(currentScreen.getNextScreen().equals("MainMenu")) {
				setScreen(new MainMenuScreen());
			} else if(currentScreen.getNextScreen().equals("CreditsMenu")) {
				setScreen(new CreditsMenuScreen());
			} else if(currentScreen.getNextScreen().equals("ShipSelect")) {
				setScreen(new ShipSelectScreen());
			} else if(currentScreen.getNextScreen().equals("Game")) {
				setScreen(new GameScreen());
			} else if(currentScreen.getNextScreen().equals("GameOver")) {
				setScreen(new GameOverScreen());
			} else {
				setScreen(new SplashScreen());
			}
		}
	}
	
	// Dispose:
	@Override
	public void dispose() {
		super.getScreen().dispose();
		super.dispose();
	}
}
