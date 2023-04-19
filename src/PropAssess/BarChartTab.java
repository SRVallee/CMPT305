
package PropAssess;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;


/**
 *
 * @author Paul B
 */
public class BarChartTab extends TabTemplate{
    
    private BarChart valueByClassChart;
    private BarChart valueByWardChart;
    private BarChart propertiesByWardChart;
    private BarChart propertiesByGarageChart;
    private BarChart valueByClassChart2;
    private BarChart propertiesByGarageChart2;
    private int passNum = 0;
    private List<Data> wardList2020;

    
    /**
     * Constructor for BarChartTab. Sets titles and widths.
     * 
     * @param valClassChart - BarChart for assess value by assess class chart
     * @param valWardChart - BarChart for 2020 assess value for each ward
     * @param propWardChart - BarChart for number of properties in 2020 in each ward
     * @param propGarageChart - BarChart for number of properties that have garages or not
     * @param valClassChart2 - BarChart for comparing another assess value and class year
     * @param propGarageChart2 - BarChart for comparing another properties and garage year
     */
    public BarChartTab(BarChart valClassChart, BarChart valWardChart,
            BarChart propWardChart, BarChart propGarageChart, BarChart valClassChart2,
            BarChart propGarageChart2){
        
        valueByClassChart = valClassChart;
        valueByClassChart.setTitle("Assess Value by Assess Class - File 1");
        valueByClassChart.setMinWidth(300);
        
        valueByWardChart = valWardChart;
        valueByWardChart.setTitle("Assessment Value by Ward 2020");
        valueByWardChart.setMinWidth(400);
        
        propertiesByWardChart = propWardChart;
        propertiesByWardChart.setTitle("Properties by Ward 2020");
        propertiesByWardChart.setMinWidth(400);
        
        propertiesByGarageChart = propGarageChart;
        propertiesByGarageChart.setTitle("Properties by Garage - File 1");
        propertiesByGarageChart.setMinWidth(300);
        
        valueByClassChart2 = valClassChart2;
        valueByClassChart2.setTitle("Assess Value by Assess Class - File 2");
        valueByClassChart2.setMinWidth(300);
        
        propertiesByGarageChart2 = propGarageChart2;
        propertiesByGarageChart2.setTitle("Properties by Garage - File 2");
        propertiesByGarageChart2.setMinWidth(300);
        
        valueByClassChart.setAnimated(false);
        propertiesByGarageChart.setAnimated(false);
        valueByWardChart.setAnimated(false);
        propertiesByWardChart.setAnimated(false);
        valueByClassChart2.setAnimated(false);
        propertiesByGarageChart2.setAnimated(false);
    }
    
    /**
     * Updates bar charts with data of selected years.
     * 
     * @param dataList - list of data objects which each represent a property
     * @param stats - not used in this class
     */
    @Override
    public void update(List<Data>[] dataList, Statistics[] stats){
        
        valueByClassChart.getData().clear();
        propertiesByGarageChart.getData().clear();
        valueByWardChart.getData().clear();
        propertiesByWardChart.getData().clear();
        valueByClassChart2.getData().clear();
        propertiesByGarageChart2.getData().clear();
        
        valueByClassChart.setAnimated(false);
        propertiesByGarageChart.setAnimated(false);
        valueByWardChart.setAnimated(false);
        propertiesByWardChart.setAnimated(false);
        valueByClassChart2.setAnimated(false);
        propertiesByGarageChart2.setAnimated(false);
        //Only 2020 dataset has wards labelled. Store the file so that whenever it updates it stays 2020 data.
        if (passNum == 0){
            wardList2020 = dataList[0];
            passNum += 1;
        }
        
        barChartsWardData(wardList2020, valueByWardChart, propertiesByWardChart);
        //Single dataset
        if (dataList[1].isEmpty()){
            barChartsYearlyData(dataList[0], valueByClassChart, propertiesByGarageChart);
        }
        //Comparison
        else{
            barChartsYearlyData(dataList[0], valueByClassChart, propertiesByGarageChart);
            barChartsYearlyData(dataList[1], valueByClassChart2,propertiesByGarageChart2);
        }
    }
    
    /**
     * barChartsYearlyData creates BarCharts that change when the property data year changes.
     * 
     * @param dataset - list of objects, each one representing a property
     * @param valClass - BarChart for sum of assess values by assess classes
     * @param propGarage - BarChart for number of properties with garages or not
     */
    public void barChartsYearlyData(List<Data> dataset, BarChart valClass, BarChart propGarage){
        
        //Data Lists and numbers for charts
        List<Data> residentialList = Searcher.getClassByResidential(true, dataset);
        List<Data> nonResidentialList = Searcher.getClassByResidential(false, dataset);
        
        double residentialValue = getAssessmentSum(residentialList);
        double nonResidentialValue = getAssessmentSum(nonResidentialList);
        
        int hasGarage = 0;
        int hasNoGarage = 0;
        for (Data property : dataset){
            if (property.hasGarage()){
                hasGarage += 1;
            }
            else{
                hasNoGarage += 1;
            }
        }        
        
        //Chart - Assessment Value by Assessment Class
        
        XYChart.Series valClassSeries = new XYChart.Series();
        valClassSeries.getData().add(new XYChart.Data("Residential", residentialValue));
        valClassSeries.getData().add(new XYChart.Data("Non-Residential", nonResidentialValue));
        valClass.getData().add(valClassSeries);
        valClass.setLegendVisible(false);
        valClass.layout();
        
        //Chart - Propeties with Garages
        
        XYChart.Series propGarageSeries = new XYChart.Series();
        propGarageSeries.getData().add(new XYChart.Data("Has Garage", hasGarage));
        propGarageSeries.getData().add(new XYChart.Data("No Garage", hasNoGarage));
        propGarage.getData().add(propGarageSeries);
        propGarage.setLegendVisible(false);
        propGarage.layout();

    }
    
