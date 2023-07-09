package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class InsertionSort extends SortingAlgorithm {
    private static void insertMin(ObservableList<XYChart.Data<String, Number>> list, int lastPos) {
        int i, lastValue = list.get(lastPos).getYValue().intValue();
        for (i = lastPos - 1; i >= 0 && lastValue < list.get(i).getYValue().intValue(); i--) {
            list.get(i).getNode().setStyle(CYAN);
            list.get(i + 1).getNode().setStyle(CYAN);
            delay();
            swap(list, i + 1, i);
            list.get(i).getNode().setStyle(LIGHT_LIME);
            list.get(i + 1).getNode().setStyle(LIGHT_LIME);
        }
        list.get(i + 1).setYValue(lastValue);
    }

    public static void insertionSort(ObservableList<XYChart.Data<String, Number>> list) {
        for (int i = 1; i < list.size(); i++) {
            insertMin(list, i);
        }
    }
}
