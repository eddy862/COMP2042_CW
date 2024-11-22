package com.example.demo.actor.plane;

public class MultiStageBoss extends Boss {
    private int stage;
    private final int stageTwoHealth;
    private static final String STAGE_TWO_IMAGE = "eagle.png";

    public MultiStageBoss(int stageOneHealth, int stageTwoHealth) {
        super(stageOneHealth + stageTwoHealth);
        this.stage = 1;
        this.stageTwoHealth = stageTwoHealth;
    }

    @Override
    public void takeDamage() {
        super.takeDamage();
        if (getHealth() <= stageTwoHealth) {
            advanceStage();
        }
    }

    public void advanceStage() {
        stage = 2;
        changeImage(STAGE_TWO_IMAGE);
    }

    public int getStage() {
        return stage;
    }
}
