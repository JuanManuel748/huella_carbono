package com.github.JuanManuel.view;

import com.github.JuanManuel.model.entities.Categoria;
import com.github.JuanManuel.model.entities.Huella;
import com.github.JuanManuel.model.entities.Recomendacion;
import com.github.JuanManuel.model.entities.Session;
import com.github.JuanManuel.model.services.huellaService;
import com.github.JuanManuel.model.services.recomendacionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HuellasController extends Controller implements Initializable {
    @FXML
    public ComboBox frec_cho;
    @FXML
    public Text impact_txt;
    @FXML
    public TableView huella_table;
    @FXML
    public TableColumn act_col;
    @FXML
    public TableColumn valor;
    @FXML
    public TableColumn unidad_col;
    @FXML
    public TableColumn date_col;
    @FXML
    public TableColumn impact_col;
    @FXML
    public TableView recom_table;
    @FXML
    public TableColumn desc_col;
    @FXML
    public TableColumn rec_imp_col;


    List<Huella> huellas_ls = new ArrayList<>();
    List<Recomendacion> recom_ls = new ArrayList<>();
    List<Categoria> cat_ls = new ArrayList<>();

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
        // Iniciar la tabla de huellas
        huellas_ls = huellaService.build().findByUser(Session.getInstance().getCurrentUser());
        huella_table.setItems(FXCollections.observableArrayList(huellas_ls));
        act_col.setCellValueFactory(new PropertyValueFactory<>("id_actividad"));
        valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unidad_col.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        // Buscar las categorías
        cat_ls = extractCats(huellas_ls);
        recom_ls = recomendacionService.build().findByCats(cat_ls);
        // iniciar la tabla de recomendaciones
        huella_table.setItems(FXCollections.observableArrayList(recom_ls));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        rec_imp_col.setCellValueFactory(new PropertyValueFactory<>("impacto_estimado"));

    }

    private List<Categoria> extractCats(List<Huella> huellasLs) {
        List<Categoria> result = new ArrayList<>();
        for (Huella huella : huellasLs) {
            Categoria cat = huella.getIdActividad().getIdCategoria();
            if (!result.contains(cat)) {
                result.add(cat);
            }
        }
        return result;
    }

    private void handleTableSelection(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Huella selected = new Huella();
            if (selected != null) {
                setHuella(selected);
            } else {
                Alert.showAlert("ERROR", "Ningún producto seleccionado", "Haz clic en un producto para seleccionarlo.");
            }
        }
    }

    private void setHuella(Huella selected) {

    }
}
