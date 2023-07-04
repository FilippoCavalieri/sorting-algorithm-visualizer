package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.*;

public class MainController {
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Slider arraySizeSlider, arrayRangeSlider;
    @FXML
    private ComboBox sortingAlgorithmChoice;
    @FXML
    private Button sortButton, resetButton;
    @FXML
    private Label arraySizeValueLabel, arrayRangeValueLabel;
    private final int DEFAULT_ARRAY_SIZE = 50;
    private final int DEFAULT_ARRAY_RANGE = 100;
    private int barsNumber, valueRange;

    @FXML
    public void initialize() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        arraySizeSlider.setValue(DEFAULT_ARRAY_SIZE);
        arraySizeValueLabel.setText(Integer.toString(DEFAULT_ARRAY_SIZE));
        arrayRangeSlider.setValue(DEFAULT_ARRAY_RANGE);
        arrayRangeValueLabel.setText(Integer.toString(DEFAULT_ARRAY_RANGE));

        barsNumber = DEFAULT_ARRAY_SIZE;
        valueRange = DEFAULT_ARRAY_RANGE;

        initializeBarChart();
        fillArray();

        arraySizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            barsNumber = (int) arraySizeSlider.getValue();
            arraySizeValueLabel.setText(Integer.toString(barsNumber));
        });

        arrayRangeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueRange = (int) arrayRangeSlider.getValue();
            arrayRangeValueLabel.setText(Integer.toString(valueRange));
        });

        sortingAlgorithmChoice.getItems().addAll("Selection sort", "Bubble sort", "Insertion sort", "Quick sort", "Merge sort");
    }

    public void initializeBarChart() {
        barChart.setBarGap(0);
    }

    public void fillArray() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < barsNumber; i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), new Random().nextInt(valueRange)));
        }

        barChart.getData().add(series);
    }

    @FXML
    public void handleReset() {
        barChart.getData().clear();
        fillArray();
    }

    @FXML
    public void handleSort() {
        String sortingAlgorithm = sortingAlgorithmChoice.getSelectionModel().getSelectedItem().toString();
        switch (sortingAlgorithm) {
            case "Selection sort" -> SortingAlgorithms.selectionSort(barChart.getData().get(0).getData());
            case "Bubble sort" -> SortingAlgorithms.bubbleSort(barChart.getData().get(0).getData());
            case "Insertion sort" -> SortingAlgorithms.insertionSort(barChart.getData().get(0).getData());
            case "Quick sort" -> SortingAlgorithms.quickSort(barChart.getData().get(0).getData());
            case "Merge sort" -> SortingAlgorithms.mergeSort(barChart.getData().get(0).getData());
        }
    }

    public class SortingAlgorithms {
        private static void swap(ObservableList<XYChart.Data<String, Integer>> list, int index1, int index2) {
            Integer tmp = list.get(index1).getYValue();
            list.get(index1).setYValue(list.get(index2).getYValue());
            list.get(index2).setYValue(tmp);
        }

        private static int foundMax(ObservableList<XYChart.Data<String, Integer>> list, int n) {
            int p = 0; //Hp: first element is the max
            for (int i = 1; i < n; ++i) {
                if (list.get(i).getYValue().compareTo(list.get(p).getYValue()) > 0)
                    p = i;
            }
            return p;
        }

        public static void selectionSort(ObservableList<XYChart.Data<String, Integer>> list) {
            int p;
            for(int listSize = list.size(); listSize > 1; listSize--) {
                p = foundMax(list, listSize);
                if (p < listSize - 1)
                    swap(list, p, listSize - 1);
            }
        }

        public static void bubbleSort(ObservableList<XYChart.Data<String, Integer>> list) {

        }

        public static void insertionSort(ObservableList<XYChart.Data<String, Integer>> list) {

        }

        public static void quickSort(ObservableList<XYChart.Data<String, Integer>> list) {

        }

        public static void mergeSort(ObservableList<XYChart.Data<String, Integer>> list) {

        }
    }
}
