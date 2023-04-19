/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PropAssess;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author pawan
 */
public class HistogramTab extends TabTemplate {

    private VBox histogramVBox;
    private BarChart<?, ?>[] histograms = new BarChart<?, ?>[2];

    public HistogramTab(VBox vBox) {
        this.histogramVBox = vBox;
        VBox.setVgrow(histogramVBox, Priority.ALWAYS); //allow elements to grow vert
        for (int i = 0; i < 2; i++) {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            histograms[i] = new BarChart<>(xAxis, yAxis);
            histograms[i].setBarGap(0);
            histograms[i].setCategoryGap(0);
            yAxis.setLabel("Percentage of Properties in value group (%)");
            xAxis.setLabel("Property Value ($)");
            histograms[i].setPrefSize(1000, 1000);
            histograms[i].setAnimated(false);
        }
    }

    @Override
    void update(List<Data>[] dataset, Statistics[] stats) {

        histogramVBox.getChildren().clear();

        int n;
        if (dataset[1].isEmpty()) { //if one dataset is empty
            n = 1;
        } else {
            n = 2;
        }
        for (int i = 0; i < n; i++) {
            double value = 100000.0;

            Map<Double, Integer> histogramData = new LinkedHashMap<>();
            for (int j = 1; j < 13; j += 1) {
                histogramData.put(value * j, 0);
            }
            //double last1 = (double) value * 13;
            boolean added1 = false;
            for (Data property : dataset[0]) {

                for (Map.Entry mapElement : histogramData.entrySet()) {
                    double key = (double) mapElement.getKey();
                    if ((double) property.getValue() <= key) {
                        histogramData.put(key, ((int) mapElement.getValue() + 1));
                        added1 = true;
                        break;
                    }

                }
                if (!added1) {
                    Map.Entry lastEntry = null;
                    for (Map.Entry mapElement : histogramData.entrySet()) {
                        lastEntry = mapElement;
                    }
                    histogramData.put((double) lastEntry.getKey(), ((int) lastEntry.getValue() + 1));

                }
                added1 = false;
            }

            int total1 = dataset[0].size();

            BarChart.Series set1 = new BarChart.Series<>();

            for (Map.Entry mapElement : histogramData.entrySet()) {
                set1.getData().add(new BarChart.Data(String.valueOf(mapElement.getKey()), (int) ((int) (mapElement.getValue()) * 100 / total1)));
            }
            histograms[i].setTitle("Histogram for File "+Integer.toString(i+1));
            histograms[i].setData(FXCollections.observableArrayList(set1));
            histogramVBox.getChildren().add(new Label( //add label for table
                    "Histogram for file " + Integer.toString(i+1)));
            histogramVBox.getChildren().add(histograms[i]); //add table
        }
    }
}
