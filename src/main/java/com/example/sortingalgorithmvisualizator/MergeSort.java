package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class MergeSort extends SortingAlgorithm {
    public static void mergeSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        mergeSortRec(list, 0, list.size() - 1, elementsColor);
    }

    private static void mergeSortRec(ObservableList<XYChart.Data<String, Number>> list, int first, int last,
                                     String elementsColor) {
        int mid;
        if (first < last) {
            mid = (first + last) / 2;

            mergeSortRec(list, first, mid, elementsColor);
            mergeSortRec(list, mid + 1, last, elementsColor);

            mergeOperation(list, first, mid, last, elementsColor);
        }
    }

    private static void mergeOperation(ObservableList<XYChart.Data<String, Number>> list, int first, int mid,
                                       int last, String elementsColor) {
        int i = first, j = mid + 1, k = first;
        List<Number> tmp = new ArrayList<>();

        for (XYChart.Data<String, Number> data : list) {
            tmp.add(data.getYValue());
        }
        for(int y = first; y <= last; y++){
            list.get(y).getNode().setStyle(LIGHT_LIME);
        }
        while (i <= mid && j <= last) {
            if ((int) tmp.get(i) < (int) tmp.get(j)) {
                list.get(k).getNode().setStyle(CYAN);
                delay();
                list.get(k).setYValue(tmp.get(i));
                list.get(k).getNode().setStyle(LIGHT_LIME);
                i++;
                k++;
            } else {
                list.get(k).getNode().setStyle(CYAN);
                delay();
                list.get(k).setYValue(tmp.get(j));
                list.get(k).getNode().setStyle(LIGHT_LIME);
                j++;
                k++;
            }
        }
        for (; i <= mid; i++) {
            list.get(k).getNode().setStyle(CYAN);
            delay();
            list.get(k).setYValue(tmp.get(i));
            list.get(k).getNode().setStyle(LIGHT_LIME);
            k++;
        }
        for (; j <= last; j++) {
            list.get(k).getNode().setStyle(CYAN);
            delay();
            list.get(k).setYValue(tmp.get(j));
            list.get(k).getNode().setStyle(LIGHT_LIME);
            k++;
        }
        for(int y = first; y <= last; y++){
            list.get(y).getNode().setStyle(elementsColor);
        }
    }
}
