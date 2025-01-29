package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.Usuario;
import com.github.JuanManuel.utils.HashPass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.(com|es)$";
    /**
     * Called when the view is opened. This method is intended for any setup or initialization
     * operations when the controller is first initialized. In this case, it's empty.
     *
     * @param input the input object passed when opening the view.
     */
    @Override
    public void onOpen(Object input) throws Exception {}

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
    }

    public void goToRegister() throws Exception {
        App.currentController.changeScene(Scenes.REGISTER, null);
    }

    public void logIn() {
        try {
            String email = emailField.getText();
            String password = HashPass.hashPassword(passwordField.getText());

            if (validateFields(email, password)) {
                Usuario tempUser = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean validateFields(String email, String password) {
        boolean rs = false;
        if (email.isEmpty() || password.isEmpty()) {
            if (email.matches(EMAIL_REGEX)) {
                rs = true;
            }
        } else {
            rs = false;
        }
        return rs;
    }
}