    /**
     * barChartsWardData creates bar charts that have ward on the x-axis. Only the
     * 2020 year data calls on this as it is the only data set with wards for the
     * properties.
     * 
     * @param dataset - list of objects, each one representing a property
     * @param valWard - BarChart for total assess values of each ward
     * @param propWard - BarChart for number of properties in each ward
     */
    public void barChartsWardData(List<Data> dataset, BarChart valWard, BarChart propWard){
        
        //Data lists and numbers for charts
        List<Data> ward1List = getWardList("Ward 1", dataset);
        List<Data> ward2List = getWardList("Ward 2", dataset);
        List<Data> ward3List = getWardList("Ward 3", dataset);
        List<Data> ward4List = getWardList("Ward 4", dataset);
        List<Data> ward5List = getWardList("Ward 5", dataset);
        List<Data> ward6List = getWardList("Ward 6", dataset);
        List<Data> ward7List = getWardList("Ward 7", dataset);
        List<Data> ward8List = getWardList("Ward 8", dataset);
        List<Data> ward9List = getWardList("Ward 9", dataset);
        List<Data> ward10List = getWardList("Ward 10", dataset);
        List<Data> ward11List = getWardList("Ward 11", dataset);
        List<Data> ward12List = getWardList("Ward 12", dataset);
        
        double ward1ListValue = getAssessmentSum(ward1List);
        double ward2ListValue = getAssessmentSum(ward2List);
        double ward3ListValue = getAssessmentSum(ward3List);
        double ward4ListValue = getAssessmentSum(ward4List);
        double ward5ListValue = getAssessmentSum(ward5List);
        double ward6ListValue = getAssessmentSum(ward6List);
        double ward7ListValue = getAssessmentSum(ward7List);
        double ward8ListValue = getAssessmentSum(ward8List);
        double ward9ListValue = getAssessmentSum(ward9List);
        double ward10ListValue = getAssessmentSum(ward10List);
        double ward11ListValue = getAssessmentSum(ward11List);
        double ward12ListValue = getAssessmentSum(ward12List);
        
        //Chart - Assessment Value by Ward
        
        XYChart.Series valWardSeries = new XYChart.Series();
        valWardSeries.getData().add(new XYChart.Data("Ward 1", ward1ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 2", ward2ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 3", ward3ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 4", ward4ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 5", ward5ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 6", ward6ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 7", ward7ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 8", ward8ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 9", ward9ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 10", ward10ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 11", ward11ListValue));
        valWardSeries.getData().add(new XYChart.Data("Ward 12", ward12ListValue));
        valWard.getData().add(valWardSeries);
        valWard.setLegendVisible(false);
        valWard.layout();
     
        
        //Chart - Properties by Ward
        
        XYChart.Series propWardSeries = new XYChart.Series();
        propWardSeries.getData().add(new XYChart.Data("Ward 1", ward1List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 2", ward2List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 3", ward3List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 4", ward4List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 5", ward5List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 6", ward6List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 7", ward7List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 8", ward8List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 9", ward9List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 10", ward10List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 11", ward11List.size()));
        propWardSeries.getData().add(new XYChart.Data("Ward 12", ward12List.size()));
        propWard.getData().add(propWardSeries);
        propWard.setLegendVisible(false);
        propWard.layout();
    }
    
    /**
     * getWardList gets a list from another list of objects that only contains
     * a specific ward.
     * 
     * @param wardName - name of ward a list is needed for
     * @param dataList - list of objects, each one representing a property
     * @return - a list of objects, each being properties of the same ward
     */
    public static List<Data> getWardList(String wardName, List<Data> dataList){
        ArrayList<Data> wardList = new ArrayList<>();
        for (Data property : dataList){
            Neighbourhood currentNeighbourhood = property.getNeighbourhood();
            String ward = currentNeighbourhood.getWard();
            if (ward.equalsIgnoreCase(wardName)){
                wardList.add(property);
            }
        }
        return wardList;
    }
    
    /**
     * getAssessmentSum gets the sum of assessed values of properties in a list 
     * of properties.
     * 
     * @param dataList - list of objects, each one representing a property
     * @return - the sum of the assessed values in the provided list
     */
    public static double getAssessmentSum(List<Data> dataList){
        double assessSum = 0;
        for (Data property : dataList){
            assessSum += property.getValue();
        }
        return assessSum;
    }
}