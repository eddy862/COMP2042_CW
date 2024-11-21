package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.ui.LoadingPage;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.level.LevelParent;

public class Controller implements Observer {

    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.level.LevelThree";
    private final Stage stage;
    private final Music music;
    private final SoundEffect soundEffect;

    public Controller(Stage stage, Music music, SoundEffect soundEffect) {
        this.stage = stage;
        this.music = music;
        this.soundEffect = soundEffect;
    }

    /**
     * Launch the game by showing the stage and loading the first level
     *
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.show();
        goToLevel(LEVEL_ONE_CLASS_NAME);
    }

    /**
     * Load the level with the given class name
     *
     * @param className the name of the class to load
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //show loading page
        LoadingPage loadingPage = new LoadingPage(stage);
        loadingPage.show();

        // create a task to load the level and initialise its scene in a background thread
        final LevelParent[] myLevel = new LevelParent[1];
        Task<Scene> task = new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                // simulate progress
                for (int i = 0; i <= 99; i++) {
                    updateProgress(i, 99);
                    updateMessage(i + "%");
                    Thread.sleep(10);
                }

                // load the level class and initialize the scene
                Class<?> myClass = Class.forName(className);
                Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Music.class, SoundEffect.class);
                myLevel[0] = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), music, soundEffect);
                myLevel[0].addObserver(Controller.this);
                return myLevel[0].initializeScene();
            }
        };

        // bind the progress bar's progress property to the task's progress property
        loadingPage.getProgressBar().progressProperty().bind(task.progressProperty());
        // bind the progress text's text property to the task's message property
        loadingPage.getProgressText().textProperty().bind(task.messageProperty());

        // set the task's onSucceeded event to update the UI once the level is loaded and done initialising its scene
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

        // set the task's onFailed event to handle any exceptions
        task.setOnFailed(event -> {
            Throwable e = task.getException();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText(e.getClass().toString());
            alert.show();
        });

        // run the task in a background thread
        new Thread(task).start();
    }

    /**
     * This method is called whenever the observed object is changed. An
     *
     * @param arg0 the observable object.
     * @param arg1 an argument passed to the {@code notifyObservers} method.
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
