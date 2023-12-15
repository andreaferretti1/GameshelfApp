module org.application.gameshelfapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.application.gameshelfapp to javafx.fxml;
    exports org.application.gameshelfapp;
    exports org.application.gameshelfapp.login;
    opens org.application.gameshelfapp.login to javafx.fxml;
    exports org.application.gameshelfapp.login.graphiccontrollers;
    opens org.application.gameshelfapp.login.graphiccontrollers to javafx.fxml;
}