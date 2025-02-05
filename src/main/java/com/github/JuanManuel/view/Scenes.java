package com.github.JuanManuel.view;
/**
 * The Scenes enum contains all the scenes in the application.
 * Each scene is associated with an FXML file that defines the layout of the scene.
 */
public enum Scenes {
    // PRINCIPAL PAGE
    ROOT("view/layout.fxml"),
    // OTHERS PAGES
    LOGIN("view/login.fxml"),
    REGISTER("view/register.fxml"),

    HOME("view/home.fxml"),
    PERFIL("view/perfil.fxml"),

    HUELLAS("view/huellas.fxml"),
    EDITHUELLA("view/editHuella.fxml"),
    HABITOS("view/habitos.fxml"),
    EDITHABITO("view/editHabito.fxml"),
    SOCIAL("view/social.fxml"),




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
