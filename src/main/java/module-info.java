module com.example.g21 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.g21 to javafx.fxml;
    exports com.example.g21;
}