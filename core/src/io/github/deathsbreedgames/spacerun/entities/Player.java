package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.deathsbreedgames.spacerun.GlobalVars;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This is the player class where all the variables and
 * methods regarding the player specifically go.
 * 
 */
public class Player extends Entity {
	private float maxAtk;
	private float maxDef;
	private float maxVel;
	
	// Shooting variables
	private float shootTime = 0.5f;
	private float shootTimer = 0f;
	public boolean shoot = false;
	
	// Constructor:
	public Player(TextureRegion img, float x, float y, float maxAtk, float maxDef, float maxVel) {
		super(img, x, y);
		this.maxAtk = maxAtk;
		this.maxDef = maxDef;
		this.maxVel = maxVel;
	}
	
	// Update:
	public void render(SpriteBatch batch, float delta) {
		this.setVelocity(0f, 0f);
		
		if(this.getPosX() != GlobalVars.getTouchX()) {
			if(this.getPosX() - GlobalVars.getTouchX() > maxVel * delta) this.setVelX(-maxVel);
			else if(this.getPosX() - GlobalVars.getTouchX() < -maxVel * delta) this.setVelX(maxVel);
			else this.setPosX(GlobalVars.getTouchX());
		}
		super.render(batch, delta);
		
		shootTimer += delta;
		if(!shoot && shootTimer >= shootTime) {
			shoot = true;
			shootTimer = 0f;
		} else if(shoot) {
			shoot = false;
		}
	}
	
	public float getMaxAtk() { return maxAtk; }
	public float getMaxDef() { return maxDef; }
	public float getMaxVel() { return maxVel; }
	public float getShootTime() { return shootTime; }
	
	public void setMaxAtk(float maxAtk) { this.maxAtk = maxAtk; }
	public void setMaxDef(float maxDef) { this.maxDef = maxDef; }
	public void setMaxVel(float maxVel) { this.maxVel = maxVel; }
	public void setShootTime(float shootTime) { this.shootTime = shootTime; }
}
