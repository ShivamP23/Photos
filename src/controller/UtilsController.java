package controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Controller class for handling errors and confirmations messages.
 *
 * @author Shivam Patel and Neel Patel
 */
public class UtilsController {

    /**
     * Method for showing error dialog.
     *
     * @param title title of error.
     * @param msg error message.
     */
    public static void showError(String title, String msg) {
        Alert error = new Alert(AlertType.ERROR);
        error.setTitle(title);
        error.setContentText(msg);
        error.showAndWait();
    }

    /**
     * Method for showing information dialog.
     *
     * @param title title of information.
     * @param msg information message.
     */
    public static void showInfo(String title, String msg) {
        Alert info = new Alert(AlertType.INFORMATION);
        info.setTitle(title);
        info.setContentText(msg);
        info.showAndWait();
    }

    /**
     * Method for getting confirmation from user.
     *
     * @param title title of confirmation.
     * @param msg message of confirmation.
     * @return true or false.
     */
    public static boolean getConfirmation(String title, String msg) {
        Alert confirm = new Alert(Alert.AlertType.WARNING);
        confirm.setTitle(title);
        confirm.setContentText(msg);
        confirm.setHeaderText(null);
        confirm.setResizable(false);
        confirm.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = confirm.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
