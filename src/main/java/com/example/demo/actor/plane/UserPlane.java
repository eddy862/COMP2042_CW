package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.projectile.UserProjectile;

/**
 * Represents the user plane in the game.
 * The user plane can move vertically and horizontally, fire projectiles, and keep track of the number of kills.
 */
public class UserPlane extends FighterPlane {

	/**
	 * The name of the image file of the user plane.
	 */
	private static final String IMAGE_NAME = "userplane.png";
	/**
	 * The y position upper bound which the user plane cannot move beyond.
	 */
	private static final double Y_UPPER_BOUND = 20;
	/**
	 * The y position lower bound which the user plane cannot move beyond.
	 */
	private static final double Y_LOWER_BOUND = 650.0;
	/**
	 * The x position upper bound which the user plane cannot move beyond.
	 */
	private static final double X_UPPER_BOUND = 0;
	/**
	 * The x position lower bound which the user plane cannot move beyond.
	 */
	private static final double X_LOWER_BOUND = 250;
	/**
	 * The initial x position of the user plane.
	 */
	private static final double INITIAL_X_POSITION = 5.0;
	/**
	 * The initial y position of the user plane.
	 */
	private static final double INITIAL_Y_POSITION = 300.0;
	/**
	 * The height of the image of the user plane.
	 */
	private static final int IMAGE_HEIGHT = 100;
	/**
	 * The predefined velocity of the user plane.
	 */
	private static final int VELOCITY = 8;
	/**
	 * The x position of the projectile relative to the user plane's position.
	 */
	private static final int PROJECTILE_X_POSITION_OFFSET = 100;
	/**
	 * The y position of the projectile relative to the user plane's position.
	 */
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	/**
	 * The vertical velocity multiplier of the user plane. positive for down, negative for up, 0 for stationary.
	 */
	private int verticalVelocityMultiplier;
	/**
	 * The horizontal velocity multiplier of the user plane. positive for right, negative for left, 0 for stationary.
	 */
	private int horizontalVelocityMultiplier;
	/**
	 * The number of kills the user plane has.
	 */
	private int numberOfKills;

	/**
	 * Constructs a UserPlane with the specified initial health.
	 *
	 * @param initialHealth the initial health of the user plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalVelocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Updates the position of the user plane by moving it vertically and horizontally.
	 */
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VELOCITY * verticalVelocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(VELOCITY * horizontalVelocityMultiplier);
			double newPosition = getLayoutX() + getTranslateX();
			if (newPosition < X_UPPER_BOUND || newPosition > X_LOWER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Updates the user plane by updating its position in each frame.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 *
	 * @return the projectile fired from the user plane
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Returns true if the user plane is moving vertically, false otherwise.
	 *
	 * @return true if the user plane is moving vertically, false otherwise
	 */
	private boolean isMovingVertically() {
		return verticalVelocityMultiplier != 0;
	}

	/**
	 * Returns true if the user plane is moving horizontally, false otherwise.
	 *
	 * @return true if the user plane is moving horizontally, false otherwise
	 */
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	/**
	 * Moves the user plane up.
	 */
	public void moveUp() {
		verticalVelocityMultiplier = -1;
	}

	/**
	 * Moves the user plane down.
	 */
	public void moveDown() {
		verticalVelocityMultiplier = 1;
	}

	/**
	 * Moves the user plane left.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the user plane right.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Stops the user plane from moving vertically.
	 */
	public void stopVertically() {
		verticalVelocityMultiplier = 0;
	}

	/**
	 * Stops the user plane from moving horizontally.
	 */
	public void stopHorizontally() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Returns the number of kills the user plane has.
	 *
	 * @return the number of kills the user plane has
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the number of kills the user plane has by 1.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

}
