package org.application.gameshelfapp.registration.grapichcontroller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.application.gameshelfapp.StartingPageController;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.registration.boundary.RegistrationBoundary;

import java.io.IOException;
public class RegistrationPageController {

    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    private RegistrationBoundary registrationBoundary;

    public void setRegistrationBoundary(RegistrationBoundary b){
        this.registrationBoundary = b;
    }

    private Stage getStage(){
        return (Stage) emailTextField.getScene().getWindow();
    }

    @FXML
    private void register(MouseEvent event) {
        try{
            this.registrationBoundary.register(this.usernameTextField.getText(), this.emailTextField.getText(), this.passwordTextField.getText());
            this.switchToInsertCodeScene();
        } catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException | GmailException |
                 NullPasswordException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    @FXML
    private void goToStartPage(MouseEvent event) {
        StartingPageController startingPageController = new StartingPageController();
        startingPageController.start(this.getStage());
    }
    public void switchToInsertCodeScene(){
        try{
            InsertCodeController.start(this.getStage(), this.registrationBoundary);
        }catch (IOException e) {
            System.exit(1);
        }
    }
    public static void start(Stage myStage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationPageController.class.getResource("/org/application/gameshelfapp/GUI/Registration-Page.fxml"));
        Parent root = fxmlLoader.load();

        RegistrationPageController controller = fxmlLoader.getController();
        controller.setRegistrationBoundary(new RegistrationBoundary());
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }
}
