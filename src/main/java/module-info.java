module com.example.networkchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.networkchat to javafx.fxml;
    exports com.example.networkchat;
    exports com.example.networkchat.controllers;
    opens com.example.networkchat.controllers to javafx.fxml;
}