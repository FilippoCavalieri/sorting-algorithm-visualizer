package com.example.sortingalgorithmvisualizator;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.lang.reflect.InvocationTargetException;
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
    private Label arraySizeValueLabel, arrayRangeValueLabel;
    @FXML
    private Button sortButton, resetButton;

    private static final int DEFAULT_ARRAY_SIZE = 50;
    private static final int DEFAULT_ARRAY_RANGE = 100;
    private static final long DEFAULT_DELAY = 100;

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
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), new Random().nextInt(1, valueRange + 1)));
        }

        barChart.getData().add(series);
    }

    public static void delay() {
        try {
            Thread.sleep(DEFAULT_DELAY);
        } catch (InterruptedException ignored) {

        }
    }

    @FXML
    public void handleReset() {
        barChart.getData().clear();
        fillArray();
        sortButton.setDisable(false);
    }

    @FXML
    public void handleSort() {
        resetButton.setDisable(true);
        sortButton.setDisable(true);
        try{
            String sortingAlgorithm = sortingAlgorithmChoice.getSelectionModel().getSelectedItem().toString();
            System.out.println(sortingAlgorithm);
            switch (sortingAlgorithm) {
                case "Selection sort" -> {
                    System.out.println(barChart.getData().get(0).getData().toString());
                    SortingAlgorithms.selectionSort(barChart.getData().get(0).getData());
                }
                case "Bubble sort" -> SortingAlgorithms.bubbleSort(barChart.getData().get(0).getData());
                case "Insertion sort" -> SortingAlgorithms.insertionSort(barChart.getData().get(0).getData());
                case "Quick sort" -> SortingAlgorithms.quickSort(barChart.getData().get(0).getData());
                case "Merge sort" -> SortingAlgorithms.mergeSort(barChart.getData().get(0).getData());
            }
        }
        catch (Exception e){
            showNoSelectedAlgorithmAlert();
        }
        finally{
            resetButton.setDisable(false);
        }
    }

    public void showNoSelectedAlgorithmAlert(){
        sortButton.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No algorithm selected");
        alert.setContentText("Please select an algorithm in the check box.");
        alert.showAndWait();

    }
    public static class SortingAlgorithms {
        private static void swap(ObservableList<XYChart.Data<String, Integer>> list, int index1, int index2) {
            Integer tmp = list.get(index1).getYValue();
            list.get(index1).setYValue(list.get(index2).getYValue());
            list.get(index2).setYValue(tmp);
        }

        private static int foundMax(ObservableList<XYChart.Data<String, Integer>> list, int range) {
            int maxIndex = 0; //Hp: first element is the max
            for (int i = 1; i < range; ++i) {
                if (list.get(i).getYValue().compareTo(list.get(maxIndex).getYValue()) > 0) {
                    delay();
                    maxIndex = i;
                }
            }
            return maxIndex;
        }

        public static void selectionSort(ObservableList<XYChart.Data<String, Integer>> list) {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    int maxIndex;
                    for (int listSize = list.size(); listSize > 1; listSize--) {
                        maxIndex = foundMax(list, listSize);
                        if (maxIndex < listSize - 1) {
                            delay();
                            swap(list, maxIndex, listSize - 1);
                        }
                    }
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }

        public static void bubbleSort(ObservableList<XYChart.Data<String, Integer>> list) {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    boolean ordered = false;
                    for (int listSize = list.size(); listSize > 1 && !ordered; listSize--) {
                        ordered = true; //Hp: the list is ordered
                        for (int i = 0; i < listSize - 1; i++) {
                            if (list.get(i).getYValue().compareTo(list.get(i + 1).getYValue()) > 0) {
                                delay();
                                swap(list, i, i + 1);
                                ordered = false;
                            }
                        }
                    }
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }

        public static void insertionSort(ObservableList<XYChart.Data<String, Integer>> list) {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {

                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }

        public static void quickSort(ObservableList<XYChart.Data<String, Integer>> list) {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {

                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }

        public static void mergeSort(ObservableList<XYChart.Data<String, Integer>> list) {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {

                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
