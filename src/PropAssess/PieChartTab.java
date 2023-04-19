/*
 * Program demontrates how the PieChartTab is created with 4 
 * different Pie Charts. Program will be used by the PropAssessProgramFXMLController to 
 * populate and update all pie charts
*/ 
package PropAssess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;



/**
 * The class creates PieChartTab object with 4 different pie charts, populates and updates each pie chart 
 * @author SasaBorojevic
*/ 
public class PieChartTab extends TabTemplate {
    
    
    private PieChart allneighbourhoodsPieChart;
    private PieChart neighbourhoodPieChart; 
    private PieChart assessmentClassPieChart;
    private PieChart garagePieChart;
    private PieChart allneighbourhoodsPieChart2;
    private PieChart neighbourhoodPieChart2; 
    private PieChart assessmentClassPieChart2;
    private PieChart garagePieChart2;
    /*
    * PieChartTab constructor
    * @param PieChart,PieChart,PieChart, PieChart
    */
    public PieChartTab(PieChart allneighbourhoods,PieChart neighbourhood,PieChart classes,PieChart garage,
        PieChart allneighbourhoods2,PieChart neighbourhood2,PieChart classes2,PieChart garage2){
        
        allneighbourhoodsPieChart=allneighbourhoods;
        allneighbourhoodsPieChart2=allneighbourhoods2;
        neighbourhoodPieChart=neighbourhood;
        neighbourhoodPieChart2=neighbourhood2;
        assessmentClassPieChart=classes;
        assessmentClassPieChart2=classes2;
        garagePieChart=garage;
        garagePieChart2=garage2;
           
    }
    
  
    /*
    * update method takes a new data set and updates all pie charts 
    * @param List, Statistics -> does nothing
    */
    @Override
    void update(List<Data>[] dataset, Statistics[] stats) {
        
        // clear all charts
        allneighbourhoodsPieChart.getData().clear();
        neighbourhoodPieChart.getData().clear();
        assessmentClassPieChart.getData().clear();
        garagePieChart.getData().clear();
        allneighbourhoodsPieChart2.getData().clear();
        neighbourhoodPieChart2.getData().clear();
        assessmentClassPieChart2.getData().clear();
        garagePieChart2.getData().clear();
         
        if (dataset[1].isEmpty()){ //if one dataset is empty
            
            fillCharts (dataset[0],allneighbourhoodsPieChart,neighbourhoodPieChart,assessmentClassPieChart,garagePieChart);    
        } 
        else {
            if (!dataset[0].isEmpty()){
                fillCharts (dataset[0],allneighbourhoodsPieChart,neighbourhoodPieChart,assessmentClassPieChart,garagePieChart);
            }
            if (!dataset[1].isEmpty()){
                fillCharts (dataset[1],allneighbourhoodsPieChart2,neighbourhoodPieChart2,assessmentClassPieChart2,garagePieChart2);
            }       
        }
    }
    /*
    * fillCharts fills charts with data from the dataset.
    * @param List<Data>, PieChart,PieChart,PieChart,PieChart
    */
    public void fillCharts (List<Data>dataset,PieChart allneighbourhoods,PieChart neighbourhoods,PieChart classes,PieChart garage){
    
    // set titles
    allneighbourhoods.setTitle("All Neighbourhoods");
    neighbourhoods.setTitle("Most Popular Neighbourhoods");
    classes.setTitle("Assessment Classes");
    garage.setTitle("Garage");
    
    // set widths
    classes.setMinWidth(310);
    garage.setMinWidth(100);  
    allneighbourhoods.setMinWidth(300);
    
    int noCounter=0; //counter for properties without a garage
    int yesCounter=0; // counter for properties with a garage       
    
    // lists for non-residential and residential data. Sizes are used for assessmentClassPieCHart   
    List<Data> res = Searcher.getClassByResidential(true, dataset);
    List<Data> nonRes = Searcher.getClassByResidential(false, dataset);                              
  
    // Data for all neighbourhoods pie chart and most popular neighbourhoods pie chart
    ObservableList<PieChart.Data> neighbourhoodData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> allneighbourhoodsData =FXCollections.observableArrayList();                    
   
    // lists for most popular neighbourhoods
    List<Data> westmount = Searcher.getAssessmentNeighbourhood("Westmount", dataset);
    List<Data> glenora = Searcher.getAssessmentNeighbourhood("Glenora", dataset);
    List<Data> strathcona = Searcher.getAssessmentNeighbourhood("Strathcona", dataset);      
    List<Data> downtown = Searcher.getAssessmentNeighbourhood("Downtown", dataset);
    List<Data> ritchie = Searcher.getAssessmentNeighbourhood("Ritchie", dataset);
    List<Data> oliver = Searcher.getAssessmentNeighbourhood("Oliver", dataset);
    List<Data> garneau = Searcher.getAssessmentNeighbourhood("Garneau", dataset);
    List<Data> strathearn = Searcher.getAssessmentNeighbourhood("Strathearn", dataset);
    
    // number of all neighbourhoods that are not one of the most popular neighbourhoods      
    int otherSize=dataset.size()-westmount.size()-glenora.size()-strathcona.size()-
            downtown.size()-ritchie.size()-oliver.size()-garneau.size()-strathearn.size();
    // number of all popular neighbourhoods
    int popNeighSize = westmount.size()+glenora.size()+strathcona.size()+
            downtown.size()+ritchie.size()+oliver.size()+garneau.size()+strathearn.size();
        
    if (dataset.size()>1){// if there is more than one account in dataset
        
        if(popNeighSize!=0){// there is at least one of the popular neighbourhoods in the dataset
            neighbourhoodData.add(new PieChart.Data("Westmount", westmount.size()));
            neighbourhoodData.add(new PieChart.Data("Glenora", glenora.size()));
            neighbourhoodData.add(new PieChart.Data("Strathcona",strathcona.size()));
            neighbourhoodData.add(new PieChart.Data("Downtown",downtown.size()));
            neighbourhoodData.add(new PieChart.Data("Ritchie",ritchie.size()));
            neighbourhoodData.add(new PieChart.Data("Oliver",oliver.size()));
            neighbourhoodData.add(new PieChart.Data("Garneau",garneau.size()));
            neighbourhoodData.add(new PieChart.Data("Strathearn",strathearn.size()));
        
            allneighbourhoodsData.add(new PieChart.Data("Most Popular", popNeighSize));
            allneighbourhoodsData.add(new PieChart.Data("Other",otherSize));
            neighbourhoods.setMinWidth(300);
            }
            else if (popNeighSize==0){// if there isn't any popular neighbourhoods
                allneighbourhoods.setMinWidth(470);
                neighbourhoods.setMinWidth(0);
                neighbourhoods.setTitle("");
                classes.setMinWidth(350);
                classes.setLayoutX(-500);
                List<String>strlist = new ArrayList();
                Set<String> strset = new HashSet<String>();
                for (Data obj: dataset){
                    strlist.add(obj.getNeighbourhood().toString());
                    strset.add(obj.getNeighbourhood().toString());
                }
                for (String str:strset){
                    int occurrences = Collections.frequency(strlist, str);
                   
                    allneighbourhoodsData.add(new PieChart.Data(str, occurrences));               
                }
                
            }    
        }
        else{// if there is only one account in dataset
            allneighbourhoodsData.add(new PieChart.Data(dataset.get(0).getNeighbourhood().toString(), 1));
            
        }
    
    
    for (Data obj: dataset){//keep track of properties with and without a garage
        if(obj.hasGarage()){yesCounter+=1;}
        else{noCounter+=1;}
    }
    
 
    // Pie chart data for assessment classes
    ObservableList<PieChart.Data> classData =
                FXCollections.observableArrayList(
                new PieChart.Data("Residential", res.size()),
                new PieChart.Data("Non-Residential", nonRes.size()));
    
    // Pie chart data for garage                        
    ObservableList<PieChart.Data> garageData =
                FXCollections.observableArrayList(
                new PieChart.Data("Yes", yesCounter),
                new PieChart.Data("No",noCounter));
    
    // set the data to each pie chart
    neighbourhoods.setData(neighbourhoodData);
    classes.setData(classData);
    garage.setData(garageData);  
    allneighbourhoods.setData(allneighbourhoodsData);
    
    // get percentage for each slice of the 4 pie charts
    showPercent(neighbourhoods);
    showPercent(classes);
    showPercent(garage);
    showPercent(allneighbourhoods);
    
    neighbourhoods.layout();
    classes.layout();
    garage.layout();  
    allneighbourhoods.layout();
    } 
    /*
    * showPercent shows percentages for each slice of a pie chart
    * @param PieChart
    */
    public void showPercent(PieChart chart){
        double total = 0;
        for (PieChart.Data d : chart.getData()) {//get the total value of the pie chart
            total += d.getPieValue();}
        final double total1 = total;
        chart.getData().forEach(data -> {
            String percentage = String.format("%.2f%%",data.getPieValue()/total1*100);// divide slice value by total
            Tooltip toolTip = new Tooltip(percentage);
            Tooltip.install(data.getNode(), toolTip);
            });
    }



}
