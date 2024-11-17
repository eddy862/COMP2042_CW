package com.example.demo.audio;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class Music {
    private static final String GAME_BACKGROUND_MUSIC = "/com/example/demo/audio/gameBackgroundMusic.mp3";
    private static final String MAIN_MENU_BACKGROUND_MUSIC = "/com/example/demo/audio/mainMenuBackgroundMusic.mp3";
    private MediaPlayer game_backgroundMusicPlayer;
    private MediaPlayer mainMenuBackgroundMusicPlayer;
    private boolean isMuted = false;

    public Music() {
        game_backgroundMusicPlayer = new MediaPlayer(new Media(getClass().getResource(GAME_BACKGROUND_MUSIC).toExternalForm()));
        game_backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        game_backgroundMusicPlayer.setVolume(0.6);

        mainMenuBackgroundMusicPlayer = new MediaPlayer(new Media(getClass().getResource(MAIN_MENU_BACKGROUND_MUSIC).toExternalForm()));
        mainMenuBackgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void mute() {
        game_backgroundMusicPlayer.setVolume(0);
        mainMenuBackgroundMusicPlayer.setVolume(0);
        isMuted = true;
    }

    public void unmute() {
        game_backgroundMusicPlayer.setVolume(0.6);
        mainMenuBackgroundMusicPlayer.setVolume(1);
        isMuted = false;
    }

    public void playGameBackgroundMusic() {
        game_backgroundMusicPlayer.play();
    }

    public void pauseGameBackgroundMusic() {
        game_backgroundMusicPlayer.pause();
    }

    public void stopGameBackgroundMusic() {
        game_backgroundMusicPlayer.stop();
    }

    public void playMainMenuBackgroundMusic() {
        mainMenuBackgroundMusicPlayer.play();
    }

    public void stopMainMenuBackgroundMusic() {
        mainMenuBackgroundMusicPlayer.stop();
    }
}
