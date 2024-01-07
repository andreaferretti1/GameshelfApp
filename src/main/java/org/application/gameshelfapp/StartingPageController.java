package org.application.gameshelfapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.login.RegistrationPageController;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;

import java.io.IOException;


public class StartingPageController extends Application {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    private UserLogInBoundary userBoundary;


    private void setUserBoundary(UserLogInBoundary boundary){
        this.userBoundary = boundary;
    }

    @FXML
    private void switchToRegistrationPage(MouseEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Registration-Page.fxml"));
        Parent root = fxmlloader.load();

        RegistrationPageController regController = fxmlloader.getController();
        regController.setUserLogInBoundary(this.userBoundary);
        this.userBoundary.setRegistrationPageController(regController);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void logIn(MouseEvent event) {
         this.userBoundary.log(this.passwordField.getText(), this.usernameField.getText());
    }

    public void switchToHomePage() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Home-page.fxml"));
            Parent root = null;
            root = fxmlLoader.load();

            Stage stage = (Stage) this.usernameField.getScene().getWindow();
            Scene scene = new Scene(root, 1440, 768);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.exit(1);
        }
    }

    public void displayErrorWindow(String s){
        //TODO crea GUI errore
    }


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Starting-Page.fxml"));
        Parent root = fxmlLoader.load();

        StartingPageController startingPageController = fxmlLoader.getController();
        startingPageController.setUserBoundary(new UserLogInBoundary(startingPageController));

        Scene scene = new Scene(root, 1440, 768);

        stage.setScene(scene);

        stage.setTitle("GameShelf");

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
