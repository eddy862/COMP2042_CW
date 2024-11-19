package com.example.demo.level;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;

public class LevelThree extends LevelParent {
    private LevelViewLevelOne levelView;
    private static final int PLAYER_INITIAL_HEALTH = 5;
    public LevelThree(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Music music, SoundEffect soundEffect) {
        super(backgroundImageName, screenHeight, screenWidth, playerInitialHealth, music, soundEffect);
    }

    @Override
    protected void initializeFriendlyUnits() {

    }

    @Override
    protected void checkIfGameOver() {

    }

    @Override
    protected void spawnEnemyUnits() {

    }

    @Override
    protected LevelView instantiateLevelView() {
        this.levelView = new LevelViewLevelOne(getRoot(), PLAYER_INITIAL_HEALTH , 0, 0);
        return levelView;
    }

    @Override
    protected void initialiseLevelScene() {

    }

    @Override
    protected void updateSpecificLevelView() {

    }
}
