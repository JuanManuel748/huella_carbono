package com.github.JuanManuel.view;

import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

/**
 * The alert class contains methods for displaying alert dialogs.
 */
public class Alert {

    /**
     * Displays an alert dialog with the specified type, title, and content.
     *
     * @param type    The type of the alert (e.g., "ERROR", "INFORMATION", "CONFIRMATION", "WARNING").
     * @param title   The title of the alert dialog.
     * @param content The message to be displayed in the alert dialog.
     */
    public static void showAlert(String type, String title, String content) {
        javafx.scene.control.Alert al = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.NONE);

        switch (type) {
            case "ERROR":
                al.setAlertType(javafx.scene.control.Alert.AlertType.ERROR);
                break;
            case "INFORMATION":
                al.setAlertType(javafx.scene.control.Alert.AlertType.INFORMATION);
                break;
            case "WARNING":
                al.setAlertType(javafx.scene.control.Alert.AlertType.WARNING);
                break;
            default:
                break;
        }

        al.setTitle(title);
        al.setHeaderText(null);
        al.setContentText(content);

        al.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the specified title and content.
     *
     * @param title   The title of the confirmation dialog.
     * @param content The message to be displayed in the confirmation dialog.
     * @return true if the user confirms, false otherwise.
     */
    public static boolean showConfirmation(String title, String content) {
        boolean result = false;
        javafx.scene.control.Alert al = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        al.setTitle(title);
        al.setHeaderText(null);
        al.setContentText(content);
        result = al.showAndWait().get().getButtonData().isDefaultButton();
        return result;
    }

    /**
     * Displays a choice dialog with the specified title and options.
     *
     * @param title   The title of the choice dialog.
     * @param options The options to be displayed in the choice dialog.
     * @return the selected option as a string, or "cancel" if no option is selected.
     */
    public static String showChoice(String title, String[] options) {
        String resultST = "cancel";
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Elige una opción");
        alert.setHeaderText(title);
        alert.getButtonTypes().clear();
        for (String option : options) {
            alert.getButtonTypes().add(new ButtonType(option));
        }
        alert.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        resultST = result.map(ButtonType::getText).orElse("cancel");
        return resultST;
    }
}