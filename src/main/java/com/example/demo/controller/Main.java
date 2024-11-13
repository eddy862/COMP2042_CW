package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.ui.PageAudio;
import com.example.demo.ui.TutorialPage;
import javafx.application.Application;
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

public class Main extends Application {

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";
    private Controller myController;
    private static final String MAIN_PAGE_CSS = "/com/example/demo/styles/mainPage.css";
    private static final String BACKGROUND_VIDEO = "/com/example/demo/videos/mainMenuBackground.mp4";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/mainMenuBackgroundMusic.mp3";

    /**
     * Start the application by launching the game
     *
     * @param stage the primary stage for this application
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Override
    public void start(Stage stage) throws SecurityException,
            IllegalArgumentException {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);

        Media music = new Media(getClass().getResource(BACKGROUND_MUSIC).toExternalForm());
        MediaPlayer musicPlayer = new MediaPlayer(music);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();

        Media background = new Media(getClass().getResource(BACKGROUND_VIDEO).toExternalForm());
        MediaPlayer backgroundPlayer = new MediaPlayer(background);
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setVolume(0);
        backgroundPlayer.play();

        MediaView mediaView = new MediaView(backgroundPlayer);
        mediaView.setFitWidth(SCREEN_WIDTH);
        mediaView.setFitHeight(SCREEN_HEIGHT);

        PageAudio pageAudio = new PageAudio();

        Label welcomeLabel = new Label("Welcome to Sky Battle!");
        welcomeLabel.getStyleClass().add("label");

        Button playButton = new Button("Start the game");
        playButton.setOnAction(e -> {
            pageAudio.playButtonClick();
            try {
                startLevelOne(stage, musicPlayer);
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        playButton.getStyleClass().addAll("button", "play-button");

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(e -> {
                    pageAudio.playButtonClick();
                    showTutorial(stage);
                }
        );
        tutorialButton.getStyleClass().addAll("button", "tutorial-button");

        VBox layout = new VBox(50);
        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(playButton, tutorialButton);
        buttonLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(mediaView, layout);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(MAIN_PAGE_CSS).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void startLevelOne(Stage stage, MediaPlayer music) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        music.stop();
        myController = new Controller(stage);
        myController.launchGame();
    }

    private void showTutorial(Stage stage) {
        TutorialPage tutorialPage = new TutorialPage(this);
        Scene tutorialScene = tutorialPage.initializeScene(stage);
        stage.setScene(tutorialScene);
    }

    public static void main(String[] args) {
        launch();
    }
}