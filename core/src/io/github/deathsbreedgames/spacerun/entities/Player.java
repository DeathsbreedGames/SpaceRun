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
	private int atk;
	private int maxDef;
	private float maxVel;
	
	private int shields;
	
	// Shooting variables
	private float shootTime;
	private float shootTimer;
	public boolean shoot;
	
	// Constructor:
	public Player(TextureRegion img, float x, float y, int atk, int maxDef, float maxVel) {
		super(img, x, y);
		this.atk = atk;
		this.maxDef = maxDef;
		this.maxVel = maxVel;
		this.shields = maxDef;
		
		shootTime = 0.5f;
		shootTimer = 0.0f;
		shoot = false;
	}
	
	// Update:
	@Override
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
	
	// Getter methods:
	public int getAtk() { return atk; }
	public int getMaxDef() { return maxDef; }
	public float getMaxVel() { return maxVel; }
	public int getShields() { return shields; }
	public float getShootTime() { return shootTime; }
	
	// Setter methods:
	public void setAtk(int atk) { this.atk = atk; }
	public void setMaxDef(int maxDef) { this.maxDef = maxDef; }
	public void setMaxVel(float maxVel) { this.maxVel = maxVel; }
	public void setShields(int shields) { this.shields = shields; }
	public void incShields(int iShields) { this.shields += iShields; }
	public void setShootTime(float shootTime) { this.shootTime = shootTime; }
}
