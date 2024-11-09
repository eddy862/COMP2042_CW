package com.example.demo.level;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelAudio {
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
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/backgroundMusic.mp3";

    private Media enemyProjectileDestroyed;
    private Media activateShield;
    private Media enemyHit;
    private Media userFire;
    private Media gameOver;
    private Media nextLevel;
    private Media win;
    private Media userHit;
    private Media shieldHit;
    private Media shieldDeactivate;
    private Media backgroundMusic;
    private MediaPlayer backgroundMusicPlayer;

    public LevelAudio() {
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

        backgroundMusic = new Media(getClass().getResource(BACKGROUND_MUSIC).toExternalForm());
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMusicPlayer.setVolume(0.6);
    }

    public void playEnemyProjectileDestroyed() {
        MediaPlayer enemyProjectileDestroyedPlayer = new MediaPlayer(enemyProjectileDestroyed);
        enemyProjectileDestroyedPlayer.play();
    }

    public void playActivateShield() {
        MediaPlayer activateShieldPlayer = new MediaPlayer(activateShield);
        activateShieldPlayer.setVolume(1.2);
        activateShieldPlayer.play();
    }

    public void playEnemyHit() {
        MediaPlayer enemyHitPlayer = new MediaPlayer(enemyHit);
        enemyHitPlayer.play();
    }

    public void playUserFire() {
        MediaPlayer userFirePlayer = new MediaPlayer(userFire);
        userFirePlayer.setVolume(0.5);
        userFirePlayer.play();
    }

    public void playGameOver() {
        MediaPlayer gameOverPlayer = new MediaPlayer(gameOver);
        gameOverPlayer.play();
    }

    public void playNextLevel() {
        MediaPlayer nextLevelPlayer = new MediaPlayer(nextLevel);
        nextLevelPlayer.play();
    }

    public void playWin() {
        MediaPlayer winPlayer = new MediaPlayer(win);
        winPlayer.play();
    }

    public void playUserHit() {
        MediaPlayer userHitPlayer = new MediaPlayer(userHit);
        userHitPlayer.play();
    }

    public void playShieldHit() {
        MediaPlayer shieldHitPlayer = new MediaPlayer(shieldHit);
        shieldHitPlayer.play();
    }

    public void playShieldDeactivate() {
        MediaPlayer shieldDeactivatePlayer = new MediaPlayer(shieldDeactivate);
        shieldDeactivatePlayer.setVolume(1.2);
        shieldDeactivatePlayer.play();
    }

    public void playBackgroundMusic() {
        backgroundMusicPlayer.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusicPlayer.stop();
    }
}
