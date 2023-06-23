module com.example.sortingalgorithmvisualizator {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sortingalgorithmvisualizator to javafx.fxml;
    exports com.example.sortingalgorithmvisualizator;
}