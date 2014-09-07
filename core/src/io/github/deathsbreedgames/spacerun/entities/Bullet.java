package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author Nicolás A. Ortega
 * @copyright DeathsbreedGames
 * @license GNU Affero GPLv3
 * @year 2014
 * 
 * Description: This class contains the variables and methods used for
 * the Bullets in the game.
 * 
 */
public class Bullet extends Entity {
	private int atk;
	
	// Constructor:
	public Bullet(TextureRegion img, float x, float y, int atk) {
		super(img, x, y);
	}
	
	// Update:
	@Override
	public void render(SpriteBatch batch, float delta) {
		super.render(batch, delta);
	}
	
	// Getter methods:
	public int getAtk() { return atk; }
	
	// Setter methods:
	public void setAtk(int atk) { this.atk = atk; }
}
