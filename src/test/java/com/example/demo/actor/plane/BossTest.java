package com.example.demo.actor.plane;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BossTest {
    private Boss boss;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        Platform.runLater(() -> {
            boss = new Boss(100);
        });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void bossHealth() {
        assertEquals(100, boss.getHealth());
    }


    @Test
    void bossActivatesShield() {
        boolean shielded = false;
        for (int i = 0; i < 1000; i++) {
            boss.updateActor();
            if (boss.isShielded()) {
                shielded = true;
                break;
            }
        }
        assertTrue(shielded);

        boss.takeDamage();
        assertEquals(100, boss.getHealth());
    }

    @Test
    void bossTakesDamageWhenNotShielded() {
        int initialHealth = boss.getHealth();
        boss.takeDamage();
        assertEquals(initialHealth - 1, boss.getHealth());
    }
}
