package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.awt.image.ColorConvertOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements the radix sort algorithm.
 */
public class RadixSort extends SortingAlgorithm {
    public static void radixSort(ObservableList<XYChart.Data<String, Number>> list, String elementsColor)
    {
        int max = getMax(list);
        String[] colors = new String[]{CYAN, YELLOW, LIGHT_LIME, RED };
        for (int exp = 1, i = 0; max / exp > 0; exp *= 10, i++)
            countSort(list, exp, colors[i]);

    }

    static int getMax(ObservableList<XYChart.Data<String, Number>> list)
    {
        int max = list.get(0).getYValue().intValue();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getYValue().intValue() > max) {
                max = list.get(i).getYValue().intValue();
            }
        }
        return max;
    }

    // A function to do counting sort of list according to the digit represented by exp.
    private static void countSort(ObservableList<XYChart.Data<String, Number>> list, int exp, String currentColor)
    {
        List<Number> tmp = new ArrayList<>(list.size());
        for (XYChart.Data<String, Number> data : list) {
            tmp.add(data.getYValue());
        }

        int i;
        int[] count = new int[MainController.valueRange];
        Arrays.fill(count, 0);

        // Store count of occurrences in count
        for (i = 0; i < tmp.size(); i++) {
            int pos = (tmp.get(i).intValue() / exp) % 10;
            count[pos] += + 1;
        }

        //Change count[i] to the sum of the occurrences of the elements less or equal then count[i] in the original list
        for(i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Order the list
        for (i = list.size() - 1; i >= 0; i--) {
            list.get(i).getNode().setStyle(currentColor);
            delay();
            list.get(count[(tmp.get(i).intValue() / exp) % 10] - 1).setYValue(tmp.get(i).intValue());
            count[(tmp.get(i).intValue() / exp) % 10]--;
        }
    }
}
