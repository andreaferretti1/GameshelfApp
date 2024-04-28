module org.application.gameshelfapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.api.client;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.auth;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires mail;
    requires org.apache.commons.codec;
    requires com.opencsv;
    requires jdk.httpserver;
    requires java.sql;


    opens org.application.gameshelfapp to javafx.fxml;
    exports org.application.gameshelfapp;
    exports org.application.gameshelfapp.login;
    opens org.application.gameshelfapp.login to javafx.fxml;
    exports org.application.gameshelfapp.login.graphiccontrollers;
    opens org.application.gameshelfapp.login.graphiccontrollers to javafx.fxml;
    exports org.application.gameshelfapp.buyvideogames.graphiccontrollers;
    opens org.application.gameshelfapp.buyvideogames.graphiccontrollers to javafx.fxml;
    exports org.application.gameshelfapp.login.graphiccontroller2;
    opens org.application.gameshelfapp.login.graphiccontroller2 to javafx.fxml;
}