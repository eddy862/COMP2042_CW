package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.projectile.EnemyProjectile;

/**
 * Represents an enemy plane in the game.
 * The enemy plane can move horizontally and fire projectiles.
 */
public class EnemyPlane extends FighterPlane {

    /**
     * The name of the image file of the enemy plane.
     */
    private static final String IMAGE_NAME = "enemyplane.png";
    /**
     * The height of the image of the enemy plane.
     */
    public static final int IMAGE_HEIGHT = 80;
    /**
     * The predefined horizontal velocity of the enemy plane.
     */
    private static final int HORIZONTAL_VELOCITY = -6;
    /**
     * The x position of the projectile relative to the enemy plane's position.
     */
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    /**
     * The y position of the projectile relative to the enemy plane's position.
     */
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    /**
     * The initial health of the enemy plane.
     */
    private static final int INITIAL_HEALTH = 1;
    /**
     * The probability of the enemy plane firing a projectile in each frame.
     */
    private static final double FIRE_RATE = .01;
    /**
     * The warning state of the enemy plane to indicate if it is in the warning area.
     */
    private boolean inWarningArea = false;
    /**
     * The x boundary of the warning area.
     */
    private static final double WARNING_AREA_X_BOUNDARY = 300;

    /**
     * Constructs an EnemyPlane with the specified initial position.
     *
     * @param initialXPos the initial x position of the enemy plane
     * @param initialYPos the initial y position of the enemy plane
     */
    public EnemyPlane(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    /**
     * Updates the position of the enemy plane by moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Fires a projectile based on predefined probability.
     * @return the projectile if it is fired, null otherwise
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Updates the state of the enemy plane, including its position and warning state.
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateWarningState();
    }

    /**
     * Updates the warning state of the enemy plane.
     * The plane is in the warning area if it crosses a certain x boundary.
     */
    public void updateWarningState() {
        inWarningArea = getLayoutX() + getTranslateX() < WARNING_AREA_X_BOUNDARY;
    }

    /**
     * Checks if the enemy plane is in the warning area.
     *
     * @return true if the plane is in the warning area, false otherwise
     */
    public boolean getInWarningArea() {
        return inWarningArea;
    }
}