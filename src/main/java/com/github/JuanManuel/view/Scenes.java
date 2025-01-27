package com.github.JuanManuel.view;
/**
 * The Scenes enum contains all the scenes in the application.
 * Each scene is associated with an FXML file that defines the layout of the scene.
 */
public enum Scenes {
    // PRINCIPAL PAGE
    ROOT("view/layout.fxml"),
    // OTHERS PAGES

    // WELCOME PAGE
    WELCOME("view/welcome.fxml");


    private final String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
