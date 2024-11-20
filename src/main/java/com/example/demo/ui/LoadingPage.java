package com.example.demo.ui;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class LoadingPage {
    private final Stage stage;
    private ProgressBar progressBar;
    private Text progressText;

    public LoadingPage(Stage stage) {
        this.stage = stage;
    }

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

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Text getProgressText() {
        return progressText;
    }
}