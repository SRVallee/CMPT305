package PropAssess;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for Searcher class
 * 
 * @author Sera Vallee <3045024>
 */
public class SearcherTest {
    public Searcher s = new Searcher();
    public List<Data> list = new ArrayList<>();
    
    
    @BeforeAll
    public void setUpClass() {
        String csvStr1 = 
                "1," //acc num
                + "," //suite
                + "42," //house num
                + "66 STREET NW," //street
                + "Y," //garage
                + "10," //nieghbourhood id
                + "MEADOWLARK," //neighbouthood name
                + "Ward 1," //ward
                + "304000," //value
                + "53.5198329083354," //lat
                + "-113.598369450708," //long
                + "POINT (-113.598369450708 53.5198329083354)," //point
                + "100," //class 1 percent
                + "," //2 percent
                + "," //3 percent
                + "RESIDENTIAL," //class 1 name
                + "," //2 name
                + ""; //3 name
        
        String csvStr2 = 
                "2," //acc num
                + "100," //suite
                + "16006," //house num
                + "87 AVENUE NW," //street
                + "N," //garage
                + "10," //nieghbourhood id
                + "CHAPELLE," //neighbouthood name
                + "Ward 2," //ward
                + "9999," //value
                + "53.5198329458316," //lat
                + "-113.5983694862031," //long
                + "POINT (-113.5983694862031 53.5198329458316)," //point
                + "90," //class 1 percent
                + "10," //2 percent
                + "," //3 percent
                + "COMMERCIAL," //class 1 name
                + "RESIDENTIAL," //2 name
                + ""; //3 name
        
        list.add(new Data(csvStr1));
        list.add(new Data(csvStr2));
        
        Field dataField;
        try {
            dataField = Searcher.class.getDeclaredField("data");
        } catch (Exception e){
            System.out.println("Failed to retrieve data field");
            return;
        }
        dataField.setAccessible(true);
        try {
            dataField.set(s, list);
        } catch (Exception e){
            System.out.println("Failed to set data field");
        }
    }
    
    @AfterAll
    public void tearDownClass() {
    }

    /**
     * Test of readCSV method, of class Searcher.
     * 
     */
    @Test
    public void testReadCSV() throws Exception {
        System.out.println("readCSV");
        String fileName = "Property_Assessment_Data.csv";
        Searcher instance = new Searcher();
        instance.readCSV(fileName);
        List<Data> data = instance.getData();
        assertTrue(data.size() == 407192);
    }
    
    /**
     * Test of readCSV method, of class Searcher for filenotfound exception
     */
    @Test
    public void testReadCSVExcept(){
        System.out.println("readCSV");
        String fileName = "Property_Assessment_Data";
        Searcher instance = new Searcher();
        assertThrows(FileNotFoundException.class, () -> {
            instance.readCSV(fileName);}
        );
    }

    /**
     * Test of getAccount method, of class Searcher.
     */
    @Test
    public void testGetAccount() {
        System.out.println("getAccount");
        int accountNumber = 1;
        Searcher instance = s;
        int expResult = list.get(0).getAccountNumber();
        int result = instance.getAccount(accountNumber).getAccountNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAddresses method, of class Searcher.
     */
    @Test
    public void testGetAddresses() {
        System.out.println("getAddresses");
        String substring = "";
        List<Data> subset = list;
        List<Data> expResult = null;
        List<Data> result = Searcher.getAddresses(substring, subset);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNeighbourhood method, of class Searcher.
     */
    @Test
    public void testGetNeighbourhood() {
        System.out.println("getNeighbourhood");
        String neighbourhoodName = "Meadowlark";
        List<Data> subset = list;
        int expResult = 1;
        List<Data> resultList = Searcher.getNeighbourhood(neighbourhoodName, subset);
        assertEquals(resultList.size(), 1);
        int result = resultList.get(0).getAccountNumber();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAssessmentClass method, of class Searcher.
     */
    @Test
    public void testGetAssessmentClass() {
        System.out.println("getAssessmentClass");
        String assessClass = "COMMERCIAL";
         List<Data> subset = list;
        int expResult = 2;
        List<Data> resultList = Searcher.getAssessmentClass(assessClass, subset);
        assertEquals(resultList.size(), 1);
        int result = resultList.get(0).getAccountNumber();
        assertEquals(expResult, result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getClassByResidential method, of class Searcher.
     */
    @Test
    public void testGetClassByResidential() {
        System.out.println("getClassByResidential");
        boolean residential = true;
        List<Data> subset = list;
        int expResult = 1;
        List<Data> resultList = Searcher.getClassByResidential(residential, subset);
        assertEquals(resultList.size(), 1);
        int result = resultList.get(0).getAccountNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class Searcher.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Searcher instance = s;
        int expResult = list.size();
        List<Data> result = instance.getData();
        assertEquals(result.size(), 2);
    }

    /**
     * Test of getStatsString method, of class Searcher.
     */
    @Test
    public void testGetStatsString() {
        System.out.println("getStatsString");
        List<Data> subset = list;
        String expResult = 
        "Statistics of Assessed Values:\n"
        + "\n\rNumber of properties: "
        + "\n\rMin: $9,999"
        + "\n\rMax: $304,000"
        + "\n\rRange: $294001"
        + "\n\rMean: $1567000"
        + "\n\rMedian: $157000"
        + "\n\rStandard Deviation: $147001";
        
        String result = Searcher.getStatsString(subset);
        assertEquals(expResult, result);
    }
    
}
