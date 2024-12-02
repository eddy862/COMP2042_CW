package com.example.demo.actor.plane;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.projectile.BossProjectile;
import com.example.demo.audio.SoundEffect;

import java.util.*;


/**
 * Represents the boss. The boss moves vertically in unique move pattern and can randomly activate a shield
 * that makes it invulnerable to damage within a certain time frame.
 */
public class Boss extends FighterPlane {
	/**
	 * The name of the image file of the boss.
	 */
	private static final String IMAGE_NAME = "bossplane.png";
	/**
	 * The initial x position of the boss.
	 */
	private static final double INITIAL_X_POSITION = 980.0;
	/**
	 * The initial y position of the boss.
	 */
	private static final double INITIAL_Y_POSITION = 400;
	/**
	 * The y position of the projectile relative to the boss's position.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	/**
	 * The probability of the boss firing a projectile in each frame.
	 */
	private static final double BOSS_FIRE_RATE = .04;
	/**
	 * The probability of the boss activating the shield in each frame.
	 */
	private static final double BOSS_SHIELD_PROBABILITY = .003;
	/**
	 * The height of the image of the boss.
	 */
	private static final int IMAGE_HEIGHT = 200;
	/**
	 * The predefined vertical velocity of the boss.
	 */
	private static final int VERTICAL_VELOCITY = 8;
	/**
	 * The number of moves per cycle. The boss moves up, down, and stays in place for a certain number of times.
	 */
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5; // Number of moves per cycle
	/**
	 * The maximum number of frames the boss can move in the same direction before changing direction.
	 */
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	/**
	 * The y position upper bound which the boss cannot move beyond.
	 */
	private static final int Y_POSITION_UPPER_BOUND = 0;
	/**
	 * The y position lower bound which the boss cannot move beyond.
	 */
	private static final int Y_POSITION_LOWER_BOUND = 550;
	/**
	 * The maximum number of frames the boss can have the shield activated.
	 */
	private static final int MAX_FRAMES_WITH_SHIELD = 200;
	/**
	 * The move pattern of the boss. It consists of a list of moves that the boss can make in a cycle including moving up, down, and staying in place.
	 */
	private final List<Integer> movePattern; // List of moves in the move pattern
	/**
	 * The shield status of the boss.
	 */
	private boolean isShielded;
	/**
	 * The number of frames with the same move. The boss should change direction after a certain number of frames.
	 */
	private int consecutiveMovesInSameDirection; // Number of frames with the same move
	/**
	 * The index of the current move in the move pattern list that determines the boss's next movement.
	 */
	private int indexOfCurrentMove; // Index of the current move in the move pattern
	/**
	 * The number of frames of boss with the shield activated. The shield is deactivated after a certain number of frames.
	 */
	private int framesWithShieldActivated; // Number of frames with the shield activated

	/**
	 * Constructor to create a new Boss instance with the customised health.
	 * @param health the health of the boss
	 */
	public Boss(int health) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, health);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Updates the position of the boss by moving it vertically according to the move pattern.
	 * If the boss reaches the upper or lower bounds, it will not move further.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());

		// note that coordinates start from the top left corner, x increases to the right, y increases to the bottom
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY); // move back to the previous position
		}
	}

	/**
	 * Updates the boss by updating its position and shield status in each frame.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the boss should fire in the current frame.
	 * @return the projectile fired by the boss, or null if the boss does not fire in the current frame
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * If the boss is not shielded, it takes damage. Otherwise, the boss is invulnerable to damage.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the move pattern of the boss. For each cycle, the boss  moves up, down, and stays
	 * in place a certain number of times. Then the moving directions are shuffled to make the boss's movement
	 * more unpredictable.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(0);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * If the boss is shielded, the number of frames with the shield activated is incremented.
	 * Otherwise, the shield is activated if the boss should activate the shield based on a certain probability.
	 * The shield is deactivated after a certain number of frames.
	 */
	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();	
		if (shieldExhausted()) deactivateShield();
	}

	/**
	 * Returns the next move in the move pattern. If the boss has moved in the same direction for a
	 * certain number of frames, the move pattern is shuffled and the index of the current move is
	 * incremented. Resets the index if it reaches the end of the move pattern.
	 * @return the next move in the move pattern
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++; // track the number of frames with the same move
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		// Reset the index if it reaches the end of the move pattern
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if the boss should fire a projectile in the current frame.
	 * @return true if the boss should fire a projectile, false otherwise
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Returns the initial y position of the projectile fired by the boss.
	 * @return the initial y position of the projectile
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the boss should activate the shield based on a predefined probability.
	 * @return true if the boss should activate the shield, false otherwise
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the shield is exhausted based on the number of frames with the shield activated.
	 * @return true if the shield is exhausted, false otherwise
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield of the boss.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the shield of the boss.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Returns the shield status of the boss.
	 * @return true if the boss is shielded, false otherwise
	 */
	public boolean isShielded() {
		return isShielded;
	}
}
