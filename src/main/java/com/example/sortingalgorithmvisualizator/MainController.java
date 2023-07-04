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
        try {
            String sortingAlgorithm = sortingAlgorithmChoice.getSelectionModel().getSelectedItem().toString();
            System.out.println(sortingAlgorithm);
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    switch (sortingAlgorithm) {
                        case "Selection sort" -> {
                            System.out.println(barChart.getData().get(0).getData().toString());
                            SortingAlgorithms.selectionSort(barChart.getData().get(0).getData());
                        }
                        case "Bubble sort" -> SortingAlgorithms.bubbleSort(barChart.getData().get(0).getData());
                        case "Insertion sort" -> SortingAlgorithms.insertionSort(barChart.getData().get(0).getData());
                        case "Quick sort" ->
                                SortingAlgorithms.quickSort(barChart.getData().get(0).getData(), 0, barChart.getData().get(0).getData().size() - 1);
                        case "Merge sort" ->
                                SortingAlgorithms.mergeSort(barChart.getData().get(0).getData(), 0, barChart.getData().get(0).getData().size() - 1);
                    }
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        } catch (Exception e) {
            showNoSelectedAlgorithmAlert();
        } finally {
            resetButton.setDisable(false);
        }
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

        private static void insMinore(ObservableList<XYChart.Data<String, Integer>> list, int lastpos) {
            int i = lastpos - 1;
            int lastValue = list.get(lastpos).getYValue();
            for (; i >= 0 && lastValue < list.get(i).getYValue(); i--) {
                delay();
                swap(list, i + 1, i);
            }
            list.get(i + 1).setYValue(lastValue);
        }

        public static void selectionSort(ObservableList<XYChart.Data<String, Integer>> list) {
            int maxIndex;
            for (int listSize = list.size(); listSize > 1; listSize--) {
                maxIndex = foundMax(list, listSize);
                if (maxIndex < listSize - 1) {
                    delay();
                    swap(list, maxIndex, listSize - 1);
                }
            }
        }

        public static void bubbleSort(ObservableList<XYChart.Data<String, Integer>> list) {
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
        }

        public static void insertionSort(ObservableList<XYChart.Data<String, Integer>> list) {
            for (int i = 1; i < list.size(); i++) {
                insMinore(list, i);
            }
        }

        public static void quickSort(ObservableList<XYChart.Data<String, Integer>> list, int first, int last) {
            int i, j, pivot;
            if (first < last) {
                i = first;
                j = last;
                pivot = list.get((first + last) / 2).getYValue();
                do {
                    for (; list.get(i).getYValue() < pivot; i++)
                        ;
                    System.out.println(i);
                    System.out.println(j);
                    for (; list.get(j).getYValue() > pivot; j--)
                        ;
                    System.out.println(i);
                    System.out.println(j);
                    if (i <= j) {
                        delay();
                        swap(list, i, j);
                        i++;
                        j--;
                    }
                } while (i <= j);
                quickSort(list, first, j);
                quickSort(list, i, last);
            }
        }

        public static void merge(ObservableList<XYChart.Data<String, Integer>> list, int beg, int mid, int end) {
            int i, j, k;
            int n1 = mid - beg + 1;
            int n2 = end - mid;

            List<XYChart.Data<String, Integer>> leftTmp = new ArrayList<>(n1);
            List<XYChart.Data<String, Integer>> rightTmp = new ArrayList<>(n2);

            for (i = 0; i < n1; i++)
                leftTmp.get(i).setYValue(list.get(beg + 1).getYValue());

            for (j = 0; j < n2; j++)
                rightTmp.get(j).setYValue(list.get(mid + 1 + j).getYValue());

            i = 0; /* initial index of leftTmp */
            j = 0; /* initial index of rightTmp */
            k = beg;  /* initial index of list */

            while (i < n1 && j < n2) {
                if (leftTmp.get(i).getYValue() <= rightTmp.get(j).getYValue()) {
                    list.get(k).setYValue(leftTmp.get(i).getYValue());
                    i++;
                } else {
                    list.get(k).setYValue(rightTmp.get(j).getYValue());
                    j++;
                }
                k++;
            }

            while (i < n1) {
                list.get(k).setYValue(leftTmp.get(i).getYValue());
                i++;
                k++;
            }

            while (j < n2) {
                list.get(k).setYValue(rightTmp.get(j).getYValue());
                j++;
                k++;
            }
        }

        public static void mergeSort(ObservableList<XYChart.Data<String, Integer>> list, int first, int last) {
            if (first < last) {
                int mid = (first + last) / 2;
                mergeSort(list, first, mid);
                mergeSort(list, mid + 1, last);
                merge(list, first, mid, last);
            }
        }
    }
}
