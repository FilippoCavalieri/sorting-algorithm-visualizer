package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class HeapSort extends SortingAlgorithm {
    public static void heapSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        int n = list.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            reorder(list, n, i, elementsColor);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            reorder(list, i, 0, elementsColor);
            list.get(i).getNode().setStyle(LIGHT_LIME);
        }
        list.get(0).getNode().setStyle(LIGHT_LIME);
    }

    private static void reorder(ObservableList<XYChart.Data<String, Number>> list, int n, int i, String elementsColor) {
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
            reorder(list, n, largest, elementsColor);
        }
        list.get(largest).getNode().setStyle(elementsColor);
    }
}
