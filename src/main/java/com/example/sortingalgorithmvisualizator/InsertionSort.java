package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Implements the insertion sort algorithm.
 *
 * @see "https://en.wikipedia.org/wiki/Insertion_sort"
 */
public class InsertionSort extends SortingAlgorithm {

    /**
     * Sorts the given list.
     *
     * @param list the ObservableList we want to sort
     * @param elementsColor the default color of the list's elements
     */
    public static void insertionSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        for (int i = 1; i < list.size(); i++) {
            insertMin(list, i);
        }
    }

    /**
     * Places the element of the list at the given index in the "right" position (where with "right" position we mean
     * the position such that the elements of the sublist which goes from index 0 to the given index are ordered).
     *
     * @param list the ObservableList to which belongs the element we want to put in the "right" position
     * @param lastPos the index of the element we want to put in the "right" position
     */
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
}
