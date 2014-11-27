package io.github.deathsbreedgames.spacerun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;

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
	private AssetManager manager;
	private Music music;
	
	public SpaceRun(ActionResolver actionResolver) {
		GlobalVars.actionResolver = actionResolver;
	}
	public SpaceRun() { }
	
	// Create:
	@Override
	public void create() {
		manager = new AssetManager();
		setScreen(new LoadingScreen(manager));
		
		// Create the music
		music = Gdx.audio.newMusic(Gdx.files.internal("sfx/Android128_-_At_Last.mp3"));
		music.setLooping(true);
	}
	
	// Update:
	@Override
	public void render () {
		BaseScreen currentScreen = (BaseScreen)super.getScreen();
		
		// Update the screen
		currentScreen.render(Gdx.graphics.getDeltaTime());
		
		// Switch screens if necessary
		if(currentScreen.isDone()) {
			currentScreen.dispose();
			if(currentScreen.getNextScreen().equals("Loading")) {
				setScreen(new LoadingScreen(manager));
			} else if(currentScreen.getNextScreen().equals("Splash")) {
				setScreen(new SplashScreen(manager));
			} else if(currentScreen.getNextScreen().equals("MainMenu")) {
				setScreen(new MainMenuScreen(manager));
			} else if(currentScreen.getNextScreen().equals("Help")) {
				setScreen(new HelpScreen());
			} else if(currentScreen.getNextScreen().equals("Options")) {
				setScreen(new OptionsScreen(manager));
			} else if(currentScreen.getNextScreen().equals("CreditsMenu")) {
				setScreen(new CreditsMenuScreen(manager));
			} else if(currentScreen.getNextScreen().equals("ShipSelect")) {
				setScreen(new ShipSelectScreen());
			} else if(currentScreen.getNextScreen().equals("Game")) {
				setScreen(new GameScreen());
			} else if(currentScreen.getNextScreen().equals("GameOver")) {
				setScreen(new GameOverScreen(manager));
			} else {
				setScreen(new SplashScreen(manager));
			}
		}
		
		// Play music if music should be played
		if(currentScreen instanceof SplashScreen || currentScreen instanceof LoadingScreen) {
			if(music.isPlaying()) music.stop();
		} else {
			if(!music.isPlaying() && GlobalVars.musicOn) music.play();
			else if(music.isPlaying() && !GlobalVars.musicOn) music.stop();
		}
	}
	
	// Dispose:
	@Override
	public void dispose() {
		music.dispose();
		manager.dispose();
		super.getScreen().dispose();
		super.dispose();
	}
}
