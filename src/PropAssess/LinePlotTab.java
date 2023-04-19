/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PropAssess;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Riley
 */
public class LinePlotTab extends TabTemplate{
    
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart <Number, Number> linePlot;
    private XYChart.Series series = new XYChart.Series();
    private XYChart.Series series2 = new XYChart.Series();
    private Statistics[] yearlyStats = {new Statistics(), new Statistics(), new Statistics(), new Statistics(), new Statistics(), new Statistics(), new Statistics(), new Statistics()};
    private Searcher[] searchers = {new Searcher(), new Searcher(), new Searcher(), new Searcher(), new Searcher(), new Searcher(), new Searcher(), new Searcher()};
    private List<Data>[] subsets = new List[8];
    
    public LinePlotTab(LineChart plot, CategoryAxis x, NumberAxis y){ 
        linePlot = plot;
        xAxis = x;
        yAxis = y;
        xAxis.setLabel("Year");
        yAxis.setLabel("Value($)");
        
        try{
            searchers[0].readCSV("Property_Assessment_Data_2019.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[0] = searchers[0].getData();
        
        try{
            searchers[1].readCSV("Property_Assessment_Data_2018.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[1] = searchers[1].getData();
        
        try{
            searchers[2].readCSV("Property_Assessment_Data_2017.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[2] = searchers[2].getData();
        
        try{
            searchers[3].readCSV("Property_Assessment_Data_2016.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[3] = searchers[3].getData();
        
        try{
            searchers[4].readCSV("Property_Assessment_Data_2015.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[4] = searchers[4].getData();
        
        try{
            searchers[5].readCSV("Property_Assessment_Data_2014.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[5] = searchers[5].getData();
        
        try{
            searchers[6].readCSV("Property_Assessment_Data_2013.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[6] = searchers[6].getData();
        
        try{
            searchers[7].readCSV("Property_Assessment_Data_2012.csv");
        } catch (FileNotFoundException exception){
            System.out.println("File not found, ERROR");
        }
        subsets[7] = searchers[7].getData();
        
        
        for (int j = 0; j < 8; j++){
            ArrayList<Double> values = new ArrayList<>();
            for (Data datum : subsets[j]){
                values.add(datum.getValue());
            }
            yearlyStats[j].updateStats(values);
        }
        
        needsUpdate=true;
        
    }
    /**
     * Fill the Property Assessments table with from a dataset
     *
     * @param dataset dataset to fill table with
     * @param stats Does nothing
     */
    @Override
    public void update(List<Data>[] dataset, Statistics[] stats) { 
        
        linePlot.setAnimated(false);
        linePlot.getData().clear();
        series.getData().clear();
        linePlot.setLegendSide(Side.RIGHT);
        series.setName("Median");
        series.getData().add(new XYChart.Data<>("2012", yearlyStats[7].median));
        series.getData().add(new XYChart.Data<>("2013", yearlyStats[6].median));
        series.getData().add(new XYChart.Data<>("2014", yearlyStats[5].median));
        series.getData().add(new XYChart.Data<>("2015", yearlyStats[4].median));
        series.getData().add(new XYChart.Data<>("2016", yearlyStats[3].median));
        series.getData().add(new XYChart.Data<>("2017", yearlyStats[2].median));
        series.getData().add(new XYChart.Data<>("2018", yearlyStats[1].median));
        series.getData().add(new XYChart.Data<>("2019", yearlyStats[0].median));
        series.getData().add(new XYChart.Data<>("2020", stats[0].median));
        
        series2.setName("Mean");
        series2.getData().add(new XYChart.Data<>("2012", yearlyStats[7].mean));
        series2.getData().add(new XYChart.Data<>("2013", yearlyStats[6].mean));
        series2.getData().add(new XYChart.Data<>("2014", yearlyStats[5].mean));
        series2.getData().add(new XYChart.Data<>("2015", yearlyStats[4].mean));
        series2.getData().add(new XYChart.Data<>("2016", yearlyStats[3].mean));
        series2.getData().add(new XYChart.Data<>("2017", yearlyStats[2].mean));
        series2.getData().add(new XYChart.Data<>("2018", yearlyStats[1].mean));
        series2.getData().add(new XYChart.Data<>("2019", yearlyStats[0].mean));
        series2.getData().add(new XYChart.Data<>("2020", stats[0].mean));
        
        linePlot.getData().addAll(series, series2);
        
        
        
    }
}