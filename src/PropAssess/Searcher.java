package PropAssess;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class that creates and searches through a list of Data objects
 * 
 * @author Group 4
 */
public class Searcher {
    private List<Data> data;
    
    /**
     * Constructor for Searcher Object, makes empty list for Data to be read 
     * into
     */
    public Searcher() {
        clearData();
    }
    
    public final void clearData(){
        data = new ArrayList<>();
    }
    
    /**
     * Opens a csv that contains information about property assessments and
     * parses it into a list of Data objects.
     * 
     * @param fileName file to be opened
     * @throws FileNotFoundException if file cannot be found, throw this
     */
    public void readCSV(String fileName) throws FileNotFoundException{
        //start CSV parsing
        clearData();
        String path = Paths.get(".").toString(); //gets working directory
        path += "//" + fileName; //append filename to working directory
        Scanner sc = new Scanner(new File(path)); //open file in scanner
        sc.nextLine(); // skip header in CSV
        
        //get all info from csv and place each line to a Data object
        while(sc.hasNext()){
            String line = sc.nextLine();
            Data datum = new Data(line);
            data.add(datum);
        }
        sc.close();
    }
    
    /**
     * Find an account of a given account number
     * 
     * @param accountNumber account number to search for
     * @return account that matches given account number if it exists otherwise
     * null
     */
    public Data getAssessmentAccount(int accountNumber) {
        Data assessment = null;
        for (Data datum : data) {
            //get account number
            int currNum = datum.getAccountNumber();
            
            //if account found, keep it, and stop looking
            if (currNum == accountNumber) {
                assessment = datum;
                break;
            }
        }
        return assessment;
    }
    
    /**
     * Searches a subset of Data object addresses for a substring. 
     * If contained, append to list and return list
     * 
     * @param substring substring to search address field for
     * @param subset subset of Data object to search through
     * @return list of Data objects with matching addresses
     */
    public static List<Data> getAssessmentAddresses(String substring, List<Data> subset){
        ArrayList<Data> assessments = new ArrayList<>();
        substring = substring.toLowerCase(); //set to lowercase for comparison
        for (Data datum : subset) { //serach through subset
            String datumAddress = null;
            //if address exists
            if (datum.getAddress() != null){
                //get lowercase neighbourhood
                datumAddress = datum.getAddress().toString().toLowerCase();
            }
            //if address string not null and it contains the substring
            if (datumAddress != null && datumAddress.contains(substring)){
                assessments.add(datum); //add assessment
            }
        }
        return assessments;
    }
    
    /**
     * Finds assessments that match a given neighbourhood.
     * 
     * @param neighbourhoodName neighbourhood to search for
     * @param subset subset of Data object to search through
     * @return list of assessments that match given neighbourhood
     */
    public static List<Data> getAssessmentNeighbourhood(String neighbourhoodName, List<Data> subset){
        ArrayList<Data> assessments = new ArrayList<>();
        for (Data datum : subset) {
            //get neighbourhood from data
            Neighbourhood currNeighbourhood = datum.getNeighbourhood();
            String currName = currNeighbourhood.getNeighbourhood();
            
            //if matching neighbourhood found, append
            if (currName.equalsIgnoreCase(neighbourhoodName)) {
                assessments.add(datum);
            } 
        }
        return assessments;
    }
    
    /**
     * Finds assessments with a given assessment class.
     * If assessment's largest class is given assessment class, it is added to 
     * the list. 
     * 
     * @param assessClass assessment class to search for
     * @param subset subset of Data object to search through
     * @return list of assessments that match given class
     */
    public static List<Data> getAssessmentClass(String assessClass, List<Data> subset){
        ArrayList<Data> assessments = new ArrayList<>();
        
        for (Data datum : subset) {
            //get an assessment
            String[] classes = datum.getPropertyClass();
            
            //if largest of the classes matches
            if (classes[0].equalsIgnoreCase(assessClass)){
                assessments.add(datum); //add assessment
            }
        }
        return assessments;
    }
    
    /**
     * Looks only for residential or non-residential properties.
     * Mainly designed for use with the UI. {@link PropAssess.Searcher#getAssessmentClass}
     * is used for more general cases
     * 
     * @see PropAssess.Searcher#getAssessmentClass
     * 
     * @param residential bool if looking for residential properties
     * @param subset subset to search through
     * @return list of properties that match
     */
    public static List<Data> getClassByResidential(boolean residential, List<Data> subset){
        ArrayList<Data> assessments = new ArrayList<>();
        
        for (Data datum : subset) {
            //get an assessment
            String[] classes = datum.getPropertyClass();
            
            //if largest class is residential
            if (classes[0].equalsIgnoreCase("Residential")){
                if(residential){ //if looking for residential
                    assessments.add(datum); //add assessment
                }
            } else { //largest class is not residential
                if(!residential){ //if looking for non-residential
                    assessments.add(datum); //add assessment
                }
            }
        }
        return assessments;
        
    }

    /**
     * Get deep copy of data
     * 
     * @return copy of data
     */
    public List<Data> getData() {
        ArrayList<Data> assessments = new ArrayList<>();
        for (Data datum : data) {
            assessments.add(datum);
        }
        return assessments;
    }

}