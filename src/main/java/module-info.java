module com.example.javafx_dakar {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafx_dakar to javafx.fxml;
    opens com.example.javafx_dakar.model to javafx.base;
    exports com.example.javafx_dakar;
    exports com.example.javafx_dakar.controller;
    opens com.example.javafx_dakar.controller to javafx.fxml;
}