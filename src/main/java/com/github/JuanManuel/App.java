package com.github.JuanManuel;

import com.github.JuanManuel.view.AppController;
import com.github.JuanManuel.view.Controller;
import com.github.JuanManuel.view.Scenes;
import com.github.JuanManuel.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class of the application. It extends the JavaFX Application class and
 * is the entry point of the application.
 */
public class App extends Application {
    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;
    private static Map<String, Controller> controllers = new HashMap<>();


    public static void main(String[] args) {
        launch();
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public static Controller getController(String name) {
        return controllers.get(name);
    }

    public static void setController(String name, Controller controller) {
        controllers.put(name, controller);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 800, 600);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);

        stage.setScene(scene);
        stage.setTitle("Huella de Carbono");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/github/JuanManuel/assets/img/logo.png")));
        stage.show();
    }
}
