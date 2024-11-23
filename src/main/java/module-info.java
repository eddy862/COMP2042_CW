module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports com.example.demo.controller;
    exports com.example.demo.level;
    exports com.example.demo.level.view;
    exports com.example.demo.actor;
    exports com.example.demo.actor.plane;
    exports com.example.demo.actor.projectile;
    exports com.example.demo.audio;
    exports com.example.demo.ui.page;
    exports com.example.demo.ui.inGameElement;

    opens com.example.demo.level to javafx.fxml;
    opens com.example.demo.actor to javafx.fxml;
    opens com.example.demo.actor.plane to javafx.fxml;
    opens com.example.demo.actor.projectile to javafx.fxml;
    opens com.example.demo.audio to javafx.fxml;
    opens com.example.demo.ui.page to javafx.fxml;
    opens com.example.demo.ui.inGameElement to javafx.fxml;
    opens com.example.demo.level.view to javafx.fxml;
}