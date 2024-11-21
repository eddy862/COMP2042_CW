package com.example.demo.ui;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingPage {
    private final Scene mainMenuScene;
    private final Music music;
    private final SoundEffect soundEffect;
    private static final String SETTING_PAGE_CSS = "/com/example/demo/styles/setting.css";
    private final Stage stage;

    public SettingPage(Scene mainMenuScene, Music music, SoundEffect soundEffect) {
        this.mainMenuScene = mainMenuScene;
        this.music = music;
        this.soundEffect = soundEffect;
        this.stage = (Stage) mainMenuScene.getWindow();
    }
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

    private void showMainMenu() {
        stage.setScene(mainMenuScene);
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