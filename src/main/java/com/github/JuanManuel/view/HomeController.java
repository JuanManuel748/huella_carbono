package com.github.JuanManuel.view;

import com.github.JuanManuel.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class HomeController  extends Controller implements Initializable {

    @FXML
    public AnchorPane page_pane;
    @FXML
    protected ComboBox<String> page_cho;
    @FXML
    private ImageView profile_btn;

    /**
     * Called when the view is opened. This method is intended for any setup or initialization
     * operations when the controller is first initialized. In this case, it's empty.
     *
     * @param input the input object passed when opening the view.
     */
    @Override
    public void onOpen(Object input) throws Exception {
        App.setController("home", this);

        Stage stage = (Stage) App.getPrimaryStage();

        if (stage != null) {
            stage.setMinWidth(1015);
            stage.setMinHeight(800);
            stage.centerOnScreen();
        } else {
            Alert.showAlert("ERROR", "", "No se ha podido cargar la ventana principal.");
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
     * @param url the location used to load the FXML file.
     * @param resourceBundle the resources used for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            onOpen(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        page_cho.setItems(observableArrayList("Huellas", "Hábitos", "Social"));
        page_cho.setOnAction(this::updatePage);
        page_cho.setValue("Huellas");
        updatePage(null);



    }

    private void updatePage(javafx.event.Event event) {
        String page = page_cho.getValue().toString().toLowerCase();
        if (page.equalsIgnoreCase("hábitos")) {
            loadPage("habitos");
            page_cho.setValue("Hábitos");
        } else if (page.equalsIgnoreCase("huellas")) {
            loadPage("huellas");
            page_cho.setValue("Huellas");
        } else if (page.equalsIgnoreCase("Social")) {
            loadPage("social");
            page_cho.setValue("Social");
        }
    }


    protected void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
            if (!page.equals("huellas") && !page.equals("habitos") && !page.equals("social")) {
                page_cho.setValue("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        page_pane.getChildren().setAll(root);
    }

    public void goToProfile(javafx.scene.input.MouseEvent mouseEvent) {
        loadPage("perfil");
    }
}
