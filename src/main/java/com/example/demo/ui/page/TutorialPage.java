package com.example.demo.ui.page;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the tutorial page in the application.
 * This class provides methods to initialize and display the tutorial page,
 * showing controls and level information with screenshots.
 */
public class TutorialPage {
    /**
     * The index of the current image in the gallery.
     */
    private int currentGalleryIndex = 0;
    /**
     * The list of level screenshots.
     */
    private final List<Image> levelScreenshots = new ArrayList<>();
    /**
     * The list of level names.
     */
    private final List<String> levelName = new ArrayList<>();
    /**
     * The list of level descriptions.
     */
    private final List<String> levelDesc = new ArrayList<>();
    /**
     * The image view to display the level screenshots.
     */
    private final ImageView imageView = new ImageView();
    /**
     * The label to display the level name.
     */
    private Label levelNameLabel;
    /**
     * The label to display the level description.
     */
    private Label levelDescLabel;
    /**
     * The stage to display the tutorial page on.
     */
    private final Stage stage;
    /**
     * The main menu scene to return to.
     */
    private final Scene mainMenuScene;

    /**
     * Constructs a TutorialPage object with the specified main menu scene.
     * Initializes the level screenshots, names, and descriptions.
     *
     * @param mainMenuScene the main menu scene to return to
     */
    public TutorialPage(Scene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
        this.stage = (Stage) mainMenuScene.getWindow();

        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial1.png").toExternalForm()));
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial2.png").toExternalForm()));
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial3.png").toExternalForm()));
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial4.png").toExternalForm()));
        levelName.addAll(List.of("Level 1", "Level 2", "Level 3", "Level 4"));
        levelDesc.addAll(List.of("Target: kill a certain number of enemy planes.\nIf any enemy plane passes the leftmost boundary, the user will lose a life.",
                "Target: kill the boss plane.\nThe boss plane has a shield that can be activated randomly.",
                "Target: survive for 20 seconds.\nYou only have 8 projectiles. Every 2 seconds, you will gain 1 projectile.",
                "Target: kill the multi-stage boss.\nThe boss plane has a shield that can be activated randomly. At stage 2, the boss will summon enemy planes."
        ));
    }

    /**
     * Initializes and returns the tutorial scene.
     * Sets up the layout, controls, and level information.
     *
     * @return the initialized tutorial scene
     */
    public Scene initializeScene() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #2c3e50;");
        Label controTitleLabel = new Label("Controls");
        controTitleLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");
        Label controlLabel = new Label("W or UP - Move Up\t\tA or LEFT - Move Left\tS or DOWN - Move Down\nD or RIGHT - Move Right\tL or SPACE - Fire Projectile\tESC or P - Pause Game");
        controlLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: 400;");
        Label levelTitleLabel = new Label("Levels");
        levelTitleLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");
        levelNameLabel = new Label(levelName.get(currentGalleryIndex));
        levelNameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: 400;");
        levelDescLabel = new Label(levelDesc.get(currentGalleryIndex));
        levelDescLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-font-weight: 400;");

        VBox controlLayout = new VBox(10);
        controlLayout.getChildren().addAll(controTitleLabel, controlLabel);
        controlLayout.setAlignment(Pos.CENTER);

        imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        imageView.setFitWidth(600);
        imageView.setFitHeight(300);

        Button leftButton = new Button("Previous");
        leftButton.setOnAction(e -> showPreviousImage());
        leftButton.setStyle("-fx-font-size: 16px; -fx-padding: 5px 15px; -fx-background-radius: 5px; -fx-background-color: #007bff; -fx-text-fill: white;");

        Button rightButton = new Button("Next");
        rightButton.setOnAction(e -> showNextImage());
        rightButton.setStyle("-fx-font-size: 16px; -fx-padding: 5px 15px; -fx-background-radius: 5px; -fx-background-color: #007bff; -fx-text-fill: white;");

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(leftButton, levelNameLabel, rightButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox levelLayout = new VBox(10);
        levelLayout.getChildren().addAll(levelTitleLabel, buttonLayout, imageView, levelDescLabel);
        levelLayout.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(30);
        mainLayout.getChildren().addAll(controlLayout, levelLayout);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainMenu());
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-background-color: grey; -fx-text-fill: white;");

        layout.setTop(backButton);
        BorderPane.setAlignment(backButton, Pos.TOP_LEFT);
        BorderPane.setMargin(backButton, new Insets(10));
        layout.setCenter(mainLayout);

        return new Scene(layout, stage.getWidth(), stage.getWidth());
    }

    /**
     * Shows the main menu scene.
     */
    private void showMainMenu() {
        stage.setScene(mainMenuScene);
    }

    /**
     * Shows the previous image in the gallery.
     * Updates the image view, level name, and description.
     */
    private void showPreviousImage() {
        if (currentGalleryIndex > 0) {
            currentGalleryIndex--;
        } else {
            currentGalleryIndex = levelScreenshots.size() - 1;
        }

        imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        levelNameLabel.setText(levelName.get(currentGalleryIndex));
        levelDescLabel.setText(levelDesc.get(currentGalleryIndex));
    }

    /**
     * Shows the next image in the gallery.
     * Updates the image view, level name, and description.
     */
    private void showNextImage() {
        if (currentGalleryIndex < levelScreenshots.size() - 1) {
            currentGalleryIndex++;
        } else {
            currentGalleryIndex = 0;
        }

        imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        levelNameLabel.setText(levelName.get(currentGalleryIndex));
        levelDescLabel.setText(levelDesc.get(currentGalleryIndex));
    }
}