package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

/**
 * Controller class for View Slide Show.
 *
 * @author Shivam Patel and Neel Patel
 */
public class ViewSlideShowController implements Initializable {

    @FXML
    private Button btnPrev;
    @FXML
    private Button btnExitSlide;
    @FXML
    private Button btnNext;

    private int pointer;
    private ArrayList<Photo> photoList;
    @FXML
    private ImageView img;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void start(int count) {
        pointer = count;
        Album a = User.getCurrentSessionAlbum();
        ArrayList<Photo> photos = a.getPhotoList();
        photoList = photos;
        img.setImage(new Image("file:" + photoList.get(pointer).getPath()));
        img.setPreserveRatio(true);
    }

    /**
     * Method for action when user click on prev button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnPrev(ActionEvent event) {
        if (photoList.size() > 1) {
            pointer--;
            if (pointer == -1) {
                pointer = photoList.size() - 1;
            }
            img.setImage(new Image("file:" + photoList.get(pointer).getPath()));
            img.setPreserveRatio(true);
        } else {
            UtilsController.showError("Slide Show", "This album has only 1 photo");
        }
    }

    /**
     * Method for action when user click on exit slide button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnExitSlide(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAlbum.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for action when user click on next button.
     *
     * @param event action event.
     */
    @FXML
    private void actionOnNext(ActionEvent event) {
        if (photoList.size() > 1) {
            pointer++;
            if (pointer == photoList.size()) {
                pointer = 0;
            }
            img.setImage(new Image("file:" + photoList.get(pointer).getPath()));
            img.setPreserveRatio(true);
        } else {
            UtilsController.showError("Slide Show", "This album has only 1 photo");
        }
    }

}
