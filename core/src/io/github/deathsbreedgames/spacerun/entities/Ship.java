/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Template for all ships.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class Ship extends Entity {
	private int weapon;
	private int maxShield;
	private int shields;
	private float maxVel;
	
	private float shootTime;
	private float shootTimer;
	public boolean shoot;
	
	// Constructor:
	public Ship(TextureRegion img, float x, float y, int weapon, int maxShield, float maxVel, float shootTime) {
		super(img, x, y);
		this.weapon = weapon;
		this.maxShield = maxShield;
		this.shields = maxShield;
		this.maxVel = maxVel;
		
		this.shootTime = shootTime;
		shootTimer = 0f;
		shoot = false;
	}
	
	// Update:
	@Override
	public void render(SpriteBatch batch, float delta) {
		super.render(batch, delta);
		
		shootTimer += delta;
		if(!shoot && shootTimer >= shootTime) {
			shoot = true;
			shootTimer = 0f;
		} else if(shoot) {
			shoot = false;
		}
		
		if(shields > maxShield) shields = maxShield;
	}
	
	// Getter methods:
	public int getWeapon() { return weapon; }
	public int getMaxShield() { return maxShield; }
	public int getShields() { return shields; }
	public float getMaxVel() { return maxVel; }
	public float getShootTime() { return shootTime; }
	public float getShootTimer() { return shootTimer; }
	
	// Setter methods:
	public void setWeapon(int weapon) { this.weapon = weapon; }
	public void upgradeWeapon() { this.weapon++; }
	public void degradeWeapon() { this.weapon--; }
	public void setMaxShield(int maxShield) { this.maxShield = maxShield; }
	public void setShields(int shields) { this.shields = shields; }
	public void incShields(int iShields) { this.shields += iShields; }
	public void setMaxVel(float maxVel) { this.maxVel = maxVel; }
	public void incMaxVel(float iMaxVel) { this.maxVel += iMaxVel; }
	public void setShootTime(float shootTime) { this.shootTime = shootTime; }
	public void setShootTimer(float shootTimer) { this.shootTimer = shootTimer; }
	public void setShoot(boolean shoot) { this.shoot = shoot; }
}
