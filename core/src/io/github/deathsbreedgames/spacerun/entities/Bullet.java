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
