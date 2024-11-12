package com.example.demo.ui;

import com.example.demo.controller.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TutorialPage {
    private int currentGalleryIndex = 0;
    private List<Image> levelScreenshots = new ArrayList<>();

    public TutorialPage() {
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial1.png").toExternalForm()));
        levelScreenshots.add(new Image(getClass().getResource("/com/example/demo/images/tutorial2.png").toExternalForm()));
    }

    public Scene initializeScene(Stage stage) {
        VBox layout = new VBox(20);
        Label tutorialLabel = new Label("This is the tutorial page.");

        ImageView imageView = new ImageView();
        imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);

        Button leftButton = new Button("Left");
        leftButton.setOnAction(e -> showPreviousImage(imageView));

        Button rightButton = new Button("Right");
        rightButton.setOnAction(e -> showNextImage(imageView));

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(leftButton, rightButton);

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> showMainMenu(stage));

        layout.getChildren().addAll(backButton, tutorialLabel, buttonLayout, imageView);

        return new Scene(layout, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }

    private void showMainMenu(Stage stage) {
        Main mainMenu = new Main();
        mainMenu.start(stage);
    }

    private void showPreviousImage(ImageView imageView) {
        if (currentGalleryIndex > 0) {
            currentGalleryIndex--;
            imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        } else {
            currentGalleryIndex = levelScreenshots.size() - 1;
            imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        }
    }

    private void showNextImage(ImageView imageView) {
        if (currentGalleryIndex < levelScreenshots.size() - 1) {
            currentGalleryIndex++;
            imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        } else {
            currentGalleryIndex = 0;
            imageView.setImage(levelScreenshots.get(currentGalleryIndex));
        }
    }
}