package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.*;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.huellaService;
import com.github.JuanManuel.model.services.recomendacionService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HuellasController extends Controller implements Initializable {
    @FXML
    public ComboBox frec_cho;
    @FXML
    public Text impact_txt;
    @FXML
    public TableView<Huella> huella_table;
    @FXML
    public TableColumn<Huella, String> act_col;
    @FXML
    public TableColumn<Huella, Double> valor_col;
    @FXML
    public TableColumn<Huella, String> unidad_col;
    @FXML
    public TableColumn<Huella, LocalDate> date_col;
    @FXML
    public TableColumn<Huella, Double> impact_col;
    @FXML
    public TableView<Recomendacion> recom_table;
    @FXML
    public TableColumn<Recomendacion, String> desc_col;
    @FXML
    public TableColumn<Recomendacion, Double> rec_imp_col;
    @FXML
    public DatePicker before_date;
    @FXML
    public DatePicker after_date;

    public static Huella selected = new Huella();
    private List<Huella> huellas_ls = new ArrayList<>();
    private List<Recomendacion> recom_ls = new ArrayList<>();
    private List<Categoria> cat_ls = new ArrayList<>();

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
        setupTables();
        before_date.setValue(LocalDate.now().minusYears(1));
        after_date.setValue(LocalDate.now());
    }

    private void setupTables() {
        // Iniciar la tabla de huellas
        huellas_ls = huellaService.build().findByUser(Session.getInstance().getCurrentUser());
        setUpHuellas(huellas_ls);
        setUpRecoms(huellas_ls);
    }

    private void setUpHuellas(List<Huella> huellasLs) {
        huella_table.setItems(FXCollections.observableArrayList(huellas_ls));
        act_col.setCellValueFactory(cellData -> {
            Huella huella = (Huella) cellData.getValue();
            Actividad act = huella.getIdActividad();
            act = actividadService.build().findByPK(act);
            return new SimpleStringProperty(act.getNombre());
        });
        valor_col.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unidad_col.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        impact_col.setCellValueFactory(cellData -> {
            Huella huella = (Huella) cellData.getValue();
            return new SimpleDoubleProperty(calculateImpact(huella)).asObject();
        });
        huella_table.setOnMouseClicked(event -> handleTableSelection(event));
    }

    private void setUpRecoms(List<Huella> ls) {
        cat_ls = extractCats(ls);
        recom_ls = recomendacionService.build().findByCats(cat_ls);
        // iniciar la tabla de recomendaciones
        recom_table.setItems(FXCollections.observableArrayList(recom_ls));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        rec_imp_col.setCellValueFactory(new PropertyValueFactory<>("impactoEstimado"));
    }

    private List<Categoria> extractCats(List<Huella> huellasLs) {
        List<Categoria> result = new ArrayList<>();
        for (Huella huella : huellasLs) {
            Actividad act = huella.getIdActividad();
            act = actividadService.build().findByPK(act);
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            if (!result.contains(cat)) {
                result.add(cat);
            }
        }
        return result;
    }

    private double calculateImpact(Huella huella) {
        Double result = 0.0;
        try {
            Actividad tempAct = huella.getIdActividad();
            tempAct = actividadService.build().findByPK(tempAct);
            Categoria tempCat = tempAct.getIdCategoria();
            tempCat = categoriaService.build().findByPK(tempCat);
            Double fact = tempCat.getFactorEmision().doubleValue();
            result = huella.getValor().doubleValue() * fact;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private void handleTableSelection(MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                selected = huella_table.getSelectionModel().getSelectedItem();
                List<Huella> tempLs = new ArrayList<>();
                tempLs.add(selected);
                setUpRecoms(tempLs);
                if (selected != null) {
                } else {
                    Alert.showAlert("ERROR", "Ninguna huella seleccionada", "Haz clic en una huella para seleccionarla.");
                }
            }
            if (event.getClickCount() == 2) {
                selected = huella_table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    goToEdit();
                } else {
                    Alert.showAlert("ERROR", "Ninguna huella seleccionada", "Haz clic en una huella para seleccionarla.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void goToEdit() throws Exception {
        HomeController homeController = (HomeController) App.getController("home");
        homeController.loadPage("editHuella");
    }


    public void filtHuellas(ActionEvent actionEvent) {
        LocalDate before = before_date.getValue();
        LocalDate after = after_date.getValue();
        Usuario user = Session.getInstance().getCurrentUser();
        huellas_ls = huellaService.build().findByDateRange(before, after, user);
        huella_table.setItems(FXCollections.observableArrayList(huellas_ls));
    }

    public void delete(ActionEvent actionEvent) {
        selected = huella_table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (Alert.showConfirmation("¿Estás seguro de que quieres eliminar esta huella?", "Esta acción no se puede deshacer.")) {
                if (huellaService.build().delete(selected)) {
                    huellas_ls.remove(selected);
                    huella_table.setItems(FXCollections.observableArrayList(huellas_ls));
                }
            }
        }  else {
            Alert.showAlert("ERROR", "Ninguna huella seleccionado", "Haz clic en una huella para seleccionarla.");
        }

    }
}
