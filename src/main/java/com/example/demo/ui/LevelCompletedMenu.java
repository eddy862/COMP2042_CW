package com.example.demo.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LevelCompletedMenu {
    private static final double WIDTH = 500;
    private static final double HEIGHT = 200;
    private final VBox layout;
    private static final String LEVEL_COMPLETE_MENU_CSS = "/com/example/demo/styles/levelComplete.css";

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

    public VBox getLayout() {
        return layout;
    }
}