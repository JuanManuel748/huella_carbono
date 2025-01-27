package com.github.JuanManuel.view;

import javafx.scene.control.Alert;
/**
 * The alert class contains methods for displaying alert dialogs.
 */
public class alert {

    /**
     * Displays an alert dialog with the specified type, title, and content.
     *
     * @param type    The type of the alert (e.g., "ERROR", "INFORMATION", "CONFIRMATION", "WARNING").
     * @param title   The title of the alert dialog.
     * @param content The message to be displayed in the alert dialog.
     */
    public static void showAlert(String type, String title, String content) {
        Alert al = new Alert(Alert.AlertType.NONE);

        switch (type) {
            case "ERROR":
                al.setAlertType(Alert.AlertType.ERROR);
                break;
            case "INFORMATION":
                al.setAlertType(Alert.AlertType.INFORMATION);
                break;
            case "CONFIRMATION":
                al.setAlertType(Alert.AlertType.CONFIRMATION);
                break;
            case "WARNING":
                al.setAlertType(Alert.AlertType.WARNING);
                break;
            default:
                break;
        }

        al.setTitle(title);
        al.setHeaderText(null);
        al.setContentText(content);

        al.showAndWait();
    }
}
