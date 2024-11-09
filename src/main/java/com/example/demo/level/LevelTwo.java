package com.example.demo.level;

import com.example.demo.actor.plane.Boss;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 10;
    private Boss boss;
    private LevelViewLevelTwo levelView;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
        boss = boss == null ? new Boss() : boss;
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
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
        levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, boss.getHealth());
        return levelView;
    }

    @Override
    protected void initialiseLevelScene() {
        levelView.displayShield();
        levelView.showBossHealth();
    }

    @Override
    protected void updateSpecificLevelView() {
        // update shield position
        levelView.updateShieldPosition(boss.getLayoutX() + boss.getTranslateX(), boss.getLayoutY() + boss.getTranslateY());

        if (boss.isShielded()) {
            levelView.showShield();
        } else {
            levelView.hideShield();
        }

        // update boss health
        levelView.updateBossHealth(boss.getHealth());
    }
}
