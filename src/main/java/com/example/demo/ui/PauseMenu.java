package com.example.demo.ui;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PauseMenu {
    private static final double WIDTH = 300;
    private static final double HEIGHT = 300;
    private final VBox layout;
    private static final String PAUSE_MENU_CSS = "/com/example/demo/styles/pauseMenu.css";
    private final Music music;
    private final SoundEffect soundEffect;

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
        layout.setVisible(true);

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

    public void hide() {
        layout.setVisible(false);
    }

    public VBox getLayout() {
        return layout;
    }

    private void toggleMuteSoundEffect(Button muteSoundEffectButton) {
        if (soundEffect.isMuted()) {
            soundEffect.unmute();
            muteSoundEffectButton.setText("Mute Sound Effect");
        } else {
            soundEffect.mute();
            muteSoundEffectButton.setText("Unmute Sound Effect");
        }
    }

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
