package org.application.gameshelfapp.login;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.graphiccontrollers.InsertCodeController;


import java.io.IOException;


public class RegistrationPageController {
    public TextField emailTextField, usernameTextField, passwordTextField, confirmPasswordTextField;
    private UserLogInBoundary userBoundary;


    public void setUserLogInBoundary(UserLogInBoundary b){
        this.userBoundary = b;
    }
    @FXML
    private void register(MouseEvent event) throws IOException{

        this.userBoundary.register(this.emailTextField.getText(), this.usernameTextField.getText(), this.passwordTextField.getText());

        FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/Insert-code.fxml"));
        Parent root = fxmlLoad.load();

        InsertCodeController controller = fxmlLoad.getController();
        controller.setUserLogInBoundary(this.userBoundary);

        Scene scene = new Scene(root, 1440, 768);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void verifyCode(MouseEvent event){


    }



    @FXML
    private void goToStartPage(MouseEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/Starting-Page.fxml"));
            Parent root = fxmlloader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1440, 768);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e){
            System.out.println("Couldn't load Starting page...");
        }
    }



}
