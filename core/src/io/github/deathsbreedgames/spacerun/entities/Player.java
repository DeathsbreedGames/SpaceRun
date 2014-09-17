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
public class Player extends Ship {
	private int score;
	
	// Pickup upgrades
	private boolean doubleShot;
	
	// Constructor:
	public Player(TextureRegion img, float x, float y, String weapon, int maxShield, float maxVel, float shootTime) {
		super(img, x, y, weapon, maxShield, maxVel, shootTime);
		this.score = 0;
		doubleShot = false;
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
		super.render(batch, delta);
	}
	
	// Getter methods:
	public int getScore() { return score; }
	public boolean getDoubleShot() { return doubleShot; }
	
	// Setter methods:
	public void setScore(int score) { this.score = score; }
	public void incScore(int iScore) { this.score += iScore; }
	public void setDoubleShot(boolean doubleShot) { this.doubleShot = doubleShot; }
}
