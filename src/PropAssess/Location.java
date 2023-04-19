package PropAssess;

/**
 * Class to contain location data
 * 
 * @author Group 4
 */
public class Location {
    private double latitude;
    private double longitude;

    /**
     * Constructor for Location object
     * 
     * @param latitude latitude of point
     * @param longitude longitude of point
     */
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Get latitude
     * 
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }
    
    /**
     * Get longitude
     * 
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
    /**
     * Print formatted longitude and latitude 
     * 
     * @return String (lat, long)
     */
    @Override
    public String toString() {
        String out = "(" + latitude + ", " + longitude + ")";
        return out;
    }
    
}
