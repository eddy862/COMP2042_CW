package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.page.SettingPage;
import com.example.demo.ui.page.TutorialPage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * The main class that launches the game. Serve as the entry point of the game.
 */
public class Main extends Application {
    /**
     * The width of the screen.
     */
    public static final int SCREEN_WIDTH = 1300;
    /**
     * The height of the screen.
     */
    public static final int SCREEN_HEIGHT = 750;
    /**
     * The title of the game.
     */
    private static final String TITLE = "Sky Battle";
    /**
     * The primary stage for the application.
     */
    private Stage stage;
    /**
     * The scene for the main menu.
     */
    private Scene scene;
    /**
     * The CSS file for the main menu.
     */
    private static final String MAIN_MENU_CSS = "/com/example/demo/styles/mainMenu.css";
    /**
     * The background video for the main menu.
     */
    private static final String BACKGROUND_VIDEO = "/com/example/demo/videos/mainMenuBackground.mp4";
    /**
     * The music manager across the game.
     */
    private final Music music = new Music();
    /**
     * The sound effect manager across the game.
     */
    private final SoundEffect soundEffect = new SoundEffect();

    /**
     * The media player for the background video.
     */
    private MediaPlayer backgroundPlayer;

    /**
     * Start the application by launching the game
     *
     * @param stage the primary stage for this application
     * @throws SecurityException if there is a security violation
     * @throws IllegalArgumentException if the arguments for the level class constructor are invalid
     */
    @Override
    public void start(Stage stage) throws SecurityException,
            IllegalArgumentException {
        backgroundPlayer = new MediaPlayer(new Media(getClass().getResource(BACKGROUND_VIDEO).toExternalForm()));
        backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundPlayer.setVolume(0);
        backgroundPlayer.play();

        music.playMainMenuBackgroundMusic();
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
        this.stage = stage;
        this.scene = initializeScene();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the main menu scene.
     *
     * @return the initialized scene
     */
    public Scene initializeScene() {
        MediaView mediaView = new MediaView(backgroundPlayer);
        mediaView.setFitWidth(stage.getWidth());
        mediaView.setFitHeight(stage.getHeight());

        Label welcomeLabel = new Label("Welcome to Sky Battle!");
        welcomeLabel.getStyleClass().add("label");

        Button playButton = new Button("Start Game");
        playButton.setOnAction(e -> {
            try {
                startLevelOne();
            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        playButton.getStyleClass().addAll("button", "play-button");

        Button tutorialButton = new Button("Tutorial");
        tutorialButton.setOnAction(e -> {
                    showTutorial();
                }
        );
        tutorialButton.getStyleClass().addAll("button", "tutorial-button");

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            stage.close();
        });
        quitButton.getStyleClass().addAll("button", "quit-button");

        Button settingButton = new Button("Audio Setting");
        settingButton.setOnAction(e -> showSetting(stage));
        settingButton.getStyleClass().addAll("button", "setting-button");

        VBox layout = new VBox(50);
        VBox buttonLayout = new VBox(20);
        buttonLayout.getChildren().addAll(playButton, tutorialButton, settingButton, quitButton);
        buttonLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(welcomeLabel, buttonLayout);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(mediaView, layout);
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        scene.getStylesheets().add(getClass().getResource(MAIN_MENU_CSS).toExternalForm());

        return scene;
    }

    /**
     * Starts the first level of the game.
     *
     * @throws ClassNotFoundException if the level class is not found
     * @throws NoSuchMethodException if the level class constructor is not found
     * @throws SecurityException if there is a security violation
     * @throws InstantiationException if the level class cannot be instantiated
     * @throws IllegalAccessException if the level class or its constructor is not accessible
     * @throws IllegalArgumentException if the arguments for the level class constructor are invalid
     * @throws InvocationTargetException if the constructor throws an exception
     */
    private void startLevelOne() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        music.stopMainMenuBackgroundMusic();
        Controller myController = new Controller(stage, music, soundEffect);
        myController.launchGame();
    }

    /**
     * Shows the tutorial page.
     */
    private void showTutorial() {
        TutorialPage tutorialPage = new TutorialPage(scene);
        Scene tutorialScene = tutorialPage.initializeScene();
        stage.setScene(tutorialScene);
    }

    /**
     * Shows the settings page.
     *
     * @param stage the primary stage for this application
     */
    private void showSetting(Stage stage) {
        SettingPage settingPage = new SettingPage(scene, music, soundEffect);
        Scene settingScene = settingPage.initializeScene();
        stage.setScene(settingScene);
    }

    /**
     * The main method to launch the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}