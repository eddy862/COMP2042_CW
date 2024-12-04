package com.example.demo.audio;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.Boss;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages sound effects for various game events.
 */
public class SoundEffect {
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String ENEMY_PROJECTILE_DESTROYED = "/com/example/demo/audio/enemyProjectileDestroyed.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String ACTIVATE_SHIELD = "/com/example/demo/audio/activateShield.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String ENEMY_HIT = "/com/example/demo/audio/enemyHit.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String USER_FIRE = "/com/example/demo/audio/fireProjectile.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String GAME_OVER = "/com/example/demo/audio/gameOver.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String NEXT_LEVEL = "/com/example/demo/audio/nextLevel.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String WIN = "/com/example/demo/audio/win.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String USER_HIT = "/com/example/demo/audio/userHit.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String SHIELD_HIT = "/com/example/demo/audio/shieldHit.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String SHIELD_DEACTIVATE = "/com/example/demo/audio/deactivateShield.mp3";
    /**
     * The location of the audio files for the sound effects.
     */
    private static final String WARNING = "/com/example/demo/audio/warning.mp3";

    /**
     * The media resources for the sound effects.
     */
    private final Media enemyProjectileDestroyed;
    /**
     * The media resources for the sound effects.
     */
    private final Media activateShield;
    /**
     * The media resources for the sound effects.
     */
    private final Media enemyHit;
    /**
     * The media resources for the sound effects.
     */
    private final Media userFire;
    /**
     * The media resources for the sound effects.
     */
    private final Media gameOver;
    /**
     * The media resources for the sound effects.
     */
    private final Media nextLevel;
    /**
     * The media resources for the sound effects.
     */
    private final Media win;
    /**
     * The media resources for the sound effects.
     */
    private final Media userHit;
    /**
     * The media resources for the sound effects.
     */
    private final Media shieldHit;
    /**
     * The media resources for the sound effects.
     */
    private final Media shieldDeactivate;
    /**
     * The media player for the warning sound effect.
     */
    private final MediaPlayer warningPlayer;
    /**
     * Check if the sound effects are muted.
     */
    private boolean isMuted = false;

    /**
     * Constructs a SoundEffect object and initializes the media players for various sound effects.
     */
    public SoundEffect() {
        enemyProjectileDestroyed = new Media(getClass().getResource(ENEMY_PROJECTILE_DESTROYED).toExternalForm());
        activateShield = new Media(getClass().getResource(ACTIVATE_SHIELD).toExternalForm());
        enemyHit = new Media(getClass().getResource(ENEMY_HIT).toExternalForm());
        userFire = new Media(getClass().getResource(USER_FIRE).toExternalForm());
        gameOver = new Media(getClass().getResource(GAME_OVER).toExternalForm());
        nextLevel = new Media(getClass().getResource(NEXT_LEVEL).toExternalForm());
        win = new Media(getClass().getResource(WIN).toExternalForm());
        userHit = new Media(getClass().getResource(USER_HIT).toExternalForm());
        shieldHit = new Media(getClass().getResource(SHIELD_HIT).toExternalForm());
        shieldDeactivate = new Media(getClass().getResource(SHIELD_DEACTIVATE).toExternalForm());

        Media warning = new Media(getClass().getResource(WARNING).toExternalForm());
        warningPlayer = new MediaPlayer(warning);
        warningPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        warningPlayer.setVolume(0.4);
    }

    /**
     * Checks if the sound effects are muted.
     *
     * @return true if the sound effects are muted, false otherwise
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Mutes all sound effects.
     */
    public void mute() {
        isMuted = true;
        warningPlayer.setVolume(0);
    }

    /**
     * Unmutes all sound effects.
     */
    public void unmute() {
        isMuted = false;
        warningPlayer.setVolume(0.4);
    }

    /**
     * Plays the sound effect for an enemy projectile being destroyed.
     */
    public void playEnemyProjectileDestroyed() {
        if (isMuted) return;
        MediaPlayer enemyProjectileDestroyedPlayer = new MediaPlayer(enemyProjectileDestroyed);
        enemyProjectileDestroyedPlayer.setVolume(0.4);
        enemyProjectileDestroyedPlayer.play();
    }

    /**
     * Plays the sound effect for activating a shield.
     */
    public void playActivateShield() {
        if (isMuted) return;
        MediaPlayer activateShieldPlayer = new MediaPlayer(activateShield);
        activateShieldPlayer.setVolume(0.6);
        activateShieldPlayer.play();
    }

    /**
     * Plays the sound effect for an enemy being hit.
     *
     * @param enemy the enemy that was hit
     */
    public void playEnemyHit(ActiveActorDestructible enemy) {
        if (isMuted) return;
        MediaPlayer enemyHitPlayer = new MediaPlayer(enemyHit);
        MediaPlayer shieldHitPlayer = new MediaPlayer(shieldHit);
        enemyHitPlayer.setVolume(0.4);
        shieldHitPlayer.setVolume(0.4);

        if (enemy instanceof Boss && ((Boss) enemy).isShielded()) {
            shieldHitPlayer.play();
            return;
        }
        enemyHitPlayer.play();
    }

    /**
     * Plays the sound effect for the user firing a projectile.
     */
    public void playUserFire() {
        if (isMuted) return;
        MediaPlayer userFirePlayer = new MediaPlayer(userFire);
        userFirePlayer.setVolume(0.1);
        userFirePlayer.play();
    }

    /**
     * Plays the sound effect for the game over event.
     */
    public void playGameOver() {
        if (isMuted) return;
        MediaPlayer gameOverPlayer = new MediaPlayer(gameOver);
        gameOverPlayer.setVolume(0.4);
        gameOverPlayer.play();
    }

    /**
     * Plays the sound effect for advancing to the next level.
     */
    public void playNextLevel() {
        if (isMuted) return;
        MediaPlayer nextLevelPlayer = new MediaPlayer(nextLevel);
        nextLevelPlayer.setVolume(0.4);
        nextLevelPlayer.play();
    }

    /**
     * Plays the sound effect for winning the game.
     */
    public void playWin() {
        if (isMuted) return;
        MediaPlayer winPlayer = new MediaPlayer(win);
        winPlayer.setVolume(0.4);
        winPlayer.play();
    }

    /**
     * Plays the sound effect for the user being hit.
     */
    public void playUserHit() {
        if (isMuted) return;
        MediaPlayer userHitPlayer = new MediaPlayer(userHit);
        userHitPlayer.setVolume(0.4);
        userHitPlayer.play();
    }

    /**
     * Plays the sound effect for deactivating a shield.
     */
    public void playShieldDeactivate() {
        if (isMuted) return;
        MediaPlayer shieldDeactivatePlayer = new MediaPlayer(shieldDeactivate);
        shieldDeactivatePlayer.setVolume(0.6);
        shieldDeactivatePlayer.play();
    }

    /**
     * Plays the warning sound effect.
     */
    public void playWarning() {
        if (isMuted) return;
        warningPlayer.play();
    }

    /**
     * Pauses the warning sound effect.
     */
    public void pauseWarning() {
        warningPlayer.pause();
    }

    /**
     * Stops the warning sound effect.
     */
    public void stopWarning() {
        warningPlayer.stop();
    }
}