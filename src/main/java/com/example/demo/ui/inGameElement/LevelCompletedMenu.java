package com.example.demo.ui.inGameElement;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents the menu displayed when a level is completed.
 * This class creates a layout with options to return to the main menu, restart the level, or proceed to the next level.
 */
public class LevelCompletedMenu {
    /**
     * The width of the level completed menu.
     */
    private static final double WIDTH = 500;
    /**
     * The height of the level completed menu.
     */
    private static final double HEIGHT = 200;
    /**
     * The layout of the level completed menu.
     */
    private final VBox layout;
    /**
     * The path to the CSS file for the level completed menu.
     */
    private static final String LEVEL_COMPLETE_MENU_CSS = "/com/example/demo/styles/levelComplete.css";

    /**
     * Constructs a LevelCompletedMenu object with the specified parameters.
     *
     * @param levelName the name of the completed level
     * @param onReturnToMainMenu the action to perform when returning to the main menu
     * @param restart the action to perform when restarting the level
     * @param nextLevel the action to perform when proceeding to the next level
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     */
    public LevelCompletedMenu(String levelName, Runnable onReturnToMainMenu, Runnable restart, Runnable nextLevel, double screenWidth, double screenHeight) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(WIDTH);
        layout.setPrefHeight(HEIGHT);
        layout.setLayoutX(screenWidth / 2 - WIDTH / 2);
        layout.setLayoutY(screenHeight / 2 - HEIGHT / 2);
        layout.getStyleClass().add("level-complete-menu");

        Label levelCompletedLabel = new Label("Level Completed: " + levelName);
        levelCompletedLabel.getStyleClass().add("title");

        Button restartButton = new Button("Replay");
        restartButton.setOnAction(e -> restart.run());
        restartButton.getStyleClass().add("button");

        Button returnToMainMenuButton = new Button("Main Menu");
        returnToMainMenuButton.setOnAction(e -> onReturnToMainMenu.run());
        returnToMainMenuButton.getStyleClass().add("button");

        Button nextLevelButton = new Button("Next Level");
        nextLevelButton.setOnAction(e -> nextLevel.run());
        nextLevelButton.getStyleClass().add("button");

        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(restartButton, returnToMainMenuButton, nextLevelButton);

        layout.getChildren().addAll(levelCompletedLabel, buttonLayout);
        layout.getStylesheets().add(getClass().getResource(LEVEL_COMPLETE_MENU_CSS).toExternalForm());
    }

    /**
     * Retrieves the layout of the level completed menu.
     *
     * @return the layout of the level completed menu
     */
    public VBox getLayout() {
        return layout;
    }
}