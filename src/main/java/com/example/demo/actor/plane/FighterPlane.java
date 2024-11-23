package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActorDestructible;

/**
 * Abstract class representing a fighter plane in the game. A fighter plan can fire projectiles and take damage.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The health of the fighter plane.
	 */
	private int health;

	/**
	 * Constructs a FighterPlane with the specified image name, image height, initial x position, initial y position, and health.
	 *
	 * @param imageName the name of the image file of the fighter plane
	 * @param imageHeight the height of the image of the fighter plane
	 * @param initialXPos the initial x position of the fighter plane
	 * @param initialYPos the initial y position of the fighter plane
	 * @param health the health of the fighter plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Abstract method to fire a projectile from the fighter plane.
	 *
	 * @return the projectile fired from the fighter plane
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Decreases the health of the fighter plane by 1. If the health of the fighter plane reaches 0, the fighter plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Returns the x position of the projectile relative to the fighter plane's position.
	 *
	 * @param xPositionOffset the x position offset of the projectile
	 * @return the x position of the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Returns the y position of the projectile relative to the fighter plane's position.
	 *
	 * @param yPositionOffset the y position offset of the projectile
	 * @return the y position of the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Determine if the health of the fighter plane is zero.
	 *
	 * @return the health of the fighter plane
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Returns the health of the fighter plane.
	 *
	 * @return the health of the fighter plane
	 */
	public int getHealth() {
		return health;
	}
		
}
