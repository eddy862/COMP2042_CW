package com.example.demo.controller;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller controller;
    private Stage stage;
    private Music music;
    private SoundEffect soundEffect;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            controller = new Controller(stage, music, soundEffect);
            stage = new Stage();
            music = new Music();
            soundEffect = new SoundEffect();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void launchGameShowsStage() throws Exception {
        Platform.runLater(() -> {
            try {
                controller.launchGame();
                assertTrue(stage.isShowing());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
