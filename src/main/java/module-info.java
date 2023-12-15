module org.application.gameshelfapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.application.gameshelfapp to javafx.fxml;
    exports org.application.gameshelfapp;
}