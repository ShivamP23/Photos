
package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.DataController;
/**
 * Main class for running the application.
 * 
 * @author Shivam Patel and Neel Patel
 */
public class Photos extends Application {
    /**
     * Start method of the application.
     * @param stage the stage will show gui.
     * @throws Exception error if gui not found.
     */
    @Override
    public void start(Stage stage) throws Exception {
        DataController.readFromAFile();
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
     

    //got it one more thing
    
    
    /**
     * Main method of the class.
     * @param args  passing arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
