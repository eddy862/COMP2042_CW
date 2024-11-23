package com.example.demo.actor.projectile;

import com.example.demo.actor.ActiveActorDestructible;

/**
 * Abstract class representing a projectile in the game. A projectile can move across the screen and take damage then destroyed when it collides with another actor.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a Projectile with the specified image name, image height, initial x position, and initial y position.
	 *
	 * @param imageName the name of the image file of the projectile
	 * @param imageHeight the height of the image of the projectile
	 * @param initialXPos the initial x position of the projectile
	 * @param initialYPos the initial y position of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Abstract method to update the position of the projectile.
	 */
	@Override
	public abstract void updatePosition();

}
