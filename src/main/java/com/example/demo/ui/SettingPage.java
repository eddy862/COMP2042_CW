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
    private MainMenu mainMenu;
    private Music music;
    private final SoundEffect soundEffect;
    private static final String SETTING_PAGE_CSS = "/com/example/demo/styles/setting.css";

    public SettingPage(MainMenu mainMenu, Music music, SoundEffect soundEffect) {
        this.mainMenu = mainMenu;
        this.music = music;
        this.soundEffect = soundEffect;
    }
    public Scene initializeScene(Stage stage) {
        VBox vBox = new VBox();

        Label settingLabel = new Label("Settings");
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
        backButton.setOnAction(e -> showMainMenu(stage));
        backButton.getStyleClass().add("button");

        vBox.getChildren().addAll(settingLabel, hBox, backButton);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStylesheets().add(getClass().getResource(SETTING_PAGE_CSS).toExternalForm());

        return new Scene(vBox, stage.getWidth(), stage.getHeight());
    }

    private void showMainMenu(Stage stage) {
        stage.setScene(mainMenu.initializeScene());
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
