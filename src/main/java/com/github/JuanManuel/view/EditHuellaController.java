package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.Actividad;
import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Huella;
import com.github.JuanManuel.model.entities.Session;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.huellaService;
import com.github.JuanManuel.view.HuellasController;
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

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.github.JuanManuel.view.HuellasController.selected;
import static javafx.collections.FXCollections.observableArrayList;

public class EditHuellaController extends Controller implements Initializable {

    @FXML
    public ComboBox<Actividad> act_cho;
    @FXML
    public Spinner<Double> valor_input;
    @FXML
    public Text unidad_txt;
    @FXML
    public DatePicker date_input;
    @FXML
    public ImageView preview_img;

    List<Actividad> act_ls = new ArrayList<>();
    Huella currentHuella = new Huella();
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
        currentAct = act_ls.get(0);
        act_cho.setItems(observableArrayList(act_ls));
        act_cho.setValue(currentAct);
        act_cho.setOnAction(e -> {setAct();});
        valor_input.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE, 0.00, 0.10));
        if (selected.getId() != null) {
            setHuella(selected);
        }
        setAct();
    }

    private void setHuella(Huella selected) {
        currentHuella = selected;
        currentAct = currentHuella.getIdActividad();
        currentAct = actividadService.build().findByPK(currentAct);
        act_cho.getSelectionModel().select(currentAct);
        setAct();
        valor_input.getValueFactory().setValue(currentHuella.getValor().doubleValue());
        date_input.setValue(currentHuella.getFecha());
    }

    private void setAct() {
        currentAct = act_cho.getSelectionModel().getSelectedItem();
        Categoria tempCat = currentAct.getIdCategoria();
        tempCat = categoriaService.build().findByPK(tempCat);
        unidad_txt.setText(tempCat.getUnidad());
        preview_img.setImage(new Image(getClass().getResource("/com/github/JuanManuel/assets/img/acts/act_"+currentAct.getId()+".jpg").toExternalForm()));
    }

    public void save(ActionEvent actionEvent) {
        try {
            currentHuella.setIdActividad(currentAct);
            currentHuella.setValor(BigDecimal.valueOf(valor_input.getValue()));
            currentHuella.setUnidad(unidad_txt.getText());
            currentHuella.setFecha(date_input.getValue());
            currentHuella.setIdUsuario(Session.getInstance().getCurrentUser());
            if (huellaService.build().insert(currentHuella)) {
                Alert.showAlert("INFORMATION", "Huella guardada", "La huella se ha guardado correctamente.");
            } else if (huellaService.build().update(currentHuella)) {
                Alert.showAlert("INFORMATION", "Huella actualizada", "La huella se ha actualizado correctamente.");
            } else {
                Alert.showAlert("ERROR", "Huella no guardada", "La huella no se ha podido guardar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
