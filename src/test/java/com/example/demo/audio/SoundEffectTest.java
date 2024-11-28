package com.example.demo.audio;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SoundEffectTest {
    private SoundEffect soundEffect;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            soundEffect = new SoundEffect();
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void soundEffectIsInitiallyNotMuted() {
        assertFalse(soundEffect.isMuted());
    }

    @Test
    void soundEffectMuted() {
        soundEffect.mute();
        assertTrue(soundEffect.isMuted());
    }

    @Test
    void soundEffectUnmuted() {
        soundEffect.mute();
        soundEffect.unmute();
        assertFalse(soundEffect.isMuted());
    }
}
