package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.Session;
import com.github.JuanManuel.model.entities.Usuario;
import com.github.JuanManuel.model.services.usuarioService;
import com.github.JuanManuel.utils.HashPass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController extends Controller implements Initializable {
    @FXML
    public Button save_btn;
    @FXML
    public TextField passField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField nameField;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.(com|es)$";

    /**
     * Called when the view is opened. This method is intended for any setup or initialization
     * operations when the controller is first initialized. In this case, it's empty.
     *
     * @param input the input object passed when opening the view.
     */
    @Override
    public void onOpen(Object input) throws Exception {
    }

    /**
     * Called when the view is closed. This method handles any cleanup or state persistence
     * when the view is closed. In this case, it's empty.
     *
     * @param output the output object passed when opening another view.
     */
    @Override
    public void onClose(Object output) {
    }


    /**
     * Initializes the controller. This method is part of the Initializable interface and
     * is called when the controller is initialized.
     *
     * @param url            the location used to load the FXML file.
     * @param resourceBundle the resources used for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario u = Session.getInstance().getCurrentUser();
        nameField.setText(u.getNombre());
        emailField.setText(u.getEmail());
        passField.setPromptText("Editar solamente si desea cambiar la contraseña");
    }


    public void save() {
        Usuario u = Session.getInstance().getCurrentUser();
        if (validate()) {
            u.setNombre(nameField.getText());
            u.setEmail(emailField.getText());
            if (!passField.getText().isEmpty()) {
                u.setContraseña(HashPass.hashPassword(passField.getText()));
            }
            if (Alert.showConfirmation("¿Seguro?", "¿Estas seguro de cambiar tus datos?")) {
                if (usuarioService.build().update(u)) {
                    Alert.showAlert("INFORMATION", "Cambios guardados", "Se han guardado los cambios en tu cuenta");
                } else {
                    Alert.showAlert("ERROR", "No se pudo guardar", "No se han podido aplicar los cambios a tu cuenta");
                }
            }
        }

    }

    public boolean validate() {
        boolean result = false;
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        if (!name.isEmpty() || !email.isEmpty()) {
            if (email.matches(EMAIL_REGEX)) {
                result = true;
            } else {
                Alert.showAlert("ERROR", "Email incorrecto", "Por favor, introduce un email válido");
            }
        } else {
            Alert.showAlert("ERROR", "Datos incorrectos", "Por favor, rellena todos los campos correctamente");
        }
        return result;
    }

    public void logout() throws Exception {
        Session.getInstance().logOut();
        App.currentController.changeScene(Scenes.WELCOME, null);


    }


}
