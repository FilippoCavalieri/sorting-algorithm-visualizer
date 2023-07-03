package com.example.sortingalgorithmvisualizator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;

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
    private SplitMenuButton sortingAlgorithmMenu;
    @FXML
    private Button sortButton, resetButton;
    @FXML
    private Label arraySizeValueLabel, arrayRangeValueLabel;
    private final int DEFAULT_ARRAY_SIZE = 50;
    private final int DEFAULT_ARRAY_RANGE = 100;
    private int barsNumber, valueRange;
    private String sortingAlgorithm;

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
}
