package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class is used for the pickups in the game
 * 
 */
public class Pickup extends Entity {
	/*
	 * Types:
	 * 		- Weapon upgrade
	 * 		- Rapid Fire
	 * 		- Double shot
	 * 		- Repairs
	 * 		- Invincibility
	 * 		- Speed
	 */
	private String type;
	
	// Constructor:
	public Pickup(TextureRegion img, float x, float y, type) {
		super(img, x, y);
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
	public void setType(String type) { this.type = type; }
}
