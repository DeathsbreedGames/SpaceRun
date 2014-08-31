package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This is the player class, where all the variables and
 * methods regarding the player specifically go.
 * 
 */
public class Player extends Entity {
	private final float MAX_VEL = 350f;
	
	public Player(TextureRegion img, float x, float y) {
		super(img, x, y);
	}
	
	public void render(SpriteBatch batch, float delta) {
		this.setVelocity(0f, 0f);
		
		if(this.getPosX() != Gdx.input.getX()) {
			if(this.getPosX() - Gdx.input.getX() > MAX_VEL * delta) this.setVelX(-MAX_VEL);
			else if(this.getPosX() - Gdx.input.getX() < -MAX_VEL * delta) this.setVelX(MAX_VEL);
			else this.setPosX(Gdx.input.getX());
		}
		super.render(batch, delta);
	}
}
