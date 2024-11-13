package com.example.demo.ui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PageAudio {
    private static final String BUTTON_CLICK = "/com/example/demo/audio/clickButton.mp3";
    Media clickSound;

    public PageAudio() {
        clickSound = new Media(getClass().getResource(BUTTON_CLICK).toExternalForm());
    }

    public void playButtonClick() {
        MediaPlayer clickSoundPlayer = new MediaPlayer(clickSound);
        clickSoundPlayer.play();
    }
}
