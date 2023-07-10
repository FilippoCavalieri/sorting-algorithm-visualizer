package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Implements the bubble sort algorithm.
 *
 * @see "https://en.wikipedia.org/wiki/Bubble_sort"
 */
public class BubbleSort extends SortingAlgorithm {

    /**
     * Sorts the given list.
     *
     * @param list the ObservableList we want to sort
     * @param elementsColor the default color of the list's elements
     */
    public static void bubbleSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        boolean ordered = false;
        int listSize = list.size();
        for (; listSize > 1 && !ordered; listSize--) {
            ordered = true; //Hp: the list is ordered
            for (int i = 0; i < listSize - 1; i++) {
                list.get(i).getNode().setStyle(CYAN);
                list.get(i + 1).getNode().setStyle(CYAN);
                delay();
                if (list.get(i).getYValue().intValue() > list.get(i + 1).getYValue().intValue()) {
                    swap(list, i, i + 1);
                    ordered = false;
                }
                list.get(i).getNode().setStyle(elementsColor);
                list.get(i + 1).getNode().setStyle(elementsColor);
            }
            list.get(listSize - 1).getNode().setStyle(LIGHT_LIME);
        }
        for (int i = listSize; i >= 0; i--) {
            delay();
            list.get(i).getNode().setStyle(LIGHT_LIME);
        }
    }
}
