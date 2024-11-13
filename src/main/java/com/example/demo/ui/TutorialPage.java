package com.example.demo.ui;

import com.example.demo.controller.Main;
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

public class TutorialPage {
    private int currentGalleryIndex = 0;
    private List<Image> levelScreenshots = new ArrayList<>();
    private Main mainMenu;
    private List<String> levelName = new ArrayList<>();
    private List<String> levelDesc = new ArrayList<>();
    private ImageView imageView = new ImageView();
    private Label levelNameLabel;
    private Label levelDescLabel;
    private static final String TUTORIAL_PAGE_CSS = "/com/example/demo/styles/tutorial.css";

    public TutorialPage(Main mainMenu) {
        this.mainMenu = mainMenu;
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial1.png").toExternalForm()));
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial2.png").toExternalForm()));
        levelName.addAll(List.of("Tutorial 1", "Tutorial 2"));
        levelDesc.addAll(List.of("Target: kills a certain number of enemy planes.\nIf any enemy plan pass the leftmost boundary, the user will lose a life.",
                "Target: kills the boss plane.\nThe boss plane has a shield that can be activated randomly.")
        );
    }

    public Scene initializeScene(Stage stage) {
        BorderPane layout = new BorderPane();
        Label controTitleLabel = new Label("Controls");
        controTitleLabel.getStyleClass().add("title");
        Label controlLabel = new Label("W or UP - Move Up\nA or LEFT - Move Left\nS or DOWN - Move Down\nD or RIGHT - Move Right\nL or SPACE - Fire Projectile");
        controlLabel.getStyleClass().add("text");
        Label levelTitleLabel = new Label("Levels");
        levelTitleLabel.getStyleClass().add("title");
        levelNameLabel = new Label(levelName.get(currentGalleryIndex));
        levelNameLabel.getStyleClass().add("text");
        levelDescLabel = new Label(levelDesc.get(currentGalleryIndex));
        levelDescLabel.getStyleClass().add("text");

        VBox controlLayout = new VBox(10);
        controlLayout.getChildren().addAll(controTitleLabel, controlLabel);
        controlLayout.setAlignment(Pos.CENTER);

        imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        imageView.setFitWidth(600);
        imageView.setFitHeight(300);

        Button leftButton = new Button("Previous");
        leftButton.setOnAction(e -> showPreviousImage());
        leftButton.getStyleClass().addAll("button", "level-button");

        Button rightButton = new Button("Next");
        rightButton.setOnAction(e -> showNextImage());
        rightButton.getStyleClass().addAll("button", "level-button");

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(leftButton, levelNameLabel, rightButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox levelLayout = new VBox(10);
        levelLayout.getChildren().addAll(levelTitleLabel, buttonLayout, imageView, levelDescLabel);
        levelLayout.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(30);
        mainLayout.getChildren().addAll(controlLayout, levelLayout);
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu(stage));
        backButton.getStyleClass().add("button");

        layout.setTop(backButton);
        BorderPane.setAlignment(backButton, Pos.TOP_LEFT);
        BorderPane.setMargin(backButton, new Insets(10));
        layout.setCenter(mainLayout);

        Scene scene = new Scene(layout, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(TUTORIAL_PAGE_CSS).toExternalForm());

        return scene;
    }

    private void showMainMenu(Stage stage) {
        mainMenu.start(stage);
    }

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