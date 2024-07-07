package org.application.gameshelfapp.registration.grapichcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.registration.boundary.RegistrationBoundary;

import java.io.IOException;
public class InsertCodeController  {
    private RegistrationBoundary registrationBoundary;

    @FXML
    public TextField codeField;

    private Stage getStage(){
        return (Stage) codeField.getScene().getWindow();
    }

    public void setRegistrationBoundary(RegistrationBoundary boundary){
        this.registrationBoundary = boundary;
    }

    @FXML
    private void verifyCode(MouseEvent event){
        int code = Integer.parseInt(this.codeField.getText());
        try{
            this.registrationBoundary.checkCode(code);
            HomePageController.start(this.getStage(), this.registrationBoundary.getUserBean());
        } catch(NumberFormatException | CheckFailedException | PersistencyErrorException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch(IOException e){
            System.exit(1);
        }
    }

    public static void start(Stage myStage, RegistrationBoundary boundary) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(InsertCodeController.class.getResource("/org/application/gameshelfapp/GUI/Insert-code.fxml"));
        Parent root = fxmlLoader.load();
        InsertCodeController controller = fxmlLoader.getController();
        controller.setRegistrationBoundary(boundary);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.setTitle("Gameshelf");
        myStage.show();
    }
}
