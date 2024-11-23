package com.example.demo.actor.plane;

/**
 * Represents a multi-stage boss in the game.
 * The multi-stage boss has two stages. The boss advances to the second stage when its health is below a certain threshold.
 */
public class MultiStageBoss extends Boss {
    /**
     * The current stage of the multi-stage boss.
     */
    private int stage;
    /**
     * The health of the multi-stage boss in the second stage.
     */
    private final int stageTwoHealth;
    /**
     * The name of the image file of the multi-stage boss in the second stage.
     */
    private static final String STAGE_TWO_IMAGE = "eagle.png";

    /**
     * Constructs a MultiStageBoss with the specified health for each stage.
     *
     * @param stageOneHealth the health of the multi-stage boss in the first stage
     * @param stageTwoHealth the health of the multi-stage boss in the second stage
     */
    public MultiStageBoss(int stageOneHealth, int stageTwoHealth) {
        super(stageOneHealth + stageTwoHealth);
        this.stage = 1;
        this.stageTwoHealth = stageTwoHealth;
    }

    /**
     * Decreases the health of the multi-stage boss by 1. If the health of the multi-stage boss reaches 0, the multi-stage boss is destroyed.
     * If the health of the multi-stage boss is below the threshold for the second stage, the multi-stage boss advances to the second stage.
     */
    @Override
    public void takeDamage() {
        super.takeDamage();
        if (getHealth() <= stageTwoHealth) {
            advanceStage();
        }
    }

    /**
     * Advances the multi-stage boss to the second stage. The image of the multi-stage boss changes when it advances to the second stage.
     */
    public void advanceStage() {
        stage = 2;
        changeImage(STAGE_TWO_IMAGE);
    }

    /**
     * Returns the current stage of the multi-stage boss.
     *
     * @return the current stage of the multi-stage boss
     */
    public int getStage() {
        return stage;
    }
}
