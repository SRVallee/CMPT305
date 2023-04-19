package PropAssess;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class that contains data about a property assessment
 * 
 * @author Group 4
 */
public class Data{

    private int accountNumber;
    private Address address;
    private boolean garage;
    private double value;
    private Neighbourhood neighbourhood;
    private Location location;
    private double[] propertyPercent;
    private String[] propertyClass;
    private String ward;
    
    /**
     * Constructor that interprets a String line of CSV.
     * 
     * @param lineCSV line from CSV to be interpreted
     */
    public Data(String lineCSV){
         //split line by commas
        String[] line = lineCSV.split(",", -1);
        
        //account number
        accountNumber = Integer.parseInt(line[0]);

        //address
        if(!line[1].isEmpty()){ //if a suite exists
            //init address with suite
            address = new Address(line[1], line[2], line[3]);

        //unless a house number or street number do not exist
        } else if (!line[2].isEmpty() && !line[3].isEmpty()){
            //init address without suite
            address = new Address(line[2], line[3]);
        }

        //has garage
        garage = line[4].equalsIgnoreCase("Y");

        //neighbourhood (includes id, neighbourhood name, and ward)
        String[] neighbourStrs = new String[3];
        for (int i = 5; i < 8 ; i++){
            String currStr = line[i];
            if (currStr.isEmpty()){
                neighbourStrs[i-5] = "";
            } else {
                neighbourStrs[i-5] = currStr;
            }
        }
        neighbourhood = new Neighbourhood(neighbourStrs[0], 
                neighbourStrs[1], neighbourStrs[2]);
        
        
        //assessed property value
        value = Double.parseDouble(line[8]);
        
        //location
        double latitude;
        if (line[9].isEmpty()){
             latitude = 53.5461;
        } else {
            latitude = Double.parseDouble(line[9]);
        }
        
        double longitude;
        if (line[10].isEmpty()){
            longitude = -113.4938;
        } else {
            longitude = Double.parseDouble(line[10]);
        }
        
        location = new Location(latitude, longitude);
        
        //property percentage
        propertyPercent = new double[3];
        for(int i = 0; i < 3; i++){
            int j = i + 12; // counter starting at 12
            if (line[j].isEmpty()){ //if string is empty, i.e., percent = 0
                propertyPercent[i] = 0;
                
            } else { //add percent to array
                propertyPercent[i] = Double.parseDouble(line[j]);
            }
        }
        
        //property class
        propertyClass = new String[3];
        for(int i = 0; i < 3; i++){
            int j = i + 15; // counter starting at 15
            
            String current;
            try{ // because trailing empty fields may be empty, try
                current = line[j];
            } catch(IndexOutOfBoundsException e){ //if index does not exist
                current = "";
            }
            propertyClass[i] = current;
        }
    }
    
    /**
     * Constructor for Data object from another data object
     * 
     * @param clone Data object to be copied
     */
    public Data(Data clone){
        accountNumber = clone.accountNumber;
        address = clone.address;
        garage = clone.garage;
        neighbourhood = clone.neighbourhood;
        value = clone.value;
        location = clone.location;
        propertyPercent = new double[3];
        System.arraycopy(clone.propertyPercent, 0, propertyPercent, 0, 3);
        propertyClass = new String[3];
        System.arraycopy(clone.propertyClass, 0, propertyClass, 0, 3);
    }
    
    /**
     * Collects information about the assessment data in a formatted fashion
     * 
     * @return formatted string of information
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Account Number = "); 
        sb.append(accountNumber);
        sb.append("\nAddress = "); 
        if (address == null){
            sb.append("null");
        } else {
            sb.append(address.toString());
        }
        sb.append("\nAccessed value = $"); 
        sb.append(Double.toString(value));
        sb.append("\nAssessment class = "); 
        for (String str: propertyClass){ //print all property classes
            sb.append(str);
            sb.append(" ");
        }
        sb.append("\nNeighbourhood = "); 
        sb.append(neighbourhood.toString());
        sb.append("\nLocation = "); 
        sb.append(location.toString());
        return sb.toString();
    }

    /**
     * Override hashcode for functioning equals() method
     * 
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.accountNumber;
        return hash;
    }

    /**
     * Compares this and another assessment to see if they are equal
     * 
     * @param obj either Integer or Data object of other assessment
     * @return if they are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Data) {
            Data other = (Data) obj;
            return this.accountNumber == other.accountNumber;
        } else if (obj instanceof Integer){
            int otherNum = (Integer) obj;
            return accountNumber == otherNum;
        } else {
            return false;
        }
    }

    /**
     * Getter for account number
     * 
     * @return account number of property
     */
    public int getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * @return address of property
     */
    public Address getAddress() {
        return address;
    }
    
    
    /**
     * @return ward of property 
     */
    public String getWard() {
        return ward;
    }
    
    /**
     * @return value of property
     */
    public double getValue() {
        return value;
    }

    public boolean hasGarage() {
        return garage;
    }
    
    /**
     * @return neighbourhood of the property
     */
    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }
    
    /**
     * @return longitude, latitude of the property
     */
    public Location getLocation() {
        return location;
    }
    

    public double[] getPropertyPercent() {
        return propertyPercent;
    }
    
    
    /**
     * @return class of property (residential, commercial, etc.)
     */
    public String[] getPropertyClass() {
        return propertyClass;
    }
    
    
    /********JAVA FX Property Methods********/
    
    /**
     * Base method that to turn string into StringProperty
     *
     * @return String as StringProperty
     */
    private StringProperty getFXProperty(String str) {
        SimpleStringProperty propStr = new SimpleStringProperty();
        propStr.set(str);
        return propStr;
    }
    
    /**
     * Method for JavaFX to grab account number from data obj
     *
     * @return account number as string property
     */
    public StringProperty accountProperty() {
        return getFXProperty(Integer.toString(accountNumber));
    }
    
    /**
     * Method for JavaFX to grab address from data obj
     * 
     * @return address as string property
     */
    public StringProperty addressProperty(){
        String addressProp;
        if (address == null){
            addressProp = "";
        } else {
            addressProp = address.toString();
        }
        
        return getFXProperty(addressProp);
    }
    
    /**
     * Method for JavaFX to grab value from data obj
     * 
     * @return value as string property
     */
    public StringProperty valueProperty(){
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return getFXProperty("$" + formatter.format(value));
    }
    
    /**
     * Method for JavaFX to grab propertyClass from data obj
     *
     * @return largest property class proportion as string property
     */
    public StringProperty propertyClassProperty() {
        if (propertyClass[0].equalsIgnoreCase("Residential")){
            return getFXProperty("Residential");
        } else {
            return getFXProperty("Non-Residential");
        }
        
    }
    
    /**
     * Method for JavaFX to grab neighbourhood from data obj
     * 
     * @return neighbourhood as string property
     */
    public StringProperty neighbourhoodProperty(){
        return getFXProperty(neighbourhood.getNeighbourhood());
    }
    
    /**
     * Method for JavaFX to grab latitude from data obj
     * 
     * @return latitude as string property
     */
    public StringProperty latitudeProperty(){
        return getFXProperty(Double.toString(location.getLatitude()));
    }
    
    /**
     * Method for JavaFX to grab longitude from data obj
     * 
     * @return longitude as string property
     */
    public StringProperty longitudeProperty(){
        return getFXProperty(Double.toString(location.getLongitude()));
    }
    
    
}
