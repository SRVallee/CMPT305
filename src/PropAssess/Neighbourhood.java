package PropAssess;

/**
 * Class to contain information for a neighbourhood.
 * 
 * @author Group 4
 */
public class Neighbourhood {
    private String id;
    private String neighbourhood;
    private String ward;
    
    /**
     * Constructor for Neighbourhood object
     * 
     * @param id
     * @param neighbourhood
     * @param ward 
     */
    public Neighbourhood(String id, String neighbourhood, String ward) {
        this.id = id;
        this.neighbourhood = neighbourhood;
        this.ward = ward;
    }
    
    /**
     * Override toString method, returns formatted neighbourhood string
     * 
     * @return formatted neighbourhood string
     */
    @Override
    public String toString(){
        return neighbourhood + " " + ward;
    }
    
    /**
     * Gets neighbourhood ID
     * 
     * @return 
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets neighbourhood name
     * 
     * @return neighbourhood name
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }
    
    /**
     * Gets ward for neighbourhood
     * 
     * @return ward for neighbourhood
     */
    public String getWard() {
        return ward;
    }
    
}
