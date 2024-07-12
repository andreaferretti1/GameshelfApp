package org.application.gameshelfapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.registration.grapichcontroller.RegistrationPageController;

import java.io.IOException;


public class StartingPageController{

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    private UserLogInBoundary userBoundary;

    public void setUserBoundary(UserLogInBoundary boundary){
        this.userBoundary = boundary;
    }

    private Stage getCurrentStage(){
        return (Stage) emailField.getScene().getWindow();
    }
    @FXML
    private void switchToRegistrationPage(MouseEvent event) {
        try{
           RegistrationPageController.start(this.getCurrentStage());
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @FXML
    private void logIn(MouseEvent event) {
        try{
            this.userBoundary.log(this.emailField.getText(), this.passwordField.getText());
            HomePageController.start(this.getCurrentStage(), this.userBoundary.getUserBean());
        }  catch (SyntaxErrorException | PersistencyErrorException | CheckFailedException | NullPasswordException e) {
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch(IOException e){
            System.exit(1);
        }
    }

    public static void start() throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(StartingPageController.class.getResource("/org/application/gameshelfapp/GUI/Starting-Page.fxml"));
        Parent root = fxmlLoader.load();

        StartingPageController startingPageController = fxmlLoader.getController();
        startingPageController.setUserBoundary(new UserLogInBoundary());
        Scene scene = new Scene(root, 1440, 768);
        Stage myStage = new Stage();
        myStage.setScene(scene);

        myStage.setTitle("GameShelf");

        myStage.show();
    }

    public static void main(String[] args){
        try{
            StartingPageController.start();
        } catch (IOException e){
            System.exit(1);
        }
    }
}
