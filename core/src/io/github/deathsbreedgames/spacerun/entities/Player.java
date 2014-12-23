/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * Object containing the variables and functions for the
 * player.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class Player extends Ship {
	private int score;
	
	// Pickup upgrades
	private boolean doubleShot;
	private boolean rapidFire;
	private boolean invincible;
	
	// Constructor:
	public Player(TextureRegion img, float x, float y, int weapon, int maxShield, float maxVel, float shootTime) {
		super(img, x, y, weapon, maxShield, maxVel, shootTime);
		this.score = 0;
		doubleShot = false;
		rapidFire = false;
		invincible = false;
	}
	
	// Update:
	@Override
	public void render(SpriteBatch batch, float delta) {
		this.setVelocity(0f, 0f);
		
		if(this.getPosX() != GlobalVars.getTouchX()) {
			if(this.getPosX() - GlobalVars.getTouchX() > this.getMaxVel() * delta) {
				this.setVelX(-this.getMaxVel());
			} else if(this.getPosX() - GlobalVars.getTouchX() < -this.getMaxVel() * delta) {
				this.setVelX(this.getMaxVel());
			} else {
				this.setPosX(GlobalVars.getTouchX());
			}
		}
		
		if(rapidFire) setShootTime(0.25f);
		else setShootTime(0.5f);
		
		super.render(batch, delta);
	}
	
	// Getter methods:
	public int getScore() { return score; }
	public boolean getDoubleShot() { return doubleShot; }
	public boolean getRapidFire() { return rapidFire; }
	public boolean isInvincible() { return invincible; }
	
	// Setter methods:
	public void setScore(int score) { this.score = score; }
	public void incScore(int iScore) { this.score += iScore; }
	public void setDoubleShot(boolean doubleShot) { this.doubleShot = doubleShot; }
	public void setRapidFire(boolean rapidFire) { this.rapidFire = rapidFire; }
	public void setInvincible(boolean invincible) { this.invincible = invincible; }
}
