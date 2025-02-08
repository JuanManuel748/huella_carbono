package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.Usuario;
import com.github.JuanManuel.model.services.usuarioService;
import com.github.JuanManuel.utils.HashPass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {
    @FXML
    public TextField nameField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField repassField;

    private String email_regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


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

    public void goToLogin() throws Exception {
        App.currentController.changeScene(Scenes.LOGIN, null);
    }

    public void register() {
        if (validate()) {
            try {
            Usuario user = new Usuario();
            user.setNombre(nameField.getText());
            user.setEmail(emailField.getText());
            user.setContraseña(HashPass.hashPassword(passwordField.getText()));
            user.setFechaRegistro(LocalDate.now());
            if (usuarioService.build().insert(user)) {
                Alert.showAlert("INFORMATION", "Usuario registrado", "El usuario ha sido registrado exitosamente");
                App.currentController.changeScene(Scenes.LOGIN, null);
            } else {
                Alert.showAlert("ERROR", "Error al registrar", "Ha ocurrido un error al registrar el usuario");
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private boolean validate() {
        boolean result = false;

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String repass = repassField.getText();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !repass.isEmpty()) {
            if (email.matches(email_regex)) {
                if (password.equals(repass)) {
                    Usuario exists = usuarioService.build().findByPK(new Usuario(email));
                    if (exists == null) {
                        result = true;
                    } else {
                        Alert.showAlert("ERROR", "Usuario existente", "Ya existe un usuario con ese correo electrónico");
                    }
                } else {
                    Alert.showAlert("ERROR", "Contraseñas no coinciden", "Las contraseñas no coinciden");
                }
            } else {
                Alert.showAlert("ERROR", "Formato de correo inválido", "El correo debe tener un formato valido, por ejemplo: ejemplo@gmail.com");
            }
        } else {
            Alert.showAlert("ERROR", "Campos vacíos", "Por favor, rellene todos los campos");
        }
        return result;
    }
}