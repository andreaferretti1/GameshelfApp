package org.application.gameshelfapp.login;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.application.gameshelfapp.StartingPageController;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
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
    @FXML
    private RadioButton customerButton;
    @FXML
    private RadioButton sellerButton;
    @FXML
    private RadioButton bothButton;
    @FXML
    private ToggleGroup typeOfUser;
    private UserLogInBoundary userBoundary;
    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setUserLogInBoundary(UserLogInBoundary b){
        this.userBoundary = b;
    }

    public void switchToInsertCodeScene(){

        try{
           InsertCodeController.start(this.stage, this.userBoundary);
        }catch (IOException e) {
            System.exit(1);
        }
    }
    @FXML
    private void register(MouseEvent event) {

        String userType = null;
        if(this.customerButton.isSelected()) userType = "customer";
        else if(this.sellerButton.isSelected()) userType = "seller";
        else if(this.bothButton.isSelected()) userType = "both";

        if(userType == null)  ErrorPageController.displayErrorWindow("You should choose your role");
        try{
            this.userBoundary.register(this.usernameTextField.getText(), this.emailTextField.getText(), this.passwordTextField.getText(), userType);
        } catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException | GmailException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
        this.switchToInsertCodeScene();
    }

    @FXML
    private void goToStartPage(MouseEvent event) {

        try {
            StartingPageController startingPageController = new StartingPageController();
            startingPageController.setUserBoundary(this.userBoundary);
            startingPageController.start(this.stage);
        } catch (IOException | PersistencyErrorException e){
            System.exit(1);
        }
    }


    public static void start(Stage myStage, UserLogInBoundary boundary) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationPageController.class.getResource("/org/application/gameshelfapp/GUI/Registration-Page.fxml"));
        Parent root = fxmlLoader.load();

        RegistrationPageController controller = fxmlLoader.getController();
        controller.setUserLogInBoundary(boundary);
        controller.setStage(myStage);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }

}
