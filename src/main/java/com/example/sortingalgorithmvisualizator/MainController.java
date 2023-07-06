package com.example.sortingalgorithmvisualizator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.*;

public class MainController {

    @FXML
    private BorderPane pane;
    @FXML
    private Slider arraySizeSlider, arrayRangeSlider;
    @FXML
    private ComboBox<String> sortingAlgorithmChoice;
    @FXML
    private Label arraySizeValueLabel, arrayRangeValueLabel;
    @FXML
    private Button sortButton, resetButton;
    @FXML
    private Spinner<Integer> delayPicker;

    private static final int DEFAULT_ARRAY_SIZE = 50;
    private static final int DEFAULT_ARRAY_RANGE = 100;
    private static final int MIN_DELAY = 0;
    private static final int DEFAULT_DELAY = 80;
    private static final int MAX_DELAY = 1000;
    private static final int RESET_DELAY = 500;

    private BarChart<String, Number> barChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private int barsNumber, valueRange;
    private static long currentDelay;
    private String sortingAlgorithm;

    @FXML
    public void initialize() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);

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

        sortingAlgorithmChoice.setItems(FXCollections.observableArrayList("Selection sort", "Bubble sort", "Insertion sort", "Quick sort", "Merge sort"));

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_DELAY, MAX_DELAY);
        spinnerValueFactory.setValue(DEFAULT_DELAY);
        delayPicker.setValueFactory(spinnerValueFactory);
        delayPicker.valueProperty().addListener((observable, oldValue, newValue) -> currentDelay = delayPicker.getValue());
    }

    public void initializeBarChart() {
        barChart.setBarGap(0);
    }

    public void fillArray() {
        XYChart.Series series = new XYChart.Series();
        barChart = new BarChart(xAxis, yAxis);
        for (int i = 0; i < barsNumber; i++) {
            series.getData().add(new XYChart.Data(Integer.toString(i), new Random().nextInt(barsNumber) + 1));
        }
        barChart.getData().add(series);
        pane.setCenter(barChart);
    }

    public static void delay() {
        try {
            Thread.sleep(currentDelay);
        } catch (InterruptedException ignored) {

        }
    }

    @FXML
    public void handleReset() throws InterruptedException {
        barChart.getData().clear();
        fillArray();
        sortButton.setDisable(false);
        Thread.sleep(RESET_DELAY);
    }

    @FXML
    public void handleSort() {
        resetButton.setDisable(true);
        sortButton.setDisable(true);
        try {
            sortingAlgorithm = sortingAlgorithmChoice.getSelectionModel().getSelectedItem().toString();
        } catch (Exception e) {
            showNoSelectedAlgorithmAlert();
        }
        Task task = new Task() {
            @Override
            protected Object call() {
                try {
                    switch (sortingAlgorithm) {
                        case "Selection sort" -> SortingAlgorithms.selectionSort(barChart.getData().get(0).getData());
                        case "Bubble sort" -> SortingAlgorithms.bubbleSort(barChart.getData().get(0).getData());
                        case "Insertion sort" -> SortingAlgorithms.insertionSort(barChart.getData().get(0).getData());
                        case "Quick sort" -> SortingAlgorithms.quickSort(barChart.getData().get(0).getData());
                        case "Merge sort" -> SortingAlgorithms.mergeSort(barChart.getData().get(0).getData());
                        default -> throw new Exception();
                    }
                } catch (NullPointerException ignored) {

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    resetButton.setDisable(false);
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public void showNoSelectedAlgorithmAlert() {
        sortButton.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No algorithm selected");
        alert.setContentText("Please select an algorithm in the check box.");
        alert.showAndWait();
    }

    public static class SortingAlgorithms {
        private static void swap(ObservableList<XYChart.Data<String, Number>> list, int index1, int index2) {
            Number tmp = list.get(index1).getYValue();
            list.get(index1).setYValue(list.get(index2).getYValue());
            list.get(index2).setYValue(tmp);
        }

        private static int findMax(ObservableList<XYChart.Data<String, Number>> list, int range) {
            int maxIndex = 0; //Hp: first element is the max
            for (int i = 1; i < range; ++i) {
                if (list.get(i).getYValue().intValue() > list.get(maxIndex).getYValue().intValue()) {
                    delay();
                    maxIndex = i;
                }
            }
            return maxIndex;
        }

        public static void selectionSort(ObservableList<XYChart.Data<String, Number>> list) {
            int maxIndex;
            for (int listSize = list.size(); listSize > 1; listSize--) {
                maxIndex = findMax(list, listSize);
                if (maxIndex < listSize - 1) {
                    delay();
                    swap(list, maxIndex, listSize - 1);
                }
            }
        }

        public static void bubbleSort(ObservableList<XYChart.Data<String, Number>> list) {
            boolean ordered = false;
            for (int listSize = list.size(); listSize > 1 && !ordered; listSize--) {
                ordered = true; //Hp: the list is ordered
                for (int i = 0; i < listSize - 1; i++) {
                    if (list.get(i).getYValue().intValue() >= list.get(i + 1).getYValue().intValue()) {
                        delay();
                        swap(list, i, i + 1);
                        ordered = false;
                    }
                }
            }
        }

        private static void insertMin(ObservableList<XYChart.Data<String, Number>> list, int lastPos) {
            int i, lastValue = list.get(lastPos).getYValue().intValue();
            for (i = lastPos - 1; i >= 0 && lastValue < list.get(i).getYValue().intValue(); i--) {
                delay();
                swap(list, i + 1, i);
            }
            list.get(i + 1).setYValue(lastValue);
        }

        public static void insertionSort(ObservableList<XYChart.Data<String, Number>> list) {
            for (int i = 1; i < list.size(); i++) {
                insertMin(list, i);
            }
        }

        private static void quickSortRec(ObservableList<XYChart.Data<String, Number>> list, int first, int last) {
            int i, j, pivot;
            if (first < last) {
                i = first;
                j = last;
                pivot = list.get((first + last) / 2).getYValue().intValue();

                do {
                    for (; list.get(i).getYValue().intValue() < pivot; i++)
                        ;
                    for (; list.get(j).getYValue().intValue() > pivot; j--)
                        ;

                    if (i <= j) {
                        delay();
                        swap(list, i, j);
                        i++;
                        j--;
                    }
                } while (i <= j);
                quickSortRec(list, first, j);
                quickSortRec(list, i, last);
            }
        }

        public static void quickSort(ObservableList<XYChart.Data<String, Number>> list) {
            quickSortRec(list, 0, list.size() - 1);
        }

        private static void merge(ObservableList<XYChart.Data<String, Number>> list, int begFirst, int begSecond,
                                  int endSecond) {
            ObservableList<XYChart.Data<String, Number>> tmp = FXCollections.observableArrayList(list);
            int i = begFirst, j = begSecond, k = begFirst;
            while(i <= begSecond - 1 && j <= endSecond){
                if(tmp.get(i).getYValue().intValue() > tmp.get(j).getYValue().intValue()){
                    list.get(k).setYValue(tmp.get(i).getYValue().intValue());
                    i++;
                }
                else{
                    list.get(k).setYValue(tmp.get(j).getYValue().intValue());
                    j++;
                }
                k++;
            }
            while(i <= begSecond - 1){
                list.get(k).setYValue(tmp.get(i).getYValue().intValue());
                i++;
                k++;
            }
            while(j <= endSecond){
                list.get(k).setYValue(tmp.get(i).getYValue().intValue());
                i++;
                k++;
            }
        }

        private static void mergeSortRec(ObservableList<XYChart.Data<String, Number>> list, int first, int last) {
            int mid;
            if (first < last) {
                mid = (first + last) / 2;
                mergeSortRec(list, first, mid);
                mergeSortRec(list, mid + 1, last);
                merge(list, first, mid + 1, last);
            }
        }

        public static void mergeSort(ObservableList<XYChart.Data<String, Number>> list) {
            mergeSortRec(list, 0, list.size() - 1);
        }
    }
}
