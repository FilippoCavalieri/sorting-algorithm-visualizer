package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class HeapSort extends SortingAlgorithm {
    public static void heapSort(ObservableList<XYChart.Data<String, Number>> list) {
        int n = list.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            reorder(list, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i);
            reorder(list, i, 0);
        }
    }

    private static void reorder(ObservableList<XYChart.Data<String, Number>> list, int n, int i) {
        int largest = i; // Inizialmente il più grande è il nodo radice
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        list.get(largest).getNode().setStyle(YELLOW);

        // Se il figlio sinistro è più grande della radice
        if (leftChild < n && list.get(leftChild).getYValue().intValue() > list.get(largest).getYValue().intValue()) {
            list.get(largest).getNode().setStyle(RUBY);
            largest = leftChild;
            list.get(largest).getNode().setStyle(YELLOW);
        }

        // Se il figlio destro è più grande del più grande finora
        if (rightChild < n && list.get(rightChild).getYValue().intValue() > list.get(largest).getYValue().intValue()) {
            list.get(largest).getNode().setStyle(RUBY);
            largest = rightChild;
            list.get(largest).getNode().setStyle(YELLOW);
        }

        // Se il più grande non è la radice
        if (largest != i) {
            delay();
            swap(list, i, largest);
            list.get(largest).getNode().setStyle(RUBY);
            reorder(list, n, largest);
        }
        list.get(largest).getNode().setStyle(RUBY);
    }
}
