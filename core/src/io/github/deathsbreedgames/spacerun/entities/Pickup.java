/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Object containing variables and functions for the pickups.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class Pickup extends Entity {
	/*
	 * Types:
	 * 		- upgrade			|	Upgrade to a better bullet
	 * 		- rapid				|	Fire bullets faster
	 * 		- double			|	Shoot two bullets instead of one
	 * 		- repair			|	Simple, it repairs the ship
	 * 		- invincibility			|	What it sounds like
	 * 		- speed				|	Change in position over time, not the drug :D
	 * 
	 */
	private String type;
	
	// Constructor:
	public Pickup(TextureRegion img, float x, float y, String type) {
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
