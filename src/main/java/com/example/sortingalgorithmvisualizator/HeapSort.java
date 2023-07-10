package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Implements the heap sort algorithm.
 *
 * @see "https://en.wikipedia.org/wiki/Heapsort"
 * @see "https://www.geeksforgeeks.org/heap-sort/"
 */
public class HeapSort extends SortingAlgorithm {

    /**
     * Sorts the given list.
     *
     * @param list the ObservableList we want to sort
     * @param elementsColor the default color of the list's elements
     */
    public static void heapSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        int n = list.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i, elementsColor);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            heapify(list, i, 0, elementsColor);
            list.get(i).getNode().setStyle(LIGHT_LIME);
        }
        list.get(0).getNode().setStyle(LIGHT_LIME);
    }

    /**
     * Transforms recursively the given list in a max heap data structure.
     *
     * @param list the ObservableList to transform
     * @param n the size of the list
     * @param i the index of the root
     * @param elementsColor the default color of the list's elements
     */
    private static void heapify(ObservableList<XYChart.Data<String, Number>> list, int n, int i, String elementsColor) {
        int largest = i; //At the beginning the root contains the max value
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        list.get(largest).getNode().setStyle(YELLOW);

        // If the right child is larger than the root
        if (leftChild < n && list.get(leftChild).getYValue().intValue() > list.get(largest).getYValue().intValue()) {
            list.get(largest).getNode().setStyle(elementsColor);
            largest = leftChild;
            list.get(largest).getNode().setStyle(YELLOW);
        }

        // If the right child is larger than the root
        if (rightChild < n && list.get(rightChild).getYValue().intValue() > list.get(largest).getYValue().intValue()) {
            list.get(largest).getNode().setStyle(elementsColor);
            largest = rightChild;
            list.get(largest).getNode().setStyle(YELLOW);
        }

        // If the largest isn't the root
        if (largest != i) {
            delay();
            swap(list, i, largest);
            list.get(i).getNode().setStyle(elementsColor);
            list.get(largest).getNode().setStyle(YELLOW);
            heapify(list, n, largest, elementsColor);
        }
        list.get(largest).getNode().setStyle(elementsColor);
    }
}
