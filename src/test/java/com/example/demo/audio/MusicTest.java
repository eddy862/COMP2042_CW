package com.example.demo.audio;

import com.example.demo.actor.projectile.UserProjectile;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MusicTest {
    private Music music;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            music = new Music();
        });

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void musicIsInitiallyNotMuted() {
        assertFalse(music.isMuted());
    }

    @Test
    void musicMuted() {
        music.mute();
        assertTrue(music.isMuted());
    }

    @Test
    void musicUnmuted() {
        music.mute();
        music.unmute();
        assertFalse(music.isMuted());
    }
}
