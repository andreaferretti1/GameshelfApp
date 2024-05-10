package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
public class ErrorPageController{
    @FXML
    private Label errorLabel;
    public static void displayErrorWindow(String s){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ErrorPageController.class.getResource("/org/application/gameshelfapp/GUI/Error-Page.fxml"));
            Parent root = fxmlLoader.load();
            Stage myStage = new Stage();
            Scene scene = new Scene(root, 480, 230);
            ErrorPageController controller = fxmlLoader.getController();
            controller.getErrorLabel().setText(s);
            myStage.setScene(scene);
            myStage.show();
        } catch(IOException e){
            System.exit(1);
        }
    }
    private Label getErrorLabel() {
        return errorLabel;
    }
}
