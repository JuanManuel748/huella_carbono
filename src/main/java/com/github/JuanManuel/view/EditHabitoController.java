package com.github.JuanManuel.view;

import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Habito;
import com.github.JuanManuel.model.entities.Session;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.habitoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JuanManuel.view.HuellasController.selected;
import static javafx.collections.FXCollections.observableArrayList;

public class EditHabitoController extends Controller implements Initializable {

    @FXML
    public ComboBox<Actividad> act_cho;
    @FXML
    public Spinner<Integer> frec_input;
    @FXML
    public ComboBox<String> type_cho;
    @FXML
    public DatePicker date_input;
    @FXML
    public ImageView preview_img;


    List<Actividad> act_ls = new ArrayList<>();
    Habito currentHabito = new Habito();
    Actividad currentAct = new Actividad();

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
     * @param url the location used to load the FXML file.
     * @param resourceBundle the resources used for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        act_ls = actividadService.build().findAll();
        date_input.setValue(LocalDate.now());
        currentAct = act_ls.get(0);
        act_cho.setItems(observableArrayList(act_ls));
        act_cho.setValue(currentAct);
        act_cho.setOnAction(e -> {setAct();});
        frec_input.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31, 1));
        setAct();
        type_cho.setItems(observableArrayList("Diario", "Semanal", "Mensual"));
    }


    private void setAct() {
        currentAct = act_cho.getSelectionModel().getSelectedItem();
        preview_img.setImage(new Image(getClass().getResource("/com/github/JuanManuel/assets/img/acts/act_"+currentAct.getId()+".jpg").toExternalForm()));
    }

    public void save(ActionEvent actionEvent) {
        try {
            if (validate()) {
                if (habitoService.build().insert(currentHabito)) {
                    Alert.showAlert("INFORMATION", "Habito guardado", "El habito se ha guardado correctamente.");
                } else {
                    Alert.showAlert("ERROR", "Error al guardar", "No se ha podido guardar el habito.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validate() {
        boolean result = false;
        try {
            if (currentAct != null && frec_input.getValue() != null && type_cho.getValue() != null && date_input.getValue() != null) {
                if(frec_input.getValue() > 0) {
                    if (!date_input.getValue().isAfter(LocalDate.now())) {
                        currentHabito = new Habito(Session.getInstance().getCurrentUser(), currentAct);
                        currentHabito.setFrecuencia(frec_input.getValue());
                        currentHabito.setTipo(type_cho.getValue());
                        currentHabito.setUltimaFecha(date_input.getValue());
                        result = true;
                    } else {
                        Alert.showAlert("ERROR", "Fecha inválida", "La fecha debe ser posterior a la actual.");
                    }
                } else {
                    Alert.showAlert("ERROR", "Frecuencia inválida", "La frecuencia debe ser mayor a 0.");
                }
            } else {
                Alert.showAlert("ERROR", "Campos vacíos", "Por favor, rellene todos los campos.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
