package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Implements the quick sort algorithm. It's a recursive algorithm.
 *
 * @see "https://en.wikipedia.org/wiki/Quicksort"
 */
public class QuickSort extends SortingAlgorithm {

    /**
     * Sorts the given list.
     *
     * @param list the ObservableList we want to sort
     * @param elementsColor the default color of the list's element
     */
    public static void quickSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        quickSortRec(list, 0, list.size() - 1, elementsColor);
    }

    /**
     * Sorts recursively the given list.
     *
     * @param list the Observable list we want to sort
     * @param first the index of the first element of the currently considered sublist
     * @param last the index of the last element of the currently considered sublist
     * @param elementsColor the default color of the list's element
     */
    private static void quickSortRec(ObservableList<XYChart.Data<String, Number>> list, int first, int last, String elementsColor) {
        int pivot, i, j, pivotIndex;
        if (first < last) {
            for (int k = first; k <= last; k++) {
                list.get(k).getNode().setStyle(LIGHT_LIME);
            }
            i = first;
            j = last;
            pivotIndex = (first + last) / 2;
            pivot = list.get(pivotIndex).getYValue().intValue();
            list.get(pivotIndex).getNode().setStyle(YELLOW);
            do {
                for (; list.get(i).getYValue().intValue() < pivot; i++)
                    ;
                for (; list.get(j).getYValue().intValue() > pivot; j--)
                    ;

                if (i <= j) {
                    list.get(i).getNode().setStyle(CYAN);
                    list.get(j).getNode().setStyle(CYAN);
                    delay();
                    swap(list, i, j);
                    list.get(i).getNode().setStyle(LIGHT_LIME);
                    list.get(j).getNode().setStyle(LIGHT_LIME);
                    list.get(pivotIndex).getNode().setStyle(YELLOW);
                    i++;
                    j--;
                }
            } while (i <= j);
            for (int k = first; k <= last; k++) {
                list.get(k).getNode().setStyle(elementsColor);
            }
            quickSortRec(list, first, j, elementsColor);
            quickSortRec(list, i, last, elementsColor);
        }
    }
}
