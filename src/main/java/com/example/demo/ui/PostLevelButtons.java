package com.example.demo.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PostLevelButtons {
    private final HBox layout;
    private static final double Y_POSITION = 550;
    private static final double WIDTH = 500;

    public PostLevelButtons(double screenWidth, Runnable onReturnToMainMenu, Runnable restartFromLevelOne) {
        layout = new HBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(WIDTH);
        layout.setLayoutX(screenWidth / 2 - WIDTH / 2);
        layout.setLayoutY(Y_POSITION);
        layout.setVisible(false);

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> restartFromLevelOne.run());
        restartButton.setStyle("-fx-font-size: 20; -fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-border-color: #000000; -fx-border-width: 2px;");

        Button returnToMainMenuButton = new Button("Main Menu");
        returnToMainMenuButton.setOnAction(e -> onReturnToMainMenu.run());
        returnToMainMenuButton.setStyle("-fx-font-size: 20; -fx-background-color: #ff0000; -fx-text-fill: #ffffff; -fx-border-color: #000000; -fx-border-width: 2px;");

        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(restartButton, returnToMainMenuButton);

        layout.getChildren().add(buttonLayout);
    }

    public HBox getLayout() {
        return layout;
    }

    public void show() {
        layout.setVisible(true);
    }
}