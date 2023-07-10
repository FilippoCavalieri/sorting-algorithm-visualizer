package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Implements the cocktail sort algorithm.
 *
 * @see "https://it.wikipedia.org/wiki/Shaker_sort"
 * @see "https://www.geeksforgeeks.org/cocktail-sort/"
 */
public class CocktailSort extends SortingAlgorithm {

    /**
     * Sorts the given list.
     *
     * @param list the ObservableList we want to sort
     * @param elementsColor the default color of the list's elements
     */
    public static void cocktailSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor) {
        boolean swapped;
        int start = 0;
        int end = list.size() - 1;

        do {
            swapped = false;

            //Right sliding
            int i;
            for (i = start; i < end; i++) {
                list.get(i).getNode().setStyle(CYAN);
                list.get(i + 1).getNode().setStyle(CYAN);
                delay();
                if (list.get(i).getYValue().intValue() > list.get(i + 1).getYValue().intValue()) {
                    swap(list, i, i + 1);
                    swapped = true;
                }
                list.get(i).getNode().setStyle(elementsColor);
                list.get(i + 1).getNode().setStyle(elementsColor);
            }
            list.get(i).getNode().setStyle(LIGHT_LIME);

            if (swapped) {
                swapped = false;
                end--;

                //Left sliding
                for (i = end - 1; i >= start; i--) {
                    list.get(i).getNode().setStyle(CYAN);
                    list.get(i + 1).getNode().setStyle(CYAN);
                    delay();
                    if (list.get(i).getYValue().intValue() > list.get(i + 1).getYValue().intValue()) {
                        swap(list, i, i + 1);
                        swapped = true;
                    }
                    list.get(i).getNode().setStyle(elementsColor);
                    list.get(i + 1).getNode().setStyle(elementsColor);
                }
                list.get(i + 1).getNode().setStyle(LIGHT_LIME);
                start++;
            }
        } while (swapped);

        for (int i = start; i <= end; i++) {
            delay();
            list.get(i).getNode().setStyle(LIGHT_LIME);
        }
    }
}
