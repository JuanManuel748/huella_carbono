package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import com.github.JuanManuel.model.entities.*;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.habitoService;
import com.github.JuanManuel.model.services.recomendacionService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for managing habits.
 * Provides methods for initializing the view, setting up tables, handling table selection, and performing CRUD operations on habits.
 */
public class HabitosController extends Controller implements Initializable {

    @FXML
    public DatePicker before_date;
    @FXML
    public DatePicker after_date;
    @FXML
    public Button filt_btn;
    @FXML
    public TableView<Habito> habitos_table;
    @FXML
    public TableColumn<Habito, String> act_col;
    @FXML
    public TableColumn<Habito, Integer> frec_col;
    @FXML
    public TableColumn<Habito, String> type_col;
    @FXML
    public TableColumn<Habito, LocalDate> date_col;
    @FXML
    public TableView<Recomendacion> recom_table;
    @FXML
    public TableColumn<Recomendacion, String> desc_col;
    @FXML
    public TableColumn<Recomendacion, Double> rec_imp_col;

    private Habito selected = new Habito();
    private List<Habito> habitos_ls = new ArrayList<>();
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

    /**
     * Sets up the tables with the data from the database.
     */
    private void setupTables() {
        habitos_ls = habitoService.build().findByUser(Session.getInstance().getCurrentUser());
        setupHabitos(habitos_ls);
        setupRecoms(habitos_ls);
    }

    /**
     * Sets up the habitos table with the data from the database.
     * @param ls the list of habits to display.
     */
    private void setupHabitos(List<Habito> ls) {
        habitos_table.setItems(FXCollections.observableArrayList(habitos_ls));
        act_col.setCellValueFactory(cellData -> {
            Habito h = cellData.getValue();
            Actividad act = h.getIdActividad();
            act = actividadService.build().findByPK(act);
            return new SimpleStringProperty(act.getNombre());
        });
        frec_col.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        type_col.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));

        habitos_table.setOnMouseClicked(event -> handleTableSelection(event));
    }

    /**
     * Sets up the recomendaciones table with the data from the database.
     * @param ls the list of habits to extract categories from.
     */
    private void setupRecoms(List<Habito> ls) {
        cat_ls = extractCats(ls);
        recom_ls = recomendacionService.build().findByCats(cat_ls);
        recom_table.setItems(FXCollections.observableArrayList(recom_ls));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        rec_imp_col.setCellValueFactory(new PropertyValueFactory<>("impactoEstimado"));
    }

    /**
     * Extracts the categories from a list of habitos.
     *
     * @param ls the list of habits to extract categories from.
     * @return a list of categories.
     */
    private List<Categoria> extractCats(List<Habito> ls) {
        List<Categoria> cats = new ArrayList<>();
        for (Habito h : ls) {
            Actividad act = h.getIdActividad();
            act = actividadService.build().findByPK(act);
            Categoria cat = act.getIdCategoria();
            cat = categoriaService.build().findByPK(cat);
            if (!cats.contains(cat)) {
                cats.add(cat);
            }
        }
        return cats;
    }

    /**
     * Handles the selection of a row in the table.
     *
     * @param event the mouse event that triggered the selection.
     */
    private void handleTableSelection(MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                selected = habitos_table.getSelectionModel().getSelectedItem();
                List<Habito> tempLs = new ArrayList<>();
                tempLs.add(selected);
                setupRecoms(tempLs);
                if (selected == null) {
                    Alert.showAlert("ERROR", "Ningún producto seleccionado", "Haz clic en un producto para seleccionarlo.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Goes to the edit page.
     *
     * @throws Exception if an error occurs while loading the edit page.
     */
    public void goToEdit() throws Exception {
        HomeController homeController = (HomeController) App.getController("home");
        homeController.loadPage("editHabito");
    }

    /**
     * Deletes the selected habit.
     *
     * @param actionEvent the event that triggered the action.
     */
    public void delete(ActionEvent actionEvent) {
        if (selected != null && selected.getId() != null) {
            if (Alert.showConfirmation("¿Estás seguro de que quieres eliminar esta huella?", "Esta acción no se puede deshacer.")) {
                if (habitoService.build().delete(selected)) {
                    habitos_ls.remove(selected);
                    habitos_table.setItems(FXCollections.observableArrayList(habitos_ls));
                }
            }
        } else {
            Alert.showAlert("ERROR", "Ninguna huella seleccionada", "Haz clic en una huella para seleccionarla.");
        }
    }

    /**
     * Filters the habitos by date.
     *
     * @param actionEvent the event that triggered the action.
     */
    public void filtHuellas(ActionEvent actionEvent) {
        LocalDate before = before_date.getValue();
        LocalDate after = after_date.getValue();
        habitos_ls = habitoService.build().findByDateRange(before, after, Session.getInstance().getCurrentUser());
        habitos_table.setItems(FXCollections.observableArrayList(habitos_ls));
    }
}