package PropAssess;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Class for the data table tab,
 * Can be used as an example of how to use the TabTemplate template
 * 
 * @author Sera Vallee <3045024>
 */
public class DataTableTab extends TabTemplate {
    //table and columns
    private VBox tableVBox;
    private TableView<Data>[] tables = new TableView[2];
    private TableColumn<Data, String>[] accountCols = new TableColumn[2];
    private TableColumn<Data, String>[] addressCols = new TableColumn[2];
    private TableColumn<Data, String>[] valueCols = new TableColumn[2];
    private TableColumn<Data, String>[] assessClassCols = new TableColumn[2];
    private TableColumn<Data, String>[] neighbourhoodCols = new TableColumn[2];
    private TableColumn<Data, String>[] latitudeCols = new TableColumn[2];
    private TableColumn<Data, String>[] longitudeCols = new TableColumn[2];
    
    /**
     * Constructor for DataTableTab
     * initializes empty columns and adds them to table
     * 
     * @param vbox
     */
    public DataTableTab(VBox vbox){
        //init columns sizes
        this.tableVBox = vbox;
        VBox.setVgrow(tableVBox, Priority.ALWAYS); //allow elements to grow vert
        for (int i = 0; i < 2; i++){
            //inite tables
            tables[i] = new TableView<>();
            tables[i].setPrefHeight(1200);
            
            //init columns
            accountCols[i] = new TableColumn("Account");
            accountCols[i].setMinWidth(70);
            
            addressCols[i] = new TableColumn("Address");
            addressCols[i].setMinWidth(170);
            
            valueCols[i] = new TableColumn("Assessed Value");
            valueCols[i].setMinWidth(60);
            
            assessClassCols[i] = new TableColumn("Assessment Class");
            assessClassCols[i].setMinWidth(110);
            
            neighbourhoodCols[i] = new TableColumn("Neighbourhood");
            neighbourhoodCols[i].setMinWidth(150);
            
            latitudeCols[i] = new TableColumn("Latitude");
            latitudeCols[i].setMinWidth(120);
            
            longitudeCols[i] = new TableColumn("Longitude");
            longitudeCols[i].setMinWidth(130);

            //add columns to tables
            tables[i].getColumns().addAll(
                    accountCols[i],
                    addressCols[i],
                    valueCols[i],
                    assessClassCols[i],
                    neighbourhoodCols[i],
                    latitudeCols[i],
                    longitudeCols[i]
            );
        }
        
    }
    /**
     * Fill the Property Assessments table with from a dataset
     *
     * @param dataset dataset to fill table with
     * @param stats Does nothing
     */
    @Override
    public void update(List<Data>[] dataset, Statistics[] stats){
        //clear 
        tableVBox.getChildren().clear();
        
        int n;
        if (dataset[1].isEmpty()){ //if one dataset is empty
            n = 1;
        } else {
            n = 2;
        }
        //fill and display appropriate number of tables
        for(int i = 0; i < n; i++){ 
            // take Data subset and cast to something JavaFX can interpret
            ObservableList<Data> data = FXCollections.observableArrayList(dataset[0]);

            // Get info by referencing methods in Data (_Property) to populate
            accountCols[i].setCellValueFactory(
                    new PropertyValueFactory("account"));

            addressCols[i].setCellValueFactory(
                    new PropertyValueFactory("address"));

            valueCols[i].setCellValueFactory(
                    new PropertyValueFactory("value"));

            assessClassCols[i].setCellValueFactory(
                    new PropertyValueFactory("propertyClass"));

            neighbourhoodCols[i].setCellValueFactory(
                    new PropertyValueFactory("neighbourhood"));

            latitudeCols[i].setCellValueFactory(
                    new PropertyValueFactory("latitude"));

            longitudeCols[i].setCellValueFactory(
                    new PropertyValueFactory("longitude"));

            //Populate tableVBox
            tables[i].setItems(data); //set table items
            tableVBox.getChildren().add(new Label( //add label for table
                    "Property Assessments for file " + Integer.toString(i+1)));
            tableVBox.getChildren().add(tables[i]); //add table
        }
    }

}
