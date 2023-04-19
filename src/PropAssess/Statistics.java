package PropAssess;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Class contains variety of methods to compute descriptive statistics
 * Also provides a method to automatically compute and store descriptive 
 * statistics
 * 
 * @author Group 4
 */
public class Statistics {
    public int n;
    public double min;
    public double max;
    public double mean;
    public double range;
    public double stdDev;
    public double median;
    
    /**
     * Collects all statistical methods under one method
     * 
     * @param data data to compute statistics from
     */
    public final void updateStats(List<Double> data){
        n = getN(data);
        min = min(data);
        max = max(data);
        range = range(data);
        mean = mean(data);
        stdDev = stdDev(data);
        median = median(data);
    }
    
    /**
     * Creates a formatted statistics string.
     *
     * @return formatted string with all the descriptive statistics
     */
    @Override
    public String toString() {

        DecimalFormat formatter = new DecimalFormat("#,###"); //format for nums
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        StringBuilder sb = new StringBuilder();
        //print stats
        sb.append("Number of properties: ");
        sb.append(formatter.format(n));
        sb.append("\n\rMin: $");
        sb.append(formatter.format(min));
        sb.append("\n\rMax: $");
        sb.append(formatter.format(max));
        sb.append("\n\rRange: $");
        sb.append(formatter.format(range));
        sb.append("\n\rMean: $");
        sb.append(formatter.format(mean));
        sb.append("\n\rMedian: $");
        sb.append(formatter.format(median));
        sb.append("\n\rStandard Deviation: $");
        sb.append(formatter.format(stdDev));

        return sb.toString();
    }
    
    /**
     * Returns number of elements in data
     * 
     * @param data data to be computed from
     * @return number of elements in data
     */
    public static int getN(List<Double> data){
        return data.size();
    }

    /**
     * Compute the minimum value of data
     * 
     * @param data data to compute from
     * @return min value of data
     */
    public static double min(List<Double> data){
        //get first member of data
        double min = data.get(0);
        int n = getN(data); //n elements
        
        //look through data
        for (int i = 1; i < n; i++){
            double currval = data.get(i);
            
            //if current value is smaller than current min
            if (currval < min){
                min = currval; //set min to current value
            } 
        }
        return min;
    }
    
    /**
     * Compute the maximum value of data
     * 
     * @param data data to compute from
     * @return max value of data
     */
    public static double max(List<Double> data){
        //get first member of data
        double max = data.get(0);
        int n = getN(data); //n elements
        
        //look through data
        for (int i = 1; i < n; i++){
            double currval = data.get(i);
            
            //if current value is larger than current max
            if (currval > max) {
                max = currval; //set max to current value
            }
        }
        return max;
    }
    
    /**
     * Computes range of the data (difference between 
     * max and min values)
     * 
     * @param data data to compute from
     * @return range of property values
     */
    public static double range(List<Double> data){
        return max(data) - min(data);
    }
    
    /**
     * Compute the mean value of data
     * 
     * @param data data to compute from
     * @return mean value of data
     */
    public static double mean(List<Double> data){
        double sum = 0;
        int n = getN(data); //n elements
        for (int i = 0; i < n; i++){//compute the sum
            sum += data.get(i);
        }
        // divide sum by n to get mean
        double mean = sum / n;
        
        return mean;
    }
    
    /**
     * Compute standard deviation of data
     * 
     * @param data data to compute from
     * @return standard deviation of data
     */
    public static double stdDev(List<Double> data){
        double sd = 0;
        int n = getN(data); //n elements
        
        double mean = mean(data); //get mean
        
        // for every value in data
        for (int i = 0; i < n; i++){
            double val = data.get(i); //get val
            val -= mean; //subtract mean from val
            val = val * val; //square val
            sd += val; //then add to sum
        }
        
        //divide sum by n to get variance, then square root result for std. dev.
        sd = sd / n;
        sd = Math.sqrt(sd);
        
        return sd;
    }
    
    /**
     * Compute median of data
     * 
     * @param data data to compute from
     * @return median of data
     */
    public static double median(List<Double> data){
        double median;
        int n = getN(data); //n elements
        if (n % 2 != 0){ //if size of assessment is odd
            median = data.get(n/2);
            
        } else { //if size of assessment is odd
            Double left = data.get(n/2);
            Double right = data.get(n/2+1);
            median = (left + right)/2;
        }
        
        return median;
    }
            
}
