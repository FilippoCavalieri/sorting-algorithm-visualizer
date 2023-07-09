package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class SelectionSort extends SortingAlgorithm {
    private static int findMax(ObservableList<XYChart.Data<String, Number>> list, int range) {
        int maxIndex = 0; //Hp: first element is the max
        list.get(maxIndex).getNode().setStyle(YELLOW);
        for (int i = 1; i < range; ++i) {
            list.get(i).getNode().setStyle(CYAN);
            delay();
            if (list.get(i).getYValue().intValue() > list.get(maxIndex).getYValue().intValue()) {
                list.get(maxIndex).getNode().setStyle(RUBY);
                delay();
                maxIndex = i;
                list.get(maxIndex).getNode().setStyle(YELLOW);
            }
            else{
                list.get(i).getNode().setStyle(RUBY);
            }
        }
        return maxIndex;
    }

    public static void selectionSort(ObservableList<XYChart.Data<String, Number>> list) {
        int maxIndex;
        for (int listSize = list.size(); listSize > 1; listSize--) {
            maxIndex = findMax(list, listSize);
            if (maxIndex < listSize - 1) {
                delay();
                swap(list, maxIndex, listSize - 1);
                list.get(maxIndex).getNode().setStyle(RUBY);
            }
            list.get(listSize - 1).getNode().setStyle(LIGHT_LIME);
        }
        list.get(0).getNode().setStyle(LIGHT_LIME);
    }
}
