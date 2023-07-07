package com.example.sortingalgorithmvisualizator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class MainController {

    @FXML
    private BorderPane pane;
    @FXML
    private Slider arraySizeSlider, arrayRangeSlider;
    @FXML
    private ComboBox<String> sortingAlgorithmChoice;
    @FXML
    private Label arraySizeValueLabel, arrayRangeValueLabel, timeElapsedLabel, timeElapsedValueLabel, infoLabel;
    @FXML
    private Button sortButton, resetButton;
    @FXML
    private Spinner<Integer> delayPicker;

    private static final int DEFAULT_ARRAY_SIZE = 50;
    private static final int DEFAULT_ARRAY_RANGE = 100;
    private static final int MIN_DELAY = 0;
    private static final int DEFAULT_DELAY = 100;
    private static final int MAX_DELAY = 1000;
    private static final double MIN_DELAY_SIZE_RATIO= 0.25;

    private BarChart<String, Number> barChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private static int barsNumber, valueRange;
    private static long currentDelay;
    private String sortingAlgorithm;

    @FXML
    public void initialize() {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();

        arraySizeSlider.setValue(DEFAULT_ARRAY_SIZE);
        arraySizeValueLabel.setText(Integer.toString(DEFAULT_ARRAY_SIZE));

        arrayRangeSlider.setValue(DEFAULT_ARRAY_RANGE);
        arrayRangeValueLabel.setText(Integer.toString(DEFAULT_ARRAY_RANGE));

        SpinnerValueFactory<Integer> delaySpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_DELAY, MAX_DELAY);
        delaySpinner.setValue(DEFAULT_DELAY);
        delayPicker.setValueFactory(delaySpinner);

        timeElapsedLabel.setVisible(false);
        timeElapsedValueLabel.setVisible(false);
        infoLabel.setVisible(false);

        barsNumber = DEFAULT_ARRAY_SIZE;
        valueRange = DEFAULT_ARRAY_RANGE;
        currentDelay = DEFAULT_DELAY;

        fillArray();

        arraySizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            barsNumber = (int) arraySizeSlider.getValue();
            arraySizeValueLabel.setText(Integer.toString(barsNumber));
            currentDelay = Math.round(barsNumber * MIN_DELAY_SIZE_RATIO);
            delaySpinner.setValue(Math.toIntExact(currentDelay));
        });

        arrayRangeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            valueRange = (int) arrayRangeSlider.getValue();
            arrayRangeValueLabel.setText(Integer.toString(valueRange));
        });

        delayPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(delayPicker.getValue() < barsNumber * MIN_DELAY_SIZE_RATIO){
                    currentDelay = Math.round(barsNumber * MIN_DELAY_SIZE_RATIO);
                }
                else{
                    currentDelay = delayPicker.getValue();
                }
                delaySpinner.setValue(Math.toIntExact(currentDelay));
            }catch (Exception ignored){

            }
        });

        sortingAlgorithmChoice.setItems(FXCollections.observableArrayList("Selection sort", "Bubble sort", "Insertion sort", "Quick sort", "Merge sort"));
    }

    public void initializeBarChart() {
        barChart.setLegendVisible(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setHorizontalZeroLineVisible(false);
        barChart.setVerticalZeroLineVisible(false);
        barChart.getXAxis().setTickLabelsVisible(false);
        barChart.getXAxis().setOpacity(0);
        barChart.setBarGap(0);
        barChart.setAnimated(false);
    }

    private void fillArray() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart = new BarChart<>(xAxis, yAxis);
        initializeBarChart();
        for (int i = 0; i < barsNumber; i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), new Random().nextInt(valueRange) + 1));
        }
        barChart.getData().add(series);
        pane.setCenter(barChart);

        for (int i = 0; i < barsNumber; i++)
            series.getData().get(i).getNode().setStyle("-fx-background-color:#CC0066");
    }

    private static void delay() {
        try {
            Thread.sleep(currentDelay);
        } catch (InterruptedException ignored) {

        }
    }

    @FXML
    public void handleReset() {
        barChart.getData().clear();
        fillArray();
        sortButton.setDisable(false);
        timeElapsedLabel.setVisible(false);
        timeElapsedValueLabel.setVisible(false);
    }

    @FXML
    public void handleSort() {
        sortButton.setDisable(true);
        sortingAlgorithm = sortingAlgorithmChoice.getSelectionModel().getSelectedItem();
        if (sortingAlgorithm == null) {
            showNoSelectedAlgorithmAlert();
        }
        Task task = new Task() {
            @Override
            protected Object call() {
                try {
                    long startTime = System.nanoTime();
                    switch (sortingAlgorithm) {
                        case "Selection sort" -> SortingAlgorithms.selectionSort(barChart.getData().get(0).getData());
                        case "Bubble sort" -> SortingAlgorithms.bubbleSort(barChart.getData().get(0).getData());
                        case "Insertion sort" -> SortingAlgorithms.insertionSort(barChart.getData().get(0).getData());
                        case "Quick sort" -> SortingAlgorithms.quickSort(barChart.getData().get(0).getData());
                        case "Merge sort" -> SortingAlgorithms.mergeSort(barChart.getData().get(0).getData());
                        default -> throw new Exception();
                    }
                    Platform.runLater(() -> {
                        timeElapsedValueLabel.setText(Long.toString(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime)) + " s");
                        timeElapsedLabel.setVisible(true);
                        timeElapsedValueLabel.setVisible(true);
                    });
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

    private void showNoSelectedAlgorithmAlert() {
        sortButton.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No algorithm selected");
        alert.setContentText("Please select an algorithm in the check box.");
        alert.getDialogPane().setGraphic(new ImageView(new Image(String.valueOf(this.getClass().getResource("icons/warning_icon.png")))));
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
            list.get(maxIndex).getNode().setStyle("-fx-background-color: #FFFF00");
            for (int i = 1; i < range; ++i) {
                list.get(i).getNode().setStyle("-fx-background-color: #3399FF");
                delay();
                if (list.get(i).getYValue().intValue() > list.get(maxIndex).getYValue().intValue()) {
                    list.get(maxIndex).getNode().setStyle("-fx-background-color: #CC0066");
                    delay();
                    maxIndex = i;
                    list.get(maxIndex).getNode().setStyle("-fx-background-color: #FFFF00");
                }
                else{
                    list.get(i).getNode().setStyle("-fx-background-color: #CC0066");
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
                    list.get(maxIndex).getNode().setStyle("-fx-background-color: #CC0066");
                }
                list.get(listSize - 1).getNode().setStyle("-fx-background-color: #99FF99");
            }
            list.get(0).getNode().setStyle("-fx-background-color: #99FF99");
        }

        public static void bubbleSort(ObservableList<XYChart.Data<String, Number>> list) {
            boolean ordered = false;
            int listSize = list.size();
            for (; listSize > 1 && !ordered; listSize--) {
                ordered = true; //Hp: the list is ordered
                for (int i = 0; i < listSize - 1; i++) {
                    list.get(i).getNode().setStyle("-fx-background-color: #3399FF");
                    list.get(i + 1).getNode().setStyle("-fx-background-color: #3399FF");
                    delay();
                    if (list.get(i).getYValue().intValue() >= list.get(i + 1).getYValue().intValue()) {
                        swap(list, i, i + 1);
                        ordered = false;
                    }
                    list.get(i).getNode().setStyle("-fx-background-color: #CC0066");
                    list.get(i + 1).getNode().setStyle("-fx-background-color: #CC0066");
                }
                list.get(listSize - 1).getNode().setStyle("-fx-background-color: #99FF99");
            }
            for(int i = listSize; i >= 0; i--){
                delay();
                list.get(i).getNode().setStyle("-fx-background-color: #99FF99");
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

        private static void mergeSort(ObservableList<XYChart.Data<String, Number>> list) {
            mergeSortRec(list, 0, barsNumber - 1);
        }

        private static void mergeSortRec(ObservableList<XYChart.Data<String, Number>> list, int start, int end) {
            int mid;
            if (start < end) {
                mid = (start + end) / 2;

                mergeSortRec(list, start, mid);
                mergeSortRec(list, mid + 1, end);

                mergeOperation(list, start, mid, end);
            }
        }

        private static void mergeOperation(ObservableList<XYChart.Data<String, Number>> list, int start, int mid, int end) {
            int i = start, j = mid + 1, k = start;

            List<Number> tmp = new ArrayList<>();

            for (XYChart.Data<String, Number> data : list) {
                tmp.add(data.getYValue());
            }

            while (i <= mid && j <= end) {
                if ((int) tmp.get(i) < (int) tmp.get(j)) {
                    delay();
                    (list.get(k)).setYValue(tmp.get(i));
                    i++;
                    k++;
                } else {
                    delay();
                    (list.get(k)).setYValue(tmp.get(j));
                    j++;
                    k++;
                }
            }
            for (; i <= mid; i++) {
                delay();
                (list.get(k)).setYValue(tmp.get(i));
                k++;
            }
            for (; j <= end; j++) {
                delay();
                (list.get(k)).setYValue(tmp.get(j));
                k++;
            }
        }
    }

    @FXML
    public void handleAbout() {

        Hyperlink linkGA = new Hyperlink("Gabriele Aldovardi -> GitHub");
        linkGA.setOnAction(e -> openWebPage("https://github.com/GabrieleAldovardi"));

        Hyperlink linkFC = new Hyperlink("Filippo Cavalieri -> GitHub");
        linkFC.setOnAction(e -> openWebPage("https://github.com/FilippoCavalieri"));

        VBox vbox = new VBox();
        Label description = new Label("Here some references:");
        vbox.getChildren().addAll(description, linkGA, linkFC);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText("Hi, we're two Computer Engineers students at UNIMORE, University of Modena and Reggio Emilia");
        alert.getDialogPane().setGraphic(new ImageView(new Image(String.valueOf(this.getClass().getResource("icons/info_icon.png")))));
        alert.getDialogPane().setContent(vbox);
        alert.showAndWait();
    }

    private void openWebPage(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleSelection() {
        sortingAlgorithm = sortingAlgorithmChoice.getSelectionModel().getSelectedItem();
        if (sortingAlgorithm != null) {
            infoLabel.setVisible(true);
            Tooltip tooltip = new Tooltip();
            switch (sortingAlgorithm) {
                case "Selection sort" ->  tooltip.setText("Sorts the array by dividing it in two parts: a " +
                        "sorted sub-array which\n" + "is built up from right to left and and a sub-array of the " +
                        "remaining\nunsorted elements that occupy the rest of the array.\n" + "Time complexity:\t• best "
                        + "case: O(n²)\t• worst case: O(n²)");
                case "Bubble sort" -> tooltip.setText("Sorts the array by iterating on it. With each iteration compares\n"
                        + "each pair of adjacent elements, swapping them if they're in the\nwrong order. At the " +
                        "end of each iteration the max element will be at\nthe end of the considered sub-array. If"
                        + " during an iteration no\nelements have been swapped then the array is sorted.\nTime " +
                        "complexity:\t• best case: O(n)\t• worst case: O(n²)");
                case "Insertion sort" -> tooltip.setText("Sorts the array by building a new ordered array, in which\n" +
                        "each element is inserted the right place.\n" + "Time complexity:\t• best case: O(n)\t• " +
                        "worst case: O(n²)");
                case "Quick sort" -> tooltip.setText("Sorts the array by partitioning it in two sub-arrays, delimited\n" +
                        "by a pivot element. The first sub-array contains only elements\nless than or equal to the pivot." +
                        " while the second sub-array contains\nonly elements larger then the pivot. The two sub-arrays" +
                        " can then be\nordered apart by applying the same procedure. The algorithm is\nrecursive, the" +
                        "trivial case consists of a sub-array of one element.\nTime complexity:\t• best case: O(n log" +
                        "n)\t• worst case: O(n²)");
                case "Merge sort" -> tooltip.setText("A different version of the quick. Sorts the array by" +
                        "partitioning\nit in two sub-arrays having the same size, sorting them apart\nand then " +
                        "finally merging them.\nTime complexity:\t• best case: O(n log n)\t• worst case: O(n log n)");
            }
            tooltip.setStyle("-fx-background-color: grey");
            tooltip.setStyle("-fx-show-duration: 40s");
            tooltip.setFont(new Font(14));
            infoLabel.setTooltip(tooltip);
        }
    }
}
