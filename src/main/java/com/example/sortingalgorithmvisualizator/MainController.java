package com.example.sortingalgorithmvisualizator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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
    private Button sortButton;
    @FXML
    private Spinner<Integer> delayPicker;

    @FXML
    private ColorPicker arrayColorPicker;

    private static final int DEFAULT_ARRAY_SIZE = 50;
    private static final int DEFAULT_ARRAY_RANGE = 100;
    private static final int MIN_DELAY = 0;
    private static final int DEFAULT_DELAY = 100;
    private static final int MAX_DELAY = 1000;
    private static final double MIN_DELAY_SIZE_RATIO = 0.4;
    private static final String DEFAULT_ARRAY_COLOR = "-fx-background-color: #CC0066";

    private BarChart<String, Number> barChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    static int barsNumber;
    static int valueRange;
    static long currentDelay;
    private String sortingAlgorithm, selectedColor;

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
        selectedColor = DEFAULT_ARRAY_COLOR;

        fillArray(selectedColor);

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

        delayPicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 1) {
                delayPicker.getEditor().setText("");
            } else if (!newValue.matches("\\d*")) {
                delayPicker.getEditor().setText(oldValue);
            } else if (Long.parseLong(newValue) > Integer.MAX_VALUE) {
                delayPicker.getEditor().setText(oldValue);
            }
        });

        delayPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(delayPicker.getEditor().textProperty().get());
                if (delayPicker.getValue() < barsNumber * MIN_DELAY_SIZE_RATIO) {
                    currentDelay = Math.round(barsNumber * MIN_DELAY_SIZE_RATIO);
                } else {
                    currentDelay = delayPicker.getValue();
                }
                delaySpinner.setValue(Math.toIntExact(currentDelay));
            } catch (NumberFormatException ignored) {
                currentDelay = Math.round(barsNumber * MIN_DELAY_SIZE_RATIO);
            }
            delaySpinner.setValue(Math.toIntExact(currentDelay));
        });

        sortingAlgorithmChoice.setItems(FXCollections.observableArrayList("Bubble sort", "Cocktail sort", "Heap " + "sort", "Insertion sort", "Merge sort", "Quick sort", "Radix sort", "Selection sort"));
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

    private void fillArray(String selectedColor) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        barChart = new BarChart<>(xAxis, yAxis);
        initializeBarChart();
        for (int i = 0; i < barsNumber; i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), new Random().nextInt(valueRange) + 1));
        }
        barChart.getData().add(series);
        pane.setCenter(barChart);

        for (int i = 0; i < barsNumber; i++)
            series.getData().get(i).getNode().setStyle(selectedColor);
    }

    @FXML
    public void handleReset() {
        barChart.getData().clear();
        selectedColor = "-fx-background-color: #" + String.valueOf(arrayColorPicker.getValue()).substring(2, 8);
        fillArray(selectedColor);
        sortButton.setDisable(false);
        timeElapsedLabel.setVisible(false);
        timeElapsedValueLabel.setVisible(false);
        delayPicker.setEditable(true);
        delayPicker.setDisable(false);
        arraySizeSlider.setDisable(false);
        arrayRangeSlider.setDisable(false);
    }
    @FXML
    public void handleSort() {
        delayPicker.setDisable(true);
        arraySizeSlider.setDisable(true);
        arrayRangeSlider.setDisable(true);
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
                        case "Bubble sort" -> BubbleSort.bubbleSort(barChart.getData().get(0).getData(),
                                selectedColor);
                        case "Cocktail sort" ->
                                CocktailSort.cocktailSort(barChart.getData().get(0).getData(), selectedColor);
                        case "Heap sort" -> HeapSort.heapSort(barChart.getData().get(0).getData(), selectedColor);
                        case "Insertion sort" ->
                                InsertionSort.insertionSort(barChart.getData().get(0).getData(), selectedColor);
                        case "Merge sort" -> MergeSort.mergeSort(barChart.getData().get(0).getData(), selectedColor);
                        case "Quick sort" -> QuickSort.quickSort(barChart.getData().get(0).getData(), selectedColor);
                        case "Radix sort" -> RadixSort.radixSort(barChart.getData().get(0).getData(), selectedColor);
                        case "Selection sort" ->
                                SelectionSort.selectionSort(barChart.getData().get(0).getData(), selectedColor);
                        default -> throw new Exception();
                    }
                    Platform.runLater(() -> {
                        if (sortButton.isDisabled()) {
                            timeElapsedValueLabel.setText(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime) + " s");
                            timeElapsedLabel.setVisible(true);
                            timeElapsedValueLabel.setVisible(true);
                        }
                    });
                } catch (NullPointerException ignored) {

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    private void showNoSelectedAlgorithmAlert() {
        sortButton.setDisable(false);
        delayPicker.setDisable(false);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No algorithm selected");
        alert.setContentText("Please select an algorithm in the check box.");
        alert.getDialogPane().setGraphic(new ImageView(new Image(String.valueOf(this.getClass().getResource("icons/warning_icon.png")))));
        alert.showAndWait();
    }

    @FXML
    public void handleAbout() {

        Hyperlink linkGA = new Hyperlink("https://github.com/GabrieleAldovardi");
        linkGA.setOnAction(e -> openWebPage("https://github.com/GabrieleAldovardi"));

        Hyperlink linkFC = new Hyperlink("https://github.com/FilippoCavalieri");
        linkFC.setOnAction(e -> openWebPage("https://github.com/FilippoCavalieri"));

        VBox vbox = new VBox();
        Label description = new Label("Here you can find more details:");
        vbox.getChildren().addAll(description, linkGA, linkFC);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText("Hi, we're two Computer Engineering students at UNIMORE, University of Modena and Reggio " + "Emilia");
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
                case "Bubble sort" -> tooltip.setText("""
                        Sorts the array by iterating on it. With each iteration compares
                        each pair of adjacent elements, swapping them if they're in the
                        wrong order. At the end of each iteration the max element will be at
                        the end of the considered sub-array. If during an iteration no
                        elements have been swapped then the array is sorted.
                        Time complexity:\t• best case: O(n)\t• worst case: O(n²)
                        """);
                case "Cocktail sort" -> tooltip.setText("""
                        A different version of the bubble sort. Sorts the array by iterating over it.
                        With the first iteration the max element will be at the end of the array.
                        With the second iteration the min element will be at the end of the array, and so on.
                        If during an iteration no elements have been swapped then the array is sorted.
                        Time complexity:\t• best case: O(n)\t• worst case: O(n²)
                        """);
                case "Heap sort" -> tooltip.setText("""
                        Sorts the array by using an heap sort data structure.
                        """);
                case "Insertion sort" -> tooltip.setText("""
                        Sorts the array by building a new ordered array, in which
                        each element is inserted the right place.
                        Time complexity:\t• best case: O(n)\t• worst case: O(n²)
                        """);
                case "Merge sort" -> tooltip.setText("""
                        A different version of the quick sort. Sorts the array by partitioning
                        it in two sub-arrays having the same size, sorting them apart and then finally merging them.
                        Time complexity:\t• best case: O(n log n)\t• worst case: O(n log n)
                        """);
                case "Quick sort" -> tooltip.setText("""
                        Sorts the array by partitioning it in two sub-arrays, delimited
                        by a pivot element. The first sub-array contains only elements
                        less than or equal to the pivot. while the second sub-array contains
                        only elements larger then the pivot. The two sub-arrays can then be\n
                        ordered apart by applying the same procedure. The algorithm is
                        recursive, the trivial case consists of a sub-array of one element.\n
                        Time complexity:\t• best case: O(n log n)\\t• worst case: O(n²)
                        """);
                case "Selection sort" -> tooltip.setText("""
                        Sorts the array by dividing it in two parts: a sorted sub-array which
                        is built up from right to left and and a sub-array of the remaining
                        unsorted elements that occupy the rest of the array.
                        Time complexity:\t• best case: O(n²)\\t• worst case: O(n²)
                        """);
            }
            tooltip.setStyle("-fx-background-color: grey");
            tooltip.setStyle("-fx-show-duration: 40s");
            tooltip.setFont(new Font(14));
            infoLabel.setTooltip(tooltip);
        }
    }
}
