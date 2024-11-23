package com.example.demo.ui.page;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

/**
 * Represents a loading page in the application.
 * This class creates a loading screen with a progress bar and text to indicate loading progress.
 */
public class LoadingPage {
    /**
     * The stage to display the loading page on.
     */
    private final Stage stage;
    /**
     * The progress bar of the loading page showing the loading progress.
     */
    private ProgressBar progressBar;
    /**
     * The progress text of the loading page showing the loading progress percentage.
     */
    private Text progressText;

    /**
     * Constructs a LoadingPage object with the specified stage.
     *
     * @param stage the stage to display the loading page on
     */
    public LoadingPage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Displays the loading page.
     * Initializes and sets up the layout, progress bar, and text elements.
     */
    public void show() {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #2c3e50;");
        Text loadingText = new Text("Loading...");
        loadingText.setFont(new Font(30));
        loadingText.setFill(Color.WHITE);

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setStyle("-fx-accent: #3498db");

        progressText = new Text("0%");
        progressText.setFont(new Font(16));
        progressText.setFill(Color.WHITE);

        root.getChildren().addAll(loadingText, progressBar, progressText);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
    }

    /**
     * Retrieves the progress bar of the loading page.
     *
     * @return the progress bar of the loading page
     */
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * Retrieves the progress text of the loading page.
     *
     * @return the progress text of the loading page
     */
    public Text getProgressText() {
        return progressText;
    }
}