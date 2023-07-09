package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class RadixSort extends SortingAlgorithm {
    static int getMax(ObservableList<XYChart.Data<String, Number>> list, int n, String elementsColor)
    {
        int max = list.get(0).getYValue().intValue();
        list.get(max).getNode().setStyle(CYAN);
        for (int i = 1; i < n; i++) {
            list.get(i).getNode().setStyle(CYAN);
            if (list.get(i).getYValue().intValue() > max) {
                max = list.get(i).getYValue().intValue();
                list.get(max).getNode().setStyle(elementsColor);
            }
        }
        return max;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    private static void countSort(ObservableList<XYChart.Data<String, Number>> list, int n, int exp, String elementsColor)
    {
        List<Number> tmp = new ArrayList<>(n);
        for (XYChart.Data<String, Number> data : list) {
            tmp.add(data.getYValue());
        }

        int i;
        List<Number> count = new ArrayList<>(10);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++)
            count.add((tmp.get(i).intValue() / exp) % 10, 1);

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count.add(i, tmp.get(i - 1).intValue());

        // Build the output array
        for (i = n - 1; i >= 0; i--) {
            list.get(count.get((tmp.get(i).intValue() / exp) % 10).intValue() - 1).setYValue(tmp.get(i).intValue());
            count.add((tmp.get(i).intValue() / exp) % 10, -1);
        }
    }

    public static void radixSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor)
    {
        int max = getMax(list, list.size() - 1, elementsColor);

        for (int exp = 1; max / exp > 0; exp *= 10)
            countSort(list, list.size(), exp, elementsColor);
    }

}
