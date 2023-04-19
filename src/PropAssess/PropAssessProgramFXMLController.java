package PropAssess;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Group 4
 */
public class PropAssessProgramFXMLController implements Initializable {
    //program vars
    private Searcher[] searchers = {new Searcher(), new Searcher()};
    private Statistics[] stats = {new Statistics(), new Statistics()};
    private List<TabTemplate> tabs;
    private List<Data>[] subsets = new List[2];
    
    //tab pane
    @FXML private TabPane tabWindow;
    
    //container for DataTableTab
    @FXML private VBox tableVBox;
    
    //PieChartTab
    @FXML private PieChart allneighbourhoodsPieChart;
    @FXML private PieChart neighbourhoodPieChart; 
    @FXML private PieChart assessmentClassPieChart;
    @FXML private PieChart garagePieChart;
    @FXML private PieChart allneighbourhoodsPieChart2;
    @FXML private PieChart neighbourhoodPieChart2; 
    @FXML private PieChart assessmentClassPieChart2;
    @FXML private PieChart garagePieChart2;
    
    //BarChartTab
    @FXML private BarChart valueByClassChart;
    @FXML private BarChart valueByClassChart2;
    @FXML private BarChart valueByWardChart;
    @FXML private BarChart propertiesByWardChart;
    @FXML private BarChart propertiesByGarageChart;
    @FXML private BarChart propertiesByGarageChart2;
    
    //these fields are for the side panel
    @FXML private ChoiceBox<String> file1Dropdown;
    @FXML private ChoiceBox<String> file2Dropdown;
    
    @FXML private TextField accountField;
    @FXML private TextField addressField;
    @FXML private TextField neighbourField;
    
    @FXML private ChoiceBox<String> classDropdown;

    @FXML private TextArea statsArea1;
    @FXML private TextArea statsArea2;
    private TextArea[] statsAreas = new TextArea[2];
    
    @FXML private VBox histogramVBox;
    
    @FXML private LineChart LinePlot;
    @FXML private CategoryAxis scatterX;
    @FXML private NumberAxis scatterY;


    @FXML void loadButtonHandler(ActionEvent e){
        //load file 1
        String file1 = file1Dropdown.getSelectionModel().getSelectedItem() + ".csv";
        try{
            searchers[0].readCSV(file1);
        } catch (FileNotFoundException exception){
            System.out.println("File 1 not found, ERROR");
        }
        //load file 2 if one is selected
        String file2 = file2Dropdown.getSelectionModel().getSelectedItem();
        if (!file2.equals("")){
            try {
                searchers[1].readCSV(file2+".csv");
            } catch (FileNotFoundException exception) {
                System.out.println("File 2 not found, ERROR");
            }
        } else {
            searchers[1] = new Searcher();
        }
        //reset everything on load
        resetState();
    }

    
    /**
     * Searches data from csv when clicked based on what's entered into fields 
     * accountField, addressField, neighbourField, and classDropdown.
     * 
     * @param e event, when search button is clicked
     */
    @FXML void searchButtonHandler(ActionEvent e){
        for(int i =0; i < 2; i++){
            List<Data> data = searchers[i].getData(); //set data to be all data
            subsets[i] = filterResults(data); //filer results
        }
        
        //update displays
        resetTabRequiresUpdate();
        updateStatsArea();
        updateCurrTab();
    }
    
    /**
     * When reset button is pushed, clear fields, recalculate default stats
     * and display in current tab
     * 
     * @param e event, clicking the button
     */
    @FXML void resetButtonHandler(ActionEvent e){
        resetState();
    }
    
    /**
     * When selected tab is switched, update current tab if it needs an update.
     * When the Scene is being created, this is called when the first tab is
     * selected by default. So, a runtimeexception occurs at program start, but
     * does not affect program functionality
     * 
     * @param e when selected tab is switched
     */
    @FXML void tabSwitchHandler(Event e){
        try{
            updateCurrTab();
        } catch (RuntimeException exception) {
            //Should only be seen on program start
            System.out.println("Scene initilizing, tab switch failed otherwise");
        }
    }
    
    /**
     * Recalculates stats and reprints stats area if necessary
     */
    public void updateStatsArea(){
        for (int i =0; i < 2; i++){
            statsAreas[i].clear(); //clear stats text area
            if (subsets[i].size() > 0) { //given that the data any elements
                ArrayList<Double> values = new ArrayList<>();
                //build list of assessment values from subset of all data
                for (Data datum : subsets[i]) {
                    values.add(datum.getValue());
                }
                stats[i].updateStats(values);
            }
            if (subsets[i].size() > 1) { //given that the data has more than 1 element
                String statsStr = "Statistics of Assessed Values for file "
                        + Integer.toString(i + 1)
                        + "\r\n\n" 
                        + stats[i].toString();
                statsAreas[i].setText(statsStr);
            }
        }
    }
    
