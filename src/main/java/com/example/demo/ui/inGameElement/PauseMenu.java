package com.example.demo.ui.inGameElement;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Represents the pause menu in the game.
 * This class creates a layout with options to resume the game, mute/unmute music and sound effects, or return to the main menu.
 */
public class PauseMenu {
    /**
     * The width of the pause menu.
     */
    private static final double WIDTH = 300;
    /**
     * The height of the pause menu.
     */
    private static final double HEIGHT = 300;
    /**
     * The layout of the pause menu.
     */
    private final VBox layout;
    /**
     * The path to the CSS file for the pause menu.
     */
    private static final String PAUSE_MENU_CSS = "/com/example/demo/styles/pauseMenu.css";
    /**
     * The music manager to control the game's music.
     */
    private final Music music;
    /**
     * The sound effect manager to control the game's sound effects.
     */
    private final SoundEffect soundEffect;

    /**
     * Constructs a PauseMenu object with the specified parameters.
     *
     * @param onResume the action to perform when resuming the game
     * @param onReturnToMainMenu the action to perform when returning to the main menu
     * @param music the music object to control the game's music
     * @param soundEffect the sound effect object to control the game's sound effects
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public PauseMenu(Runnable onResume, Runnable onReturnToMainMenu, Music music, SoundEffect soundEffect, double screenWidth, double screenHeight) {
        this.music = music;
        this.soundEffect = soundEffect;

        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("pause-menu");
        layout.setPrefWidth(WIDTH);
        layout.setPrefHeight(HEIGHT);
        layout.setLayoutX(screenWidth / 2 - WIDTH / 2);
        layout.setLayoutY(screenHeight / 2 - HEIGHT / 2);

        Label pauseLabel = new Label("Game Paused");
        pauseLabel.getStyleClass().add("label");
        pauseLabel.getStyleClass().add("title");

        Button muteMusicButton = new Button(music.isMuted() ? "Unmute Music" : "Mute Music");
        muteMusicButton.setOnAction(e -> toggleMuteMusic(muteMusicButton));
        muteMusicButton.getStyleClass().add("button");

        Button muteSoundEffectButton = new Button(soundEffect.isMuted() ? "Unmute Sound Effect" : "Mute Sound Effect");
        muteSoundEffectButton.setOnAction(e -> toggleMuteSoundEffect(muteSoundEffectButton));
        muteSoundEffectButton.getStyleClass().add("button");

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> onResume.run());
        resumeButton.getStyleClass().add("button");

        Button returnToMainMenuButton = new Button("Main Menu");
        returnToMainMenuButton.setOnAction(e -> onReturnToMainMenu.run());
        returnToMainMenuButton.getStyleClass().add("button");

        layout.getChildren().addAll(pauseLabel, muteMusicButton, muteSoundEffectButton, resumeButton, returnToMainMenuButton);
        layout.getStylesheets().add(getClass().getResource(PAUSE_MENU_CSS).toExternalForm());
    }

    /**
     * Hides the pause menu.
     */
    public void hide() {
        layout.setVisible(false);
    }

    /**
     * Retrieves the layout of the pause menu.
     *
     * @return the layout of the pause menu
     */
    public VBox getLayout() {
        return layout;
    }

    /**
     * Toggles the mute state of the sound effects and updates the button text.
     *
     * @param muteSoundEffectButton the button to update the text for
     */
    private void toggleMuteSoundEffect(Button muteSoundEffectButton) {
        if (soundEffect.isMuted()) {
            soundEffect.unmute();
            muteSoundEffectButton.setText("Mute Sound Effect");
        } else {
            soundEffect.mute();
            muteSoundEffectButton.setText("Unmute Sound Effect");
        }
    }

    /**
     * Toggles the mute state of the music and updates the button text.
     *
     * @param muteMusicButton the button to update the text for
     */
    private void toggleMuteMusic(Button muteMusicButton) {
        if (music.isMuted()) {
            music.unmute();
            muteMusicButton.setText("Mute Music");
        } else {
            music.mute();
            muteMusicButton.setText("Unmute Music");
        }
    }
}