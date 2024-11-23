package com.example.demo.actor;

/**
 * Abstract class representing an active actor that can be destroyed in the game.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * Determines if the actor is destroyed.
	 */
    private boolean isDestroyed;

    /**
     * Constructs an ActiveActorDestructible with the specified image, height, and initial position.
     *
     * @param imageName the name of the image file
     * @param imageHeight the height of the image
     * @param initialXPos the initial x position of the actor
     * @param initialYPos the initial y position of the actor
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        isDestroyed = false;
    }

    /**
     * Updates the position of the actor.
     * This method should be implemented by subclasses to define specific movement behavior.
     */
    @Override
    public abstract void updatePosition();

    /**
     * Updates the state of the actor.
     * This method should be implemented by subclasses to define specific update behavior.
     */
    public abstract void updateActor();

    /**
     * Takes damage and updates the state of the actor.
     * This method should be implemented by subclasses to define specific damage behavior.
     */
    @Override
    public abstract void takeDamage();

    /**
     * Destroys the actor by setting its destroyed state.
     */
    @Override
    public void destroy() {
        this.isDestroyed = true;
    }

    /**
     * Checks if the actor is destroyed.
     *
     * @return true if the actor is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }
}