    /**
     * Updates current tab if it needs an update
     */
    public void updateCurrTab(){
        //get currently selected tab
        int i = tabWindow.getSelectionModel().getSelectedIndex();
        TabTemplate currTab = tabs.get(i);
        
        //update
        if (currTab.needsUpdate){
            currTab.update(subsets, stats);
            currTab.needsUpdate = false;
        }
    }
    
    /**
     * Clears all fields, and loads data into subsets and updates necessary
     * elements
     */
    public void resetState(){
        //clear all fields
        accountField.clear();
        addressField.clear();
        neighbourField.clear();
        classDropdown.getSelectionModel().selectFirst();
        for (int i = 0; i < 2; i++) {
            subsets[i] = searchers[i].getData();
        }

        //update displays
        resetTabRequiresUpdate();
        updateStatsArea();
        updateCurrTab();
    }
    
    /**
     * Resets needsUpdate flags in all tabs
     */
    public void resetTabRequiresUpdate(){
        for(TabTemplate currTab : tabs){
            currTab.needsUpdate = true;
        }
    }
    
    /**
     * Filter results starting from most restrictive to least.Pass some 
 data into this program and it will grab entered fields and then filter.
     * 
     * @param data some portion of data to search through
     * @return 
     */
    public List<Data> filterResults(List<Data> data){
        // Get text in account number field
        String accountStr = accountField.getText();
        if (!accountStr.isEmpty() && accountStr.matches("[0-9]+")) { //if something is in account num field and not letters
            data = new ArrayList<>(); //dump subset
            //look for account
            for (int i = 0; i < 2; i++){
                int currAccNum = Integer.valueOf(accountStr);
                Data singleAcc = searchers[i].getAssessmentAccount(currAccNum);
                if (singleAcc != null) { //if account found
                    data.add(singleAcc); //add account to list
                }
            }

        } else { //no account number
            // Get text in address field
            String addressStr = addressField.getText();
            if (!addressStr.isEmpty()) { //field is not empty
                //filter results by address
                data = Searcher.getAssessmentAddresses(addressStr, data);
            }

            // Get text in neighbourhood field
            String neighbourStr = neighbourField.getText();
            if (!neighbourStr.isEmpty()) { //field is not empty
                //filter results by neighbourhood
                data = Searcher.getAssessmentNeighbourhood(neighbourStr, data);
            }

            //get test from classDropdown choice box
            String classStr = classDropdown.getValue();
            if (!classStr.equalsIgnoreCase("")) { //choice is not blank option
                //is residential option
                if (classStr.equalsIgnoreCase("Residential")) {
                    //filter results by residential properties
                    data = Searcher.getClassByResidential(true, data);

                } else { //if non-residential option
                    //filter results by non-residential properties
                    data = Searcher.getClassByResidential(false, data);
                }
            }
        } //end filtering data
        
        return data;
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
        searchers[0].readCSV("Property_Assessment_Data_2020.csv");
        } catch (FileNotFoundException e){
            System.out.println("File not found, exiting.");
            return;
        }
        subsets[0] = searchers[0].getData();
        subsets[1] = searchers[1].getData();
        
        ArrayList<String> fileList = new ArrayList<>();
        for (int i = 2020; i >= 2012; i--){
            fileList.add("Property_Assessment_Data_" + Integer.toString(i));
        }
        
        ObservableList<String> fileOBList = FXCollections.observableList(fileList);
        file1Dropdown.setItems(fileOBList);
        file1Dropdown.getSelectionModel().select(0);
        
        file2Dropdown.setItems(fileOBList);
        file2Dropdown.getItems().add("");
        file2Dropdown.getSelectionModel().select(9);
        
        //set dropdown menu options
        classDropdown.getItems().clear();
        classDropdown.getItems().addAll("", "Residential", "Non-Residential");
        classDropdown.getSelectionModel().selectFirst();
        
        statsAreas[0] = statsArea1;
        statsAreas[1] = statsArea2;
        
        //add tabs to tab list
        tabs = new ArrayList<>();
        tabs.add(new DataTableTab(tableVBox));
        tabs.add(new PieChartTab(allneighbourhoodsPieChart,neighbourhoodPieChart,assessmentClassPieChart,garagePieChart,
        allneighbourhoodsPieChart2,neighbourhoodPieChart2,assessmentClassPieChart2,garagePieChart2));
        tabs.add(new LinePlotTab(LinePlot, scatterX, scatterY)); //Scatter charts TODO: replace
        tabs.add(new BarChartTab(valueByClassChart, valueByWardChart, propertiesByWardChart,
                propertiesByGarageChart, valueByClassChart2, propertiesByGarageChart2));
        tabs.add(new HistogramTab(histogramVBox)); //Histogram charts TODO: replace
        
        
        
        //update displays
        resetTabRequiresUpdate();
        updateStatsArea();
        updateCurrTab();
    }    
    
}
