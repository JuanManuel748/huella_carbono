package com.github.JuanManuel.view;

import com.github.JuanManuel.model.entities.*;
import com.github.JuanManuel.model.services.actividadService;
import com.github.JuanManuel.model.services.categoriaService;
import com.github.JuanManuel.model.services.huellaService;
import com.github.JuanManuel.model.services.usuarioService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class SocialController extends Controller implements Initializable {
    @FXML
    public ComboBox<String> filter_cho;
    @FXML
    public TextField newUser_input;
    @FXML
    public ImageView search_btn;
    @FXML
    public Text currentUser_impact;
    @FXML
    public BarChart<String, Number> currentUser_barChart;
    @FXML
    public Text newUser_impact;
    @FXML
    public BarChart<String, Number> newUser_barChart;
    @FXML
    public ComboBox<String> grachic_cho;
    @FXML
    public LineChart<String, Number> currentUser_line;
    @FXML
    public LineChart<String, Number> newUser_line;

    private Usuario newUser = null;
    private Usuario currentUser = null;

    private String email_regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

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
        setCurrentUsr();
        setGraphs();
    }

    private void updateImpact(Text vText, Usuario vUser) {
        double impact = 0.0;
        for (Huella huella : vUser.getHuellas()) {
            impact += calculateImpact(huella);
        }
        impact = Math.round(impact * 1000.0) / 1000.0;
        vText.setText(String.valueOf(impact) + " (kg CO₂)");
    }
    private double calculateImpact(Huella huella) {
        double result = 0.0;
        try {
            Actividad tempAct = huella.getIdActividad();
            tempAct = actividadService.build().findByPK(tempAct);
            Categoria tempCat = tempAct.getIdCategoria();
            tempCat = categoriaService.build().findByPK(tempCat);
            double fact = tempCat.getFactorEmision().doubleValue();
            result = huella.getValor().doubleValue() * fact;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    private void updateBars(List<Object[]> vLs, BarChart<String, Number> vChart) {
        vChart.getData().clear();
        vChart.setTitle("Nº de huellas\npor categoría");
        for (Object[] o : vLs) {
            String cat = (String) o[0];
            Long count = (Long) o[1];
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(cat);
            series.getData().add(new XYChart.Data<>("", count));
            vChart.getData().add(series);
        }
    }
    private void setCatsFilter() {
        List<Categoria> categorias = categoriaService.build().findAll();
        Categoria all = new Categoria();
        all.setNombre("Todas");
        all.setId(0);
        categorias.add(0, all);
        ObservableList<String> cats = observableArrayList();
        for (Categoria cat : categorias) {
            cats.add(cat.getNombre());
        }
        filter_cho.setItems(cats);
        filter_cho.getSelectionModel().selectFirst();
        filter_cho.setOnAction(this::filtByCat);
    }
    private void setDateFilter() {
        filter_cho.setOnAction(this::filtByDate);
        ObservableList<String> dates = observableArrayList("Diario", "Semanal", "Mensual", "Anual");
        filter_cho.setItems(dates);
        filter_cho.setValue("Mensual");

    }
    private void updateLine(List<Object[]> vLs, LineChart<String, Number> vChart) {
        vChart.getData().clear();
        vChart.setLegendVisible(false);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Object[] o : vLs) {
            String date = "";
            Number count = 0;
            if (o.length > 2) {
                date = o[0].toString() + " " + o[1].toString();
                count = (Number) o[2];
            } else {
                date = o[0].toString();
                count = (Number) o[1];
            }

            series.getData().add(new XYChart.Data<>(date, count));
        }
        vChart.getData().add(series);
    }
    private void alternateGraphics(boolean activated) {
        currentUser_line.getData().clear();
        newUser_line.getData().clear();
        currentUser_line.setVisible(activated);
        newUser_line.setVisible(activated);
        currentUser_line.setManaged(activated);
        newUser_line.setManaged(activated);

        currentUser_barChart.getData().clear();
        newUser_barChart.getData().clear();
        currentUser_barChart.setVisible(!activated);
        newUser_barChart.setVisible(!activated);
        currentUser_barChart.setManaged(!activated);
        newUser_barChart.setManaged(!activated);
    }
    @FXML
    public void search(MouseEvent mouseEvent) {

        if (validateSearch()) {
            Usuario tempUser = new Usuario();
            tempUser.setEmail(newUser_input.getText());
            tempUser = usuarioService.build().findByPK(tempUser);
            if (tempUser != null) {
                setNewUser(tempUser);
            } else {
                Alert.showAlert("ERROR", "Usuario no encontrado", "No se ha encontrado un usuario con el correo electrónico proporcionado");
            }
        } else {
            Alert.showAlert("ERROR", "Correo electrónico inválido", "El correo electrónico proporcionado no es válido");
        }
    }
    private boolean validateSearch() {
        boolean result = false;
        try {
            String search = newUser_input.getText();
            if (search.matches(email_regex)) {
                result = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void setNewUser(Usuario vnewUser) {
        newUser = vnewUser;
        newUser.setHuellas(huellaService.build().findByUser(newUser));
        updateImpact(newUser_impact, newUser);

        if (currentUser_line.isVisible()) {
            List<Object[]> countResults = huellaService.build().statsByMonth(newUser);
            updateLine(countResults, newUser_line);
        } else {
            List<Object[]> countResults = huellaService.build().countByCategorias(newUser);
            updateBars(countResults, newUser_barChart);
        }
    }
    private void setCurrentUsr() {
        currentUser = Session.getInstance().getCurrentUser();
        currentUser.setHuellas(huellaService.build().findByUser(currentUser));
        updateImpact(currentUser_impact, currentUser);
    }

    private void setGraphs() {
        grachic_cho.setItems(observableArrayList("Categoria", "Historial"));
        grachic_cho.setOnAction(this::updateGraph);
        grachic_cho.getSelectionModel().selectFirst();
        updateGraph(null);
    }
    private void updateGraph(Event event) {
        String selected = grachic_cho.getSelectionModel().getSelectedItem().toString();
        if (selected.equalsIgnoreCase("Historial")) {
            setHistorial();
        } else if (selected.equalsIgnoreCase("Categoria")) {
            setCategories();
        }
    }

    private void setCategories() {
        alternateGraphics(false);
        setCatsFilter();
        updateImpact(currentUser_impact, currentUser);
        List<Object[]> currentCount = huellaService.build().countByCategorias(currentUser);
        updateBars(currentCount, currentUser_barChart);
        if (newUser != null) {
            updateImpact(newUser_impact, newUser);
            List<Object[]> newCount = huellaService.build().countByCategorias(newUser);
            updateBars(newCount, newUser_barChart);
        }
    }

    private void setHistorial() {
        alternateGraphics(true);
        setDateFilter();
        updateImpact(currentUser_impact, currentUser);
        List<Object[]> currentCount = huellaService.build().statsByMonth(currentUser);
        updateLine(currentCount, currentUser_line);
        if (newUser != null) {
            updateImpact(newUser_impact, newUser);
            List<Object[]> newCount = huellaService.build().statsByMonth(newUser);
            updateLine(newCount, newUser_line);
        }
    }

    private void filtByCat(ActionEvent actionEvent) {
        try {
            String catName = filter_cho.getSelectionModel().getSelectedItem();
            if (catName.equalsIgnoreCase("Todas")) {
                setCategories();
                currentUser.setHuellas(huellaService.build().findByUser(currentUser));
                updateImpact(currentUser_impact, currentUser);
                if (newUser != null) {
                    newUser.setHuellas(huellaService.build().findByUser(newUser));
                    updateImpact(newUser_impact, newUser);
                }
            } else {
                Categoria cat = new Categoria();
                cat.setNombre(catName);
                cat = categoriaService.build().findByPK(cat);
                List<Object[]> currentCount = huellaService.build().countByCat(currentUser, cat);
                updateBars(currentCount, currentUser_barChart);
                currentUser.setHuellas(huellaService.build().findByUserFiltByCat(currentUser, cat));
                updateImpact(currentUser_impact, currentUser);
                if (newUser != null) {
                    List<Object[]> newCount = huellaService.build().countByCat(newUser, cat);
                    updateBars(newCount, newUser_barChart);
                    newUser.setHuellas(huellaService.build().findByUserFiltByCat(newUser, cat));
                    updateImpact(newUser_impact, newUser);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void filtByDate(ActionEvent actionEvent) {
        String selected = filter_cho.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }

        List<Object[]> currentCount = new ArrayList<>();
        List<Object[]> newCount = new ArrayList<>();
        switch (selected) {
            case "Diario":
                currentCount = huellaService.build().statsByDay(currentUser);
                if (newUser != null) {
                    newCount = huellaService.build().statsByDay(newUser);
                }
                break;
            case "Semanal":
                currentCount = huellaService.build().statsByWeek(currentUser);
                if (newUser != null) {
                    newCount = huellaService.build().statsByWeek(newUser);
                }
                break;
            case "Mensual":
                currentCount = huellaService.build().statsByMonth(currentUser);
                if (newUser != null) {
                    newCount = huellaService.build().statsByMonth(newUser);
                }
                break;
            case "Anual":
                currentCount = huellaService.build().statsByYear(currentUser);
                if (newUser != null) {
                    newCount = huellaService.build().statsByYear(newUser);
                }
                break;
            default:
                break;
        }
        updateLine(currentCount, currentUser_line);
        updateLine(newCount, newUser_line);
        updateAverage(currentUser_impact, currentCount);
        updateAverage(newUser_impact, newCount);

    }

    private void updateAverage(Text vText, List<Object[]> vLs) {
        double impact = 0.0;
        for (Object[] o : vLs) {
            impact += Double.parseDouble(o[1].toString());
        }
        impact = impact / vLs.size();
        impact = Math.round(impact * 1000.0) / 1000.0;
        vText.setText(String.valueOf(impact) + " (kg CO₂)");
    }

}