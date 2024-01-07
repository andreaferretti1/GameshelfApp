package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;

import java.io.IOException;



public class InsertCodeController {
    private UserLogInBoundary userLogInBoundary;

    @FXML
    public TextField codeField;

    public void setUserLogInBoundary(UserLogInBoundary boundary){
        this.userLogInBoundary = boundary;
    }

    @FXML
    private void verifyCode(MouseEvent event){
        String code = this.codeField.getText();
        this.userLogInBoundary.checkCode(code);
    }

    public void switchToHomePage(){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Home-Page.fxml"));
        try {
            Parent root = loader.load();

            Stage stage = (Stage) this.codeField.getScene().getWindow();
            Scene scene = new Scene(root, 1440, 768);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void displayErrorWindow(String s){

    }
}
