package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPageController {

    @FXML
    private TextField textField;

    public void showErrorMessage(String s){
        this.textField.setText(s);
    }
}
