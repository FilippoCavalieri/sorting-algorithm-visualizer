package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class CocktailSort extends SortingAlgorithm{
    private static boolean checkIfSwapped(ObservableList<XYChart.Data<String, Number>> list, int i) {
        boolean ret = false;
        list.get(i).getNode().setStyle(CYAN);
        list.get(i + 1).getNode().setStyle(CYAN);
        delay();
        if (list.get(i).getYValue().intValue() > list.get(i + 1).getYValue().intValue()) {
            swap(list, i, i + 1);
            ret = true;
        }
        list.get(i).getNode().setStyle(RUBY);
        list.get(i + 1).getNode().setStyle(RUBY);
        return ret;
    }
    public static void cocktailSort(ObservableList<XYChart.Data<String, Number>> list) {
        boolean swapped;
        int start = 0;
        int end = list.size() - 1;

        do {
            swapped = false;

            // Scorrimento verso destra
            int i;
            for (i = start; i < end; i++) {
                swapped = checkIfSwapped(list, i);
            }
            list.get(i).getNode().setStyle(LIGHT_LIME);

            if (swapped) {
                swapped = false;
                end--;

                // Scorrimento verso sinistra
                for (i = end - 1; i >= start; i--) {
                    swapped = checkIfSwapped(list, i);
                }
                list.get(i + 1).getNode().setStyle(LIGHT_LIME);
                start++;
            }
        } while (swapped);

        for(int i = start; i <= end; i++) {
            list.get(i).getNode().setStyle(LIGHT_LIME);
        }
    }
}
