/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * A loading screen at the beginning which loads all necessary
 * assets for the game at the start.
 * 
 * @author Nicolás A. Ortega, Mats Svensson (original code under Unlicense)
 * @version 14.12.22
 */
public class LoadingScreen extends BaseScreen {
	private Stage stage;

	private OrthographicCamera camera;

	private Image logo;
	private Image loadingFrame;
	private Image loadingBarHidden;
	private Image screenBg;
	private Image loadingBg;

	private float startX, endX;
	private float percent;

	private Actor loadingBar;

	// Constructor:
	public LoadingScreen(AssetManager manager) {
		super("Loading", manager);
		// Tell the manager to load assets for the loading screen
		manager.load("gfx/loading/loading.pack", TextureAtlas.class);
		// Wait until they are finished loading
		manager.finishLoading();

		// Setup the camera
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0);
		camera.update();

		// Initialize the stage where we will place everything
		//stage = new Stage();
		stage = new Stage(new StretchViewport(GlobalVars.width, GlobalVars.height));

		// Get our textureatlas from the manager
		TextureAtlas atlas = manager.get("gfx/loading/loading.pack", TextureAtlas.class);

		// Grab the regions from the atlas and create some images
		logo = new Image(atlas.findRegion("loading-text"));
		loadingFrame = new Image(atlas.findRegion("loading-frame"));
		loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
		screenBg = new Image(atlas.findRegion("screen-bg"));
		loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

		// Or if you only need a static bar, you can do
		loadingBar = new Image(atlas.findRegion("loading-bar1"));

		// Add all the actors to the stage
		stage.addActor(screenBg);
		stage.addActor(loadingBar);
		stage.addActor(loadingBg);
		stage.addActor(loadingBarHidden);
		stage.addActor(loadingFrame);
		stage.addActor(logo);

		screenBg.setSize(GlobalVars.width, GlobalVars.height);
		logo.setX((GlobalVars.width - logo.getWidth()) / 2);
		logo.setY((GlobalVars.height - logo.getHeight()) / 2 + 100);
		loadingFrame.setX((GlobalVars.width - loadingFrame.getWidth()) / 2);
		loadingFrame.setY((GlobalVars.height - loadingFrame.getHeight()) / 2);
		loadingBar.setX(loadingFrame.getX() + 15);
		loadingBar.setY(loadingFrame.getY() + 5);
		loadingBarHidden.setX(loadingBar.getX() + 35);
		loadingBarHidden.setY(loadingBar.getY() - 3);
		startX = loadingBarHidden.getX();
		endX = 440;
		loadingBg.setSize(450, 50);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setY(loadingBarHidden.getY() + 3);

		// Add everything to be loaded, for instance:
		manager.load("gfx/deathsbreedgames/logo.png", Texture.class);
		manager.load("gfx/space-run.png", Texture.class);
		manager.load("gfx/ui/buttons.pack", TextureAtlas.class);
		manager.load("gfx/sprites/pickups.pack", TextureAtlas.class);
		manager.load("gfx/sprites/spaceships.pack", TextureAtlas.class);
		manager.load("gfx/sprites/bullets.pack", TextureAtlas.class);
	}

	/*@Override
	public void resize(int width, int height) {
		// Set our screen to always be XXX x 480 in size
		//width = 480 * width / height;
		width = 320;
		height = 480;
		stage.getViewport().update(width , height, false);

		// Make the background fill the screen
		screenBg.setSize(width, height);

		// Place the logo in the middle of the screen and 100 px up
		logo.setX((width - logo.getWidth()) / 2);
		logo.setY((height - logo.getHeight()) / 2 + 100);

		// Place the loading frame in the middle of the screen
		loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
		loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);

		// Place the loading bar at the same spot as the frame, adjusted a few px
		loadingBar.setX(loadingFrame.getX() + 15);
		loadingBar.setY(loadingFrame.getY() + 5);

		// Place the image that will hide the bar on top of the bar, adjusted a few px
		loadingBarHidden.setX(loadingBar.getX() + 35);
		loadingBarHidden.setY(loadingBar.getY() - 3);
		// The start position and how far to move the hidden loading bar
		startX = loadingBarHidden.getX();
		endX = 440;

		// The rest of the hidden bar
		loadingBg.setSize(450, 50);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setY(loadingBarHidden.getY() + 3);
	}*/

	@Override
	public void render(float delta) {
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(manager.update()) { // Load some, will return true if done loading
			setNextScreen("Splash");
			setDone(true);
		}

		// Interpolate the percentage to make it more smooth
		percent = Interpolation.linear.apply(percent, manager.getProgress(), 0.1f);

		// Update positions (and size) to match the percentage
		loadingBarHidden.setX(startX + endX * percent);
		loadingBg.setX(loadingBarHidden.getX() + 30);
		loadingBg.setWidth(450 - 450 * percent);
		loadingBg.invalidate();

		// Show the loading screen
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		// Dispose the loading assets as we no longer need them
		manager.unload("gfx/loading/loading.pack");
	}
}
