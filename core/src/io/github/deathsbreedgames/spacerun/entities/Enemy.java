package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class contains methods and variables used for the
 * enemies in the game.
 * 
 */
public class Enemy extends Ship {
	private String type;
	
	// Constructor:
	public Enemy(TextureRegion img, float x, float y, String weapon, int maxShield, float maxVel, float shootTime, String type) {
		super(img, x, y, weapon, maxShield, maxVel, shootTime);
		this.type = type;
	}
	
	// Update:
	@Override
	public void render(SpriteBatch batch, float delta) {
		super.render(batch, delta);
	}
	
	// Getter methods:
	public String getType() { return type; }
	
	// Setter methods:
	public void setType(String type) { this.type = type }
}
