/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The bullet object in the game (for both sides).
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class Bullet extends Entity {
	private int dmg;
	
	// Constructor:
	public Bullet(TextureRegion img, float x, float y, int dmg) {
		super(img, x, y);
		this.dmg = dmg;
	}
	
	// Update:
	@Override
	public void render(SpriteBatch batch, float delta) {
		super.render(batch, delta);
	}
	
	// Getter methods:
	public int getDmg() { return dmg; }
	
	// Setter methods:
	public void setDmg(int atk) { this.dmg = dmg; }
}
