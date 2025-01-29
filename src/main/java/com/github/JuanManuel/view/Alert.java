package com.github.JuanManuel.view;

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
            case "CONFIRMATION":
                al.setAlertType(javafx.scene.control.Alert.AlertType.CONFIRMATION);
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
}
