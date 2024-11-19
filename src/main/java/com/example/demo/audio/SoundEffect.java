package com.example.demo.audio;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.Boss;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundEffect {
    private static final String ENEMY_PROJECTILE_DESTROYED = "/com/example/demo/audio/enemyProjectileDestroyed.mp3";
    private static final String ACTIVATE_SHIELD = "/com/example/demo/audio/activateShield.mp3";
    private static final String ENEMY_HIT = "/com/example/demo/audio/enemyHit.mp3";
    private static final String USER_FIRE = "/com/example/demo/audio/fireProjectile.mp3";
    private static final String GAME_OVER = "/com/example/demo/audio/gameOver.mp3";
    private static final String NEXT_LEVEL = "/com/example/demo/audio/nextLevel.mp3";
    private static final String WIN = "/com/example/demo/audio/win.mp3";
    private static final String USER_HIT = "/com/example/demo/audio/userHit.mp3";
    private static final String SHIELD_HIT = "/com/example/demo/audio/shieldHit.mp3";
    private static final String SHIELD_DEACTIVATE = "/com/example/demo/audio/deactivateShield.mp3";
    private static final String WARNING = "/com/example/demo/audio/warning.mp3";

    private final Media enemyProjectileDestroyed;
    private final Media activateShield;
    private final Media enemyHit;
    private final Media userFire;
    private final Media gameOver;
    private final Media nextLevel;
    private final Media win;
    private final Media userHit;
    private final Media shieldHit;
    private final Media shieldDeactivate;
    private final Media warning;
    private final MediaPlayer warningPlayer;

    private boolean isMuted = false;

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

        warning = new Media(getClass().getResource(WARNING).toExternalForm());
        warningPlayer = new MediaPlayer(warning);
        warningPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void mute() {
        isMuted = true;
        warningPlayer.setVolume(0);
    }

    public void unmute() {
        isMuted = false;
        warningPlayer.setVolume(1);
    }

    public void playEnemyProjectileDestroyed() {
        MediaPlayer enemyProjectileDestroyedPlayer = new MediaPlayer(enemyProjectileDestroyed);
        if (isMuted) enemyProjectileDestroyedPlayer.setVolume(0);
        enemyProjectileDestroyedPlayer.play();
    }

    public void playActivateShield() {
        MediaPlayer activateShieldPlayer = new MediaPlayer(activateShield);
        activateShieldPlayer.setVolume(1.2);
        if (isMuted) activateShieldPlayer.setVolume(0);
        activateShieldPlayer.play();
    }

    public void playEnemyHit(ActiveActorDestructible enemy) {
        if (isMuted) return;
        MediaPlayer enemyHitPlayer = new MediaPlayer(enemyHit);
        MediaPlayer shieldHitPlayer = new MediaPlayer(shieldHit);

        if (enemy instanceof Boss && ((Boss) enemy).isShielded()) {
            shieldHitPlayer.play();
            return;
        }
        enemyHitPlayer.play();
    }

    public void playUserFire() {
        MediaPlayer userFirePlayer = new MediaPlayer(userFire);
        userFirePlayer.setVolume(0.5);
        if (isMuted) userFirePlayer.setVolume(0);
        userFirePlayer.play();
    }

    public void playGameOver() {
        MediaPlayer gameOverPlayer = new MediaPlayer(gameOver);
        if (isMuted) gameOverPlayer.setVolume(0);
        gameOverPlayer.play();
    }

    public void playNextLevel() {
        MediaPlayer nextLevelPlayer = new MediaPlayer(nextLevel);
        if (isMuted) nextLevelPlayer.setVolume(0);
        nextLevelPlayer.play();
    }

    public void playWin() {
        MediaPlayer winPlayer = new MediaPlayer(win);
        if (isMuted) winPlayer.setVolume(0);
        winPlayer.play();
    }

    public void playUserHit() {
        MediaPlayer userHitPlayer = new MediaPlayer(userHit);
        if (isMuted) userHitPlayer.setVolume(0);
        userHitPlayer.play();
    }

    public void playShieldDeactivate() {
        MediaPlayer shieldDeactivatePlayer = new MediaPlayer(shieldDeactivate);
        if (isMuted) shieldDeactivatePlayer.setVolume(0);
        shieldDeactivatePlayer.setVolume(1.2);
        shieldDeactivatePlayer.play();
    }

    public void playWarning() {
        warningPlayer.play();
    }

    public void pauseWarning() {
        warningPlayer.pause();
    }
}
