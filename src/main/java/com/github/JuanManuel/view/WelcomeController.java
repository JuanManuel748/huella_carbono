package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.Session;
import com.github.JuanManuel.model.entities.Usuario;
import com.github.JuanManuel.model.services.usuarioService;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController extends Controller implements Initializable {
    /**
     * Called when the view is opened. This method is intended for any setup or initialization
     * operations when the controller is first initialized. In this case, it's empty.
     *
     * @param input the input object passed when opening the view.
     */
    @Override
    public void onOpen(Object input) throws Exception {
        Stage stage = (Stage) App.getPrimaryStage();

        if (stage != null) {
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.centerOnScreen();
        } else {
            Alert.showAlert("ERROR", "FATAL ERROR", "No se ha podido cargar la ventana principal.");
        }
    }

    /**
     * Called when the view is closed. This method handles any cleanup or state persistence
     * when the view is closed. In this case, it's empty.
     *
     * @param output the output object passed when opening another view.
     */
    @Override
    public void onClose(Object output) {}

    /**
     * Initializes the controller. This method is part of the Initializable interface and
     * is called when the controller is initialized.
     *
     * @param location the location used to load the FXML file.
     * @param resources the resources used for localization.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onOpen(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes the scene to the login view.
     */
    public void goLogin() throws Exception {
        App.currentController.changeScene(Scenes.LOGIN, null);

    }

}
