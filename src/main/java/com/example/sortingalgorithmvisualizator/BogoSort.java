package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Random;
import java.util.random.RandomGenerator;

public class BogoSort extends SortingAlgorithm {
    public static void bogoSort(ObservableList<XYChart.Data<String, Number>> list) {
        RandomGenerator random = new Random(Double.doubleToLongBits(Math.random()));

        while (!isSorted(list)) {
            shuffleArray(list, random);
        }
    }

    private static boolean isSorted(ObservableList<XYChart.Data<String, Number>> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getYValue().intValue() < list.get(i - 1).getYValue().intValue()) {
                for(int j = i - 1; j >= 0; j--) {
                    list.get(j).getNode().setStyle(RUBY);
                }
                return false;
            }
            list.get(i - 1).getNode().setStyle(LIGHT_LIME);
            delay();
        }
        return true;
    }

    private static void shuffleArray(ObservableList<XYChart.Data<String, Number>> list, RandomGenerator random) {
        for (int i = list.size() - 1; i >= 0; i--) {
            int j = random.nextInt(i + 1);
            list.get(i).getNode().setStyle(CYAN);
            list.get(j).getNode().setStyle(CYAN);
            delay();
            swap(list, i, j);
            list.get(i).getNode().setStyle(RUBY);
            list.get(j).getNode().setStyle(RUBY);
        }
    }
}
