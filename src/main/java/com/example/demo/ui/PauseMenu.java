package com.example.demo.ui;

import com.example.demo.controller.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PauseMenu {
    private static final double WIDTH = 500;
    private static final double HEIGHT = 200;
    private VBox layout;
    private static final String PAUSE_MENU_CSS = "/com/example/demo/styles/pauseMenu.css";

    public PauseMenu(Runnable onResume, Runnable onReturnToMainMenu) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setVisible(false);
        layout.getStyleClass().add("pause-menu");
        layout.setPrefWidth(WIDTH);
        layout.setPrefHeight(HEIGHT);
        layout.setLayoutX(Main.SCREEN_WIDTH / 2 - WIDTH / 2);
        layout.setLayoutY(Main.SCREEN_HEIGHT / 2 - HEIGHT / 2);

        HBox layout1 = new HBox(20);
        Label pauseLabel = new Label("Game Paused");
        pauseLabel.getStyleClass().add("label");
        pauseLabel.getStyleClass().add("title");

        Button resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> onResume.run());
        resumeButton.getStyleClass().add("button");

        Button returnToMainMenuButton = new Button("Return to Main Menu");
        returnToMainMenuButton.setOnAction(e -> onReturnToMainMenu.run());
        returnToMainMenuButton.getStyleClass().add("button");

        layout1.getChildren().addAll(resumeButton, returnToMainMenuButton);
        layout1.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(pauseLabel, layout1);
        layout.getStylesheets().add(getClass().getResource(PAUSE_MENU_CSS).toExternalForm());
    }

    public void show() {
        layout.setVisible(true);
    }

    public void hide() {
        layout.setVisible(false);
    }

    public VBox getLayout() {
        return layout;
    }
}
