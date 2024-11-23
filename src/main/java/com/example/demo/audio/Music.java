package com.example.demo.audio;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

/**
 * Manages the background music for the game, main menu and related pages.
 */
public class Music {
    /**
     * The location of the audio files for the background music.
     */
    private static final String GAME_BACKGROUND_MUSIC = "/com/example/demo/audio/gameBackgroundMusic.mp3";
    /**
     * The location of the audio files for the main menu background music.
     */
    private static final String MAIN_MENU_BACKGROUND_MUSIC = "/com/example/demo/audio/mainMenuBackgroundMusic.mp3";
    /**
     * The media player for the game background music.
     */
    private final MediaPlayer game_backgroundMusicPlayer;
    /**
     * The media player for the main menu background music.
     */
    private final MediaPlayer mainMenuBackgroundMusicPlayer;
    /**
     * Check if the music is muted.
     */
    private boolean isMuted = false;

    /**
     * Constructs a Music object and initializes the media players for game and main menu background music.
     */
    public Music() {
        game_backgroundMusicPlayer = new MediaPlayer(new Media(getClass().getResource(GAME_BACKGROUND_MUSIC).toExternalForm()));
        game_backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        game_backgroundMusicPlayer.setVolume(0.6);

        mainMenuBackgroundMusicPlayer = new MediaPlayer(new Media(getClass().getResource(MAIN_MENU_BACKGROUND_MUSIC).toExternalForm()));
        mainMenuBackgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    /**
     * Checks if the music is muted.
     *
     * @return true if the music is muted, false otherwise
     */
    public boolean isMuted() {
        return isMuted;
    }

    /**
     * Mutes the background music.
     */
    public void mute() {
        game_backgroundMusicPlayer.setVolume(0);
        mainMenuBackgroundMusicPlayer.setVolume(0);
        isMuted = true;
    }

    /**
     * Unmutes the background music.
     */
    public void unmute() {
        game_backgroundMusicPlayer.setVolume(0.6);
        mainMenuBackgroundMusicPlayer.setVolume(1);
        isMuted = false;
    }

    /**
     * Plays the game background music.
     */
    public void playGameBackgroundMusic() {
        game_backgroundMusicPlayer.play();
    }

    /**
     * Pauses the game background music.
     */
    public void pauseGameBackgroundMusic() {
        game_backgroundMusicPlayer.pause();
    }

    /**
     * Stops the game background music.
     */
    public void stopGameBackgroundMusic() {
        game_backgroundMusicPlayer.stop();
    }

    /**
     * Plays the main menu background music.
     */
    public void playMainMenuBackgroundMusic() {
        mainMenuBackgroundMusicPlayer.play();
    }

    /**
     * Stops the main menu background music.
     */
    public void stopMainMenuBackgroundMusic() {
        mainMenuBackgroundMusicPlayer.stop();
    }
}