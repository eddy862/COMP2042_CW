package com.example.demo.actor;

/**
 * Interface for actors that can take damage and be destroyed.
 */
public interface Destructible {

	/**
	 * Takes damage and updates the state of the actor.
	 */
	void takeDamage();

	/**
	 * Destroys the actor.
	 */
	void destroy();
}
