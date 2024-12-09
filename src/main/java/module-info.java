module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.junit.jupiter.api;
    requires org.junit.platform.commons;
    requires org.junit.platform.launcher;

    exports com.example.demo.controller;
    exports com.example.demo.level;
    exports com.example.demo.level.view;
    exports com.example.demo.actor;
    exports com.example.demo.actor.plane;
    exports com.example.demo.actor.projectile;
    exports com.example.demo.audio;
    exports com.example.demo.ui.page;
    exports com.example.demo.ui.inGameElement;

    opens com.example.demo.actor.plane to org.junit.platform.commons, org.junit.platform.launcher;
    opens com.example.demo.actor.projectile to org.junit.platform.commons, org.junit.platform.launcher;
    opens com.example.demo.audio to org.junit.platform.commons, org.junit.platform.launcher;
    opens com.example.demo.controller to org.junit.platform.commons, org.junit.platform.launcher;
    opens com.example.demo.level to org.junit.platform.commons, org.junit.platform.launcher;
    opens com.example.demo.level.view to org.junit.platform.commons, org.junit.platform.launcher;
}