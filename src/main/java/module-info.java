module com.example.sortingalgorithmvisualizator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.sortingalgorithmvisualizator to javafx.fxml;
    exports com.example.sortingalgorithmvisualizator;
}