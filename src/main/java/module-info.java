module com.example.networkchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.networkchat to javafx.fxml;
    exports com.example.networkchat;
}