package PropAssess;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author Group 4
 */
public class Main extends Application {

    /**
     * Displays a table with the data from Property_Assessment_Data.csv
     * 
     * @param stage 
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(
                "PropAssessProgramFXML.fxml"));
        Scene scene = new Scene(root);
        // window title
        stage.setTitle("Edmonton Property Assessments");
                
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main. Starts JavaFX application
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
