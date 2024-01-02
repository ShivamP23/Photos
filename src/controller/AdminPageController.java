package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

/**
 * Controller class for Admin Page View. Handle all operations of Admin Page.
 *
 * @author Shivam Patel and Neel Patel
 */
public class AdminPageController implements Initializable {

    /**
     * List view for showing all users.
     */
    @FXML
    private ListView<String> listUsers;
    /**
     * TextField for getting username from user.
     */
    @FXML
    private TextField tfUsername;
    /**
     * Button for creating new user.
     */
    @FXML
    private Button btnCreate;
    /**
     * Button for deleting an existing user.
     */
    @FXML
    private Button btnDelete;
    /**
     * Button for logout from system.
     */
    @FXML
    private Button btnLogout;
    /**
     * List for storing the users.
     */
    private final ObservableList<String> users = FXCollections.observableArrayList();

    /**
     * Method for initializing the Admin Page.
     *
     * @param url url.
     * @param rb resources.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listUsers.setItems(users);
        users.addAll(DataController.getInstance().getUsernames());
        if (users.isEmpty()) {
            btnDelete.setDisable(true);
        }

    }

    /**
     * Method for action event when user click on Create button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnCreate(ActionEvent event) {
        String user = tfUsername.getText();
        if (user.isEmpty()) {
            UtilsController.showError("Create New User", "Please Enter Username!");
        } else if (DataController.getInstance().containsUser(user)) {
            UtilsController.showError("Create New User", "User is already exist!");
        } else {
            users.add(user);
            listUsers.getSelectionModel().select(user);
            DataController.getInstance().addUser(new User(user));
            btnDelete.setDisable(false);
            tfUsername.clear();
            try {
                DataController userData = DataController.getInstance();
                userData.writeToAFile();
            } catch (Exception ex) {

            }
        }
    }

    /**
     * Method for action event when user click on Delete button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnDelete(ActionEvent event) {
        String user = listUsers.getSelectionModel().getSelectedItem();
        if (user == null) {
            UtilsController.showError("Delete User", "Select a user for deleting");
        } else {
            if (UtilsController.getConfirmation("Delete User", "Are you sure you want to delete \"" + user + "\" User?")) {
                users.remove(user);
                DataController.getInstance().deleteUser(user);
                if (users.isEmpty()) {
                    btnDelete.setDisable(true);
                }
                try {
                    DataController userData = DataController.getInstance();
                    userData.writeToAFile();
                } catch (Exception ex) {

                }
            }
        }
    }

    /**
     * Method for action event when user click on logout button.
     *
     * @param event action event.
     * @throws IOException handling exceptions.
     */
    @FXML
    private void actionOnLogout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
