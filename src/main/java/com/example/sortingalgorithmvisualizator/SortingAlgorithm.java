package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import static com.example.sortingalgorithmvisualizator.MainController.currentDelay;

/**
 * An abstract class representing a generic sorting algorithm.
 */
public abstract class SortingAlgorithm {
    static final String LIGHT_LIME = "-fx-background-color: #99FF99";
    static final String CYAN = "-fx-background-color: #3399FF";
    static final String YELLOW = "-fx-background-color: #FFFF00";
    static final String RED = "-fx-background-color: #F90C0C";

    /**
     * Swaps the element at the specified indexes of the given list .
     * @param list the ObservableList object whose elements have to be swapped
     * @param index1 the index of the first element to be swapped
     * @param index2 the index of the second element to be swapped
     */
    static void swap(ObservableList<XYChart.Data<String, Number>> list, int index1, int index2) {
        Number tmp = list.get(index2).getYValue();
        list.get(index2).setYValue(list.get(index1).getYValue());
        list.get(index1).setYValue(tmp);
    }

    /**
     * Sleeps the current thread for the number of milliseconds specified in the controller class.
     * It's needed to visualize on the screen the swap between list's elements.
     */
    static void delay() {
        try {
            Thread.sleep(currentDelay);
        } catch (InterruptedException ignored) {

        }
    }
}
