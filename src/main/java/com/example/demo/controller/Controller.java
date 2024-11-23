package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.page.LoadingPage;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;

/**
 * Controller class that manages the game flow and handles level transitions.
 */
public class Controller implements Observer {

    /**
     * The name of the first level class.
     */
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelOne";
    /**
     * The primary stage for the application.
     */
    private final Stage stage;
    /**
     * The music manager across the game.
     */
    private final Music music;
    /**
     * The sound effect manager across the game.
     */
    private final SoundEffect soundEffect;

    /**
     * Constructs a Controller with the specified stage, music, and sound effects.
     *
     * @param stage the primary stage for the application
     * @param music the music manager
     * @param soundEffect the sound effect manager
     */
    public Controller(Stage stage, Music music, SoundEffect soundEffect) {
        this.stage = stage;
        this.music = music;
        this.soundEffect = soundEffect;
    }

    /**
     * Launches the game by showing the stage and loading the first level.
     *
     * @throws ClassNotFoundException if the level class is not found
     * @throws NoSuchMethodException if the level class constructor is not found
     * @throws SecurityException if there is a security violation
     * @throws InstantiationException if the level class cannot be instantiated
     * @throws IllegalAccessException if the level class or its constructor is not accessible
     * @throws IllegalArgumentException if the arguments for the level class constructor are invalid
     * @throws InvocationTargetException if the constructor throws an exception
     */
    public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.show();
        goToLevel(LEVEL_ONE_CLASS_NAME);
    }

    /**
     * Loads the level with the given class name and handles the transition to the level scene.
     *
     * @param className the name of the class to load
     * @throws ClassNotFoundException if the level class is not found
     * @throws NoSuchMethodException if the level class constructor is not found
     * @throws SecurityException if there is a security violation
     * @throws InstantiationException if the level class cannot be instantiated
     * @throws IllegalAccessException if the level class or its constructor is not accessible
     * @throws IllegalArgumentException if the arguments for the level class constructor are invalid
     * @throws InvocationTargetException if the constructor throws an exception
     */
    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Show loading page
        LoadingPage loadingPage = new LoadingPage(stage);
        loadingPage.show();

        // Create a task to load the level and initialize its scene in a background thread
        final LevelParent[] myLevel = new LevelParent[1];
        Task<Scene> task = new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                // Simulate progress
                for (int i = 0; i <= 99; i++) {
                    updateProgress(i, 99);
                    updateMessage(i + "%");
                    Thread.sleep(10);
                }

                // Load the level class and initialize the scene
                Class<?> myClass = Class.forName(className);
                Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Music.class, SoundEffect.class);
                myLevel[0] = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), music, soundEffect);
                myLevel[0].addObserver(Controller.this);
                return myLevel[0].initializeScene();
            }
        };

        // Bind the progress bar's progress property to the task's progress property
        loadingPage.getProgressBar().progressProperty().bind(task.progressProperty());
        // Bind the progress text's text property to the task's message property
        loadingPage.getProgressText().textProperty().bind(task.messageProperty());

        // Set the task's onSucceeded event to update the UI once the level is loaded and done initializing its scene
        task.setOnSucceeded(event -> {
            try {
                stage.setScene(task.getValue());
                myLevel[0].startGame();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText(e.getClass().toString());
                alert.show();
            }
        });

        // Set the task's onFailed event to handle any exceptions
        task.setOnFailed(event -> {
            Throwable e = task.getException();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        });

        // Run the task in a background thread
        new Thread(task).start();
    }

    /**
     * This method is automatically called whenever the observed object is changed, usually used to handle level transitions.
     * @param arg0 the observable object
     * @param arg1 an argument passed to the {@code notifyObservers} method
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        try {
            goToLevel((String) arg1);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        }
    }

}