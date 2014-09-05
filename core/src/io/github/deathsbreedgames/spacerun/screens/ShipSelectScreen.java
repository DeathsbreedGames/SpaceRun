package io.github.deathsbreedgames.spacerun.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	// Constructor:
	public ShipSelectScreen() {
		super("Game");
		
		camera = new OrthographicCamera(GlobalVars.width, GlobalVars.height);
		camera.position.set(GlobalVars.width / 2, GlobalVars.height / 2, 0f);
		camera.update();
		batch = new SpriteBatch();
	}
	
	// Update:
	@Override
	public void render(float delta) {
		super.render(delta);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Draw here
		batch.end();
	}
	
	// Dispose:
	@Override
	public void dispose() {
		batch.dispose();
	}
}
