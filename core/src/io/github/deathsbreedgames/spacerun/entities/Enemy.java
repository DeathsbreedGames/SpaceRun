/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Object which contains variables and methods for enemy ships.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class Enemy extends Ship {
	private String type;
	
	// Constructor:
	public Enemy(TextureRegion img, float x, float y, int weapon, int maxShield, float maxVel, float shootTime, String type) {
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
	public void setType(String type) { this.type = type; }
}
