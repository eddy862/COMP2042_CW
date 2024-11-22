package com.example.demo.level;

import com.example.demo.actor.plane.Boss;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.LevelCompletedMenu;
import javafx.scene.Scene;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.level.LevelThree";
    private static final int PLAYER_INITIAL_HEALTH = 6;
    private Boss boss;
    private static final int BOSS_HEALTH = 15;
    private LevelViewLevelTwo levelView;
    private final SoundEffect soundEffect;
    private boolean isBossShielded;

    public LevelTwo(double screenHeight, double screenWidth, Music music, SoundEffect soundEffect) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, music, soundEffect);
        this.soundEffect = soundEffect;
        boss = new Boss(BOSS_HEALTH);
        this.isBossShielded = boss.isShielded();
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
            levelView.hideBossHealth();
        } else if (boss.isDestroyed()) {
            levelCompleted();
            levelView.hideBossHealth();
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
        levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, BOSS_HEALTH, 1);
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
        levelView.updateShieldPosition(boss);
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
