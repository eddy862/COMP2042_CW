package com.example.demo.ui;

import com.example.demo.audio.Music;
import com.example.demo.controller.Controller;
import com.example.demo.controller.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;

public class MainMenu {
    private Stage stage;
    private Controller myController;
    private static final String MAIN_MENU_CSS = "/com/example/demo/styles/mainMenu.css";
    private static final String BACKGROUND_VIDEO = "/com/example/demo/videos/mainMenuBackground.mp4";
    private final Music music = new Music();

    public MainMenu(Stage stage) {
        this.stage = stage;
        music.playMainMenuBackgroundMusic();
    }
    public Scene initializeScene() {
        Media background = new Media(getClass().getResource(BACKGROUND_VIDEO).toExternalForm());
        MediaPlayer backgroundPlayer = new MediaPlayer(background);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setVolume(0);
        backgroundPlayer.play();

        MediaView mediaView = new MediaView(backgroundPlayer);
        mediaView.setFitWidth(Main.SCREEN_WIDTH);
        mediaView.setFitHeight(Main.SCREEN_HEIGHT);

        Label welcomeLabel = new Label("Welcome to Sky Battle!");
        welcomeLabel.getStyleClass().add("label");

        Button playButton = new Button("Start the game");
        playButton.setOnAction(e -> {
            try {
                startLevelOne(stage);
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        playButton.getStyleClass().addAll("button", "play-button");

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(e -> {
                    showTutorial(stage);
                }
        );
        tutorialButton.getStyleClass().addAll("button", "tutorial-button");

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            stage.close();
        });
        quitButton.getStyleClass().addAll("button", "quit-button");

        VBox layout = new VBox(50);
        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(playButton, tutorialButton, quitButton);
        buttonLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(mediaView, layout);
        Scene scene = new Scene(root, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(MAIN_MENU_CSS).toExternalForm());

        return scene;
    }

    private void startLevelOne(Stage stage) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        music.stopMainMenuBackgroundMusic();
        myController = new Controller(stage);
        myController.launchGame();
    }

    private void showTutorial(Stage stage) {
        TutorialPage tutorialPage = new TutorialPage(this);
        Scene tutorialScene = tutorialPage.initializeScene(stage);
        stage.setScene(tutorialScene);
    }
}
