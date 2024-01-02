package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class of Login Form Page
 *
 * @author Shivam Patel and Neel Patel
 */
public class LoginFormController implements Initializable {

    /**
     * Textfield for getting username from user.
     */
    @FXML
    private TextField tfUsername;
    /**
     * Button for login.
     */
    @FXML
    private Button btnLogin;

    /**
     * Button for ending program.
     */
    @FXML
    private Button btnExit;

    /**
     * Method for initializing the Login Form.
     *
     * @param url url.
     * @param rb resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfUsername.requestFocus();
    }

    /**
     * Method for action when user click on login button.
     *
     * @param event event of button.
     * @throws IOException handling exceptions.
     */
    @FXML
    private void actionOnLogin(ActionEvent event) throws IOException {
        if (tfUsername.getText().isEmpty()) {
            UtilsController.showError("Login", "Please enter username!");
        } else {
            boolean userFound = false;
            if (tfUsername.getText().equalsIgnoreCase("admin")) {
                userFound = true;
                Parent root = FXMLLoader.load(getClass().getResource("/view/AdminPage.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                DataController userData = DataController.getInstance();
                if (userData.containsUser(tfUsername.getText().trim())) {
                    try {
                        userData.setCurrentSessionUser(tfUsername.getText().trim());
                        userFound = true;
                        Parent root = FXMLLoader.load(getClass().getResource("/view/AlbumMainPage.fxml"));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!userFound) {
                UtilsController.showError("Login", "Invalid username!");
            }
        }
    }

    /**
     * Method for action when user click on exit.
     *
     * @param event event of button.
     */
    @FXML
    private void actionOnExit(ActionEvent event) {
        System.exit(0);
    }

}
