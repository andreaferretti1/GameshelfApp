package org.application.gameshelfapp.login;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.application.gameshelfapp.StartingPageController;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.graphiccontrollers.InsertCodeController;


import java.io.IOException;


public class RegistrationPageController {

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    private UserLogInBoundary userBoundary;


    public void setUserLogInBoundary(UserLogInBoundary b){
        this.userBoundary = b;
    }

    public void switchToInsertCodeScene(){
        FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Insert-code.fxml"));
        Parent root = null;
        try {
            root = fxmlLoad.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InsertCodeController controller = fxmlLoad.getController();
        controller.setUserLogInBoundary(this.userBoundary);
        this.userBoundary.setInsertCodeController(controller);

        Scene scene = new Scene(root, 1440, 768);

        Stage stage = (Stage) this.emailTextField.getScene().getWindow();

        stage.setScene(scene);

        stage.show();
    }

    public void displayErrorWindow(String s){

    }

    @FXML
    private void register(MouseEvent event) throws IOException{

        this.userBoundary.register(this.emailTextField.getText(), this.usernameTextField.getText(), this.passwordTextField.getText());

    }

    @FXML
    private void goToStartPage(MouseEvent event) {

        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Starting-Page.fxml"));
            Parent root = fxmlloader.load();

            StartingPageController controller = fxmlloader.getController();
            this.userBoundary.setStartingPageController(controller);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1440, 768);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e){
            System.exit(1);
        }
    }
}
