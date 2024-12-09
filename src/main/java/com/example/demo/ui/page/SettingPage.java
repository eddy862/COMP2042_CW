package com.example.demo.ui.page;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the settings page in the application.
 * This class provides methods to initialize and display the settings page,
 * allowing users to mute or unmute music and sound effects.
 */
public class SettingPage {
    /**
     * The main menu scene to return to.
     */
    private final Scene mainMenuScene;
    /**
     * The music object to control.
     */
    private final Music music;
    /**
     * The sound effect object to control.
     */
    private final SoundEffect soundEffect;
    /**
     * The path to the CSS file for the settings page.
     */
    private static final String SETTING_PAGE_CSS = "/com/example/demo/styles/setting.css";
    /**
     * The stage to display the settings page on.
     */
    private final Stage stage;

    /**
     * Constructs a SettingPage object with the specified main menu scene, music, and sound effect.
     *
     * @param mainMenuScene the main menu scene to return to
     * @param music the music object to control
     * @param soundEffect the sound effect object to control
     */
    public SettingPage(Scene mainMenuScene, Music music, SoundEffect soundEffect) {
        this.mainMenuScene = mainMenuScene;
        this.music = music;
        this.soundEffect = soundEffect;
        this.stage = (Stage) mainMenuScene.getWindow();
    }

    /**
     * Initializes and returns the settings scene.
     * Sets up the layout, buttons, and styles for the settings page.
     *
     * @return the initialized settings scene
     */
    public Scene initializeScene() {
        VBox vBox1 = new VBox(50);
        VBox vBox2 = new VBox(20);

        Label settingLabel = new Label("Audio Settings");
        settingLabel.getStyleClass().add("label");

        Button muteMusicButton = new Button(music.isMuted() ? "Unmute Music" : "Mute Music");
        muteMusicButton.setOnAction(e -> toggleMuteMusic(muteMusicButton));
        muteMusicButton.getStyleClass().add("button");

        Button muteSoundEffectButton = new Button(soundEffect.isMuted() ? "Unmute Sound Effect" : "Mute Sound Effect");
        muteSoundEffectButton.setOnAction(e -> toggleMuteSoundEffect(muteSoundEffectButton));
        muteSoundEffectButton.getStyleClass().add("button");

        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(muteMusicButton, muteSoundEffectButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainMenu());
        backButton.getStyleClass().add("button");

        vBox2.getChildren().addAll(hBox, backButton);
        vBox2.setAlignment(Pos.CENTER);
        vBox1.getChildren().addAll(settingLabel, vBox2);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setStyle("-fx-background-color: #2c3e50;");

        Scene scene = new Scene(vBox1, stage.getWidth(), stage.getHeight());
        scene.getStylesheets().add(getClass().getResource(SETTING_PAGE_CSS).toExternalForm());

        return scene;
    }

    /**
     * Shows the main menu scene.
     */
    private void showMainMenu() {
        stage.setScene(mainMenuScene);
    }

    /**
     * Toggles the mute state of the music and updates the button text accordingly.
     *
     * @param muteMusicButton the button to update the text of
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

    /**
     * Toggles the mute state of the sound effect and updates the button text accordingly.
     *
     * @param muteSoundEffectButton the button to update the text of
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
}