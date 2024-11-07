module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports com.example.demo.controller;

    opens com.example.demo.level to javafx.fxml;
    opens com.example.demo.ui to javafx.fxml;
    opens com.example.demo.actor to javafx.fxml;
    opens com.example.demo.actor.plane to javafx.fxml;
    opens com.example.demo.actor.projectile to javafx.fxml;
}