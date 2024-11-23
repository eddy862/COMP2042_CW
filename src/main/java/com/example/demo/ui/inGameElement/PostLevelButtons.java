package com.example.demo.ui.inGameElement;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Represents the buttons displayed after a level is completed.
 * This class creates a layout with options to return to the main menu or restart from level one.
 */
public class PostLevelButtons {
    /**
     * The layout of the post level buttons.
     */
    private final HBox layout;
    /**
     * The y position of the post level buttons.
     */
    private static final double Y_POSITION = 550;
    /**
     * The width of the post level buttons.
     */
    private static final double WIDTH = 500;

    /**
     * Constructs a PostLevelButtons object with the specified parameters.
     *
     * @param screenWidth the width of the screen
     * @param onReturnToMainMenu the action to perform when returning to the main menu
     * @param restartFromLevelOne the action to perform when restarting from level one
     */
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

    /**
     * Retrieves the layout of the post level buttons.
     *
     * @return the layout of the post level buttons
     */
    public HBox getLayout() {
        return layout;
    }

    /**
     * Shows the post level buttons.
     */
    public void show() {
        layout.setVisible(true);
    }
}