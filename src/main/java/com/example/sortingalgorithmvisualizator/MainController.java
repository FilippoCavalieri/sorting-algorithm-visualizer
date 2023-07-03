package com.example.sortingalgorithmvisualizator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.*;

public class MainController {
    @FXML private BarChart<String, Integer> barChart;
    @FXML
    void onClick(ActionEvent event) {

    }

    public void handleNewArray() throws InterruptedException {
        // Qui sarebbe necessario inserire il valore dato dallo slider
        int[] elements = new int[12];
        Random rnd = new Random();
        for (int i = 0; i < elements.length; i++) {
            elements[i] = rnd.nextInt(101);
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        // Create a XYChart.Data object for each element. Add it to the series.
        for (int i = 0; i < elements.length; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(elements[i]),elements[i]));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
