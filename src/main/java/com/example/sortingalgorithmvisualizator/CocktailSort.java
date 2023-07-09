package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class CocktailSort extends SortingAlgorithm{
    public static void cocktailSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        boolean swapped;
        int start = 0;
        int end = list.size() - 1;

        do {
            swapped = false;

            //Right sliding
            int i;
            for (i = start; i < end; i++) {
                swapped = checkIfSwapped(list, i, elementsColor);
            }
            list.get(i).getNode().setStyle(LIGHT_LIME);

            if (swapped) {
                swapped = false;
                end--;

                //Left sliding
                for (i = end - 1; i >= start; i--) {
                    swapped = checkIfSwapped(list, i, elementsColor);
                }
                list.get(i + 1).getNode().setStyle(LIGHT_LIME);
                start++;
            }
        } while (swapped);

        for(int i = start; i <= end; i++) {
            list.get(i).getNode().setStyle(LIGHT_LIME);
        }
    }

    private static boolean checkIfSwapped(ObservableList<XYChart.Data<String, Number>> list, int i,
                                          String elementsColor) {
        boolean ret = false;
        list.get(i).getNode().setStyle(CYAN);
        list.get(i + 1).getNode().setStyle(CYAN);
        delay();
        if (list.get(i).getYValue().intValue() > list.get(i + 1).getYValue().intValue()) {
            swap(list, i, i + 1);
            ret = true;
        }
        list.get(i).getNode().setStyle(elementsColor);
        list.get(i + 1).getNode().setStyle(elementsColor);
        return ret;
    }
}
