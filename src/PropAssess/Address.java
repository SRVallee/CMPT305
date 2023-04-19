package PropAssess;

/**
 * Class to contain information for a single address.
 * 
 * @author Group 4
 */
public class Address {
    // suite is optional, and so may not be initalisised in constructor
    private String suite = null; // default value is null unless overridden
    private String houseNumber;
    private String streetName;
    
    /**
     * Constructor for neighbourhood class. This constructor has an argument for
     * optional suite field.
     * 
     * @param suite suite of address
     * @param houseNumber house number of address
     * @param streetName street name of address
     */
    public Address(String suite, String houseNumber, String streetName) {
        this(houseNumber, streetName);
        this.suite = suite;
    }
    
    /**
     * Constructor for neighbourhood class. This constructor does not have an
     * argument for optional suite field.
     *
     * @param houseNumber
     * @param streetName
     */
    public Address(String houseNumber, String streetName) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }
    
    /**
     * Override toString method, returns formatted address
     * 
     * @return formatted address
     */
    @Override
    public String toString(){
        String strAddress = "";
        // if there is a suite, add to address
        if (suite != null){
            strAddress += suite + " ";
        }
        // otherwise return house number and street address
        strAddress += houseNumber + " " + streetName;
        return strAddress;
    }
    
    /**
     * Getter for suite. May return null if there is no suite
     * 
     * @return suite for address, may return null if no suite exists
     */
    public String getSuite() {
        return suite;
    }
    
    /**
     * Getter for house number.
     * 
     * @return house number of address
     */
    public String getHouseNumber() {
        return houseNumber;
    }
    
    /**
     * Getter for street name.
     * 
     * @return street name of address
     */
    public String getStreetName() {
        return streetName;
    }
    
}
