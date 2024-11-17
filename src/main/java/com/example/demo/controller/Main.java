package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.ui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";

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

        MainMenu mainMenu = new MainMenu(stage);
        stage.setScene(mainMenu.initializeScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}