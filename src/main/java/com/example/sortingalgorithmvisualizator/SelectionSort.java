package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Implements the selection sort algorithm.
 *
 * @see "https://en.wikipedia.org/wiki/Selection_sort"
 */
public class SelectionSort extends SortingAlgorithm {

    /**
     * Sorts the given list.
     *
     * @param list the ObservableList we want to sort
     * @param elementsColor the default color of the list's elements
     */
    public static void selectionSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        int maxIndex;
        for (int listSize = list.size(); listSize > 1; listSize--) {
            maxIndex = findMax(list, listSize, elementsColor);
            if (maxIndex < listSize - 1) {
                delay();
                swap(list, maxIndex, listSize - 1);
                list.get(maxIndex).getNode().setStyle(elementsColor);
            }
            list.get(listSize - 1).getNode().setStyle(LIGHT_LIME);
        }
        list.get(0).getNode().setStyle(LIGHT_LIME);
    }

    /**
     * Finds the index of the max value inside a sublist of the given list.
     *
     * @param list the ObservableList of which we want to discover the max value
     * @param range determines the portion of the list we want to examine
     * @param elementsColor the default color of the list's elements
     *
     * @return the index of the max value
     */
    private static int findMax(ObservableList<XYChart.Data<String, Number>> list, int range, String elementsColor) {
        int maxIndex = 0; // Hp: first element is the max
        list.get(maxIndex).getNode().setStyle(YELLOW);
        for (int i = 1; i < range; ++i) {
            list.get(i).getNode().setStyle(CYAN);
            delay();
            if (list.get(i).getYValue().intValue() > list.get(maxIndex).getYValue().intValue()) {
                list.get(maxIndex).getNode().setStyle(elementsColor);
                delay();
                maxIndex = i;
                list.get(maxIndex).getNode().setStyle(YELLOW);
            } else {
                list.get(i).getNode().setStyle(elementsColor);
            }
        }
        return maxIndex;
    }
}
