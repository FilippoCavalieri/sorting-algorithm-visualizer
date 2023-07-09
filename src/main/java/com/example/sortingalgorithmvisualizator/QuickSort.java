package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class QuickSort extends SortingAlgorithm {
    private static void quickSortRec(ObservableList<XYChart.Data<String, Number>> list, int first, int last) {
        int pivot, i, j, pivotIndex;
        if (first < last) {
            for(int k = first; k <= last; k++){
                list.get(k).getNode().setStyle(LIGHT_LIME);
            }
            i = first;
            j = last;
            pivotIndex = (first + last) / 2;
            pivot = list.get(pivotIndex).getYValue().intValue();
            list.get(pivotIndex).getNode().setStyle(YELLOW);
            do {
                for (; list.get(i).getYValue().intValue() < pivot; i++);
                for (; list.get(j).getYValue().intValue() > pivot; j--);

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
            for(int k = first; k <= last; k++){
                list.get(k).getNode().setStyle(RUBY);
            }
            quickSortRec(list, first, j);
            quickSortRec(list, i, last);
        }
    }

    public static void quickSort(ObservableList<XYChart.Data<String, Number>> list) {
        quickSortRec(list, 0, list.size() - 1);
    }
}
