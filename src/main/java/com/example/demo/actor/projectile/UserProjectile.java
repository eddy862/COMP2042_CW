package com.example.demo.actor.projectile;

/**
 * Represents a projectile fired by the user.
 * The projectile moves horizontally across the screen.
 */
public class UserProjectile extends Projectile {

	/**
	 * The name of the image file of the user projectile.
	 */
	private static final String IMAGE_NAME = "userfire.png";
	/**
	 * The height of the image of the user projectile.
	 */
	private static final int IMAGE_HEIGHT = 125;
	/**
	 * The predefined horizontal velocity of the user projectile.
	 */
	private static final int HORIZONTAL_VELOCITY = 15;

	/**
	 * Constructs a UserProjectile with the specified initial position.
	 *
	 * @param initialXPos the initial x position of the projectile
	 * @param initialYPos the initial y position of the projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor by updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
