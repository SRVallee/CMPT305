package PropAssess;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Group 4
 */
public class DataTableFXMLController implements Initializable {
    
    Searcher searcher = new Searcher();
    
    @FXML private TableView<Data> propAssessTable;
    @FXML private TableColumn<Data, String> account;
    @FXML private TableColumn<Data, String> address;
    @FXML private TableColumn<Data, String> value;
    @FXML private TableColumn<Data, String> assessClass;
    @FXML private TableColumn<Data, String> neighbourhood;
    @FXML private TableColumn<Data, String> latitude;
    @FXML private TableColumn<Data, String> longitude;
    
    //these fields are for the side panel
    @FXML private TextField accountField;
    @FXML private TextField addressField;
    @FXML private TextField neighbourField;
    @FXML private ChoiceBox<String> classDropdown;
    @FXML private Button searchButton;
    @FXML private Button resetButton;
    @FXML private TextArea statsArea;
    
    /**
     * Searches data from csv when clicked based on what's entered into fields 
     * accountField, addressField, neighbourField, and classDropdown
     * 
     * @param e event, when search button is clicked
     */
    @FXML void searchButtonHandler(ActionEvent e){
        List<Data> subset = searcher.getData(); //set data to be all data
        
        // Get text in account number field
        String accountStr = accountField.getText();
        if (!accountStr.isEmpty()){ //if something is in field
            subset = new ArrayList<>(); //dump total list
            //look for account
            Data singleAcc = searcher.getAccount(Integer.valueOf(accountStr));
            if (singleAcc != null){ //if account found
                subset.add(singleAcc); //add account to list
            }
            
        } else { //no account number
            // Get text in address field
            String addressStr = addressField.getText();
            if (!addressStr.isEmpty()){ //field is not empty
                //filter results by address
                subset = Searcher.getAddresses(addressStr, subset); 
            }
            
            // Get text in neighbourhood field
            String neighbourStr = neighbourField.getText();
            if (!neighbourStr.isEmpty()){ //field is not empty
                //filter results by neighbourhood
                subset = Searcher.getNeighbourhood(neighbourStr, subset);
            }
            
            //get test from classDropdown choice box
            String classStr = classDropdown.getValue();
            if (!classStr.equalsIgnoreCase("")){ //choice is not blank option
                //is residential option
                if (classStr.equalsIgnoreCase("Residential")){ 
                    //filter results by residential properties
                    subset = Searcher.getClassByResidential(true, subset);
                    
                } else { //if non-residential option
                    //filter results by non-residential properties
                    subset = Searcher.getClassByResidential(false, subset);
                }
            }
        }
        
        //display statistics
        statsArea.clear(); //clear stats text area
        if(subset.size() > 1){ //given that the data has more than 1 element
            //print stats
            statsArea.setText(Searcher.getStatsString(subset));
        } 
        fillTable(subset); //fill table with data
    }
    
    /**
     * When reset button is pushed, clear fields, recalculate default stats
     * and show all data in table
     * 
     * @param e event, clicking the button
     */
    @FXML void resetButtonHandler(ActionEvent e){
        //clear all fields
        accountField.clear();
        addressField.clear();
        neighbourField.clear();
        classDropdown.getSelectionModel().selectFirst();
        
        defaultDisplay(); //show all data and stats for all data
    }
    
    /**
     * Sets stats area to display stats of all data
     * Fills table will all data
     * 
     */
    public void defaultDisplay(){
        List<Data> allData = searcher.getData(); //grab all data
        //display stats of all data
        statsArea.setText(Searcher.getStatsString(allData));
        //fill table with all data
        fillTable(searcher.getData());
    }
    
    /**
     * Fill the Property Assessments table with all data from a Searcher object
     * 
     * @param subset 
     */
    public void fillTable(List<Data> subset){
        // take Data subset and cast to something JavaFX can interpret
        ObservableList<Data> data = FXCollections.observableArrayList(subset);

        // Get info by referencing methods in Data (_Property) to populate
        account.setCellValueFactory(
                new PropertyValueFactory("account"));
        
        address.setCellValueFactory(
                new PropertyValueFactory("address"));
        
        value.setCellValueFactory(
                new PropertyValueFactory("value"));
        
        assessClass.setCellValueFactory(
                new PropertyValueFactory("propertyClass"));
        
        neighbourhood.setCellValueFactory(
                new PropertyValueFactory("neighbourhood"));
        
        latitude.setCellValueFactory(
                new PropertyValueFactory("latitude"));
        
        longitude.setCellValueFactory(
                new PropertyValueFactory("longitude"));
        
        //Populate table
        propAssessTable.setItems(data);
        
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //populate searcher object
        try{
        searcher.readCSV("Property_Assessment_Data.csv");
        } catch (FileNotFoundException e){
            System.out.println("File not found, exiting.");
            return;
        }
        
        //set dropdown menu options
        classDropdown.getItems().clear();
        classDropdown.getItems().addAll("", "Residential", "Non-Residential");
        classDropdown.getSelectionModel().selectFirst();
        
        defaultDisplay(); //show all data and stats for all data
    }    
    
}
