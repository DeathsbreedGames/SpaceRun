/**
 * Copyright (C) 2015 DeathsbreedGames
 * License: GNU Affero GPLv3
 */
package io.github.deathsbreedgames.spacerun.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * A template for all other classes in this package.
 * 
 * @author Nicolás A. Ortega
 * @version 14.12.22
 */
public class Entity extends Sprite {
	private Vector2 position;
	private Vector2 velocity;
	
	// Constructor:
	public Entity(TextureRegion img, float x, float y) {
		super(img);
		position = new Vector2(x, y);
		velocity = new Vector2(0f, 0f);
		setCenter(position.x, position.y);
		setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	public Entity(Texture img, float x, float y) {
		super(img);
		position = new Vector2(x, y);
		velocity = new Vector2(0f, 0f);
		setCenter(position.x, position.y);
		setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	// Update:
	public void render(SpriteBatch batch, float delta) {
		position.add(velocity.x * delta, velocity.y * delta);
		setCenter(position.x, position.y);
		if(getPosX() < 0f) setPosX(0f);
		if(getPosX() > Gdx.graphics.getWidth()) setPosX(Gdx.graphics.getWidth());
		if(getPosY() < 0f) setPosY(0f);
		if(getPosY() > Gdx.graphics.getHeight()) setPosY(Gdx.graphics.getHeight());
		
		setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		super.draw(batch);
	}
	
	// Getter methods:
	public Vector2 getPosition() { return position; }
	public float getPosX() { return position.x; }
	public float getPosY() { return position.y; }
	public Vector2 getVelocity() { return velocity; }
	public float getVelX() { return velocity.x; }
	public float getVelY() { return velocity.y; }
	
	// Setter methods:
	public void setPosition(Vector2 position) { this.position = position; }
	public void setPosition(float x, float y) { this.position = new Vector2(x, y); }
	public void setPosX(float x) { this.position.x = x; }
	public void setPosY(float y) { this.position.y = y; }
	public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
	public void setVelocity(float x, float y) { this.velocity = new Vector2(x, y); }
	public void setVelX(float x) { this.velocity.x = x; }
	public void setVelY(float y) { this.velocity.y = y; }
}
