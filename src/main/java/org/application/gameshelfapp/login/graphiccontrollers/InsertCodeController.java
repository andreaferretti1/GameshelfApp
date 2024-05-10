package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import java.io.IOException;
public class InsertCodeController  {
    private UserLogInBoundary userLogInBoundary;

    @FXML
    public TextField codeField;

    private Stage getStage(){
        return (Stage) codeField.getScene().getWindow();
    }

    public void setUserLogInBoundary(UserLogInBoundary boundary){
        this.userLogInBoundary = boundary;
    }

    @FXML
    private void verifyCode(MouseEvent event){
        String code = this.codeField.getText();
        try{
            this.userLogInBoundary.checkCode(code);
            HomePageController.start(this.getStage(), this.userLogInBoundary.getUserBean());
        } catch(NumberFormatException | CheckFailedException | PersistencyErrorException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch(IOException e){
            System.exit(1);
        }
    }

    public static void start(Stage myStage, UserLogInBoundary boundary) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(InsertCodeController.class.getResource("/org/application/gameshelfapp/GUI/Insert-code.fxml"));
        Parent root = fxmlLoader.load();
        InsertCodeController controller = fxmlLoader.getController();
        controller.setUserLogInBoundary(boundary);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.setTitle("Gameshelf");
        myStage.show();
    }
}
