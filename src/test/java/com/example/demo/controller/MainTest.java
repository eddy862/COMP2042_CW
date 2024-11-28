package com.example.demo.controller;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    private Main main;
    private Stage stage;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            main = new Main();
            stage = new Stage();
            try {
                main.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mainMenuSceneIsInitialized() {
        assertNotNull(main.initializeScene());
    }

    @Test
    void mainMenuSceneHasCorrectTitle() {
        assertEquals("Sky Battle", stage.getTitle());
    }

    @Test
    void mainMenuSceneIsShowing() {
        Platform.runLater(() -> {
            try {
                assertTrue(stage.isShowing());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
