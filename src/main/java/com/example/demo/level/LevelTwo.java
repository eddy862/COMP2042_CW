package com.example.demo.level;

import com.example.demo.actor.plane.Boss;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.LevelCompletedMenu;
import javafx.scene.Scene;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
    private static final int PLAYER_INITIAL_HEALTH = 10;
    private Boss boss;
    private LevelViewLevelTwo levelView;
    private final SoundEffect soundEffect;
    private boolean isBossShielded;

    public LevelTwo(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
        this.soundEffect = soundEffect;
        boss = boss == null ? new Boss() : boss;
        this.isBossShielded = boss.isShielded();
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            levelCompleted();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        // check is boss is initialised
        boss = boss == null ? new Boss() : boss;
        levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, boss.getHealth(), 1);
        return levelView;
    }

    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        levelView.displayShield();
        levelView.showBossHealth();
        return scene;
    }

    @Override
    protected void updateLevelView() {
        super.updateLevelView();
        // update shield position
        levelView.updateShieldPosition(boss.getLayoutX() + boss.getTranslateX(), boss.getLayoutY() + boss.getTranslateY());
        boolean bossWasShielded = isBossShielded;

        if (boss.isShielded()) {
            levelView.showShield();
            if (!bossWasShielded) {
                soundEffect.playActivateShield();
            }
        } else {
            levelView.hideShield();
            if (bossWasShielded) {
                soundEffect.playShieldDeactivate();
            }
        }

        isBossShielded = boss.isShielded();

        // update boss health
        levelView.updateBossHealth(boss.getHealth());
        levelView.updateBossHealthPosition(boss);
    }

    @Override
    protected LevelCompletedMenu showLevelCompletedMenu() {
        return new LevelCompletedMenu("Level Two", this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelTwo"), () -> goToNextLevel(NEXT_LEVEL), getScreenWidth(), getScreenHeight());
    }
}
