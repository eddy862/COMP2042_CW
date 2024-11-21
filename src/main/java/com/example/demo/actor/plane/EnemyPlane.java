package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.projectile.EnemyProjectile;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	public static final int IMAGE_HEIGHT = 120;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;
    private boolean inWarningArea = false;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile with a certain probability
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

	@Override
	public void updateActor() {
		updatePosition();
		updateWarningState();
	}

	public void updateWarningState() {
        double warningAreaXBoundary = 300;
        inWarningArea = getLayoutX() + getTranslateX() < warningAreaXBoundary;
	}

	public boolean getInWarningArea() {
		return inWarningArea;
	}
}
