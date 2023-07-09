package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import static com.example.sortingalgorithmvisualizator.MainController.currentDelay;

public abstract class SortingAlgorithm {
    static final String RUBY = "-fx-background-color: #CC0066";
    static final String LIGHT_LIME = "-fx-background-color: #99FF99";
    static final String CYAN = "-fx-background-color: #3399FF";
    static final String YELLOW = "-fx-background-color: #FFFF00";
    static void swap(ObservableList<XYChart.Data<String, Number>> list, int index1, int index2) {
        Number tmp = list.get(index2).getYValue();
        list.get(index2).setYValue(list.get(index1).getYValue());
        list.get(index1).setYValue(tmp);
    }
    static void delay() {
        try {
            Thread.sleep(currentDelay);
        } catch (InterruptedException ignored) {

        }
    }
}
