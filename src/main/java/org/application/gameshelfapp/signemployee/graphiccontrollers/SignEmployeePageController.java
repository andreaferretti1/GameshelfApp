package org.application.gameshelfapp.signemployee.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.signemployee.boundary.AdminBoundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignEmployeePageController implements Initializable{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private RadioButton sellerButton;
    @FXML
    private RadioButton adminButton;
    @FXML
    ToggleGroup radioButtonGroup;
    private AdminBoundary adminBoundary;
    private Stage stage;
    public void setAdminBoundary(AdminBoundary adminBoundary) {
        this.adminBoundary = adminBoundary;
    }

    public AdminBoundary getAdminBoundary() {
        return this.adminBoundary;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public static void start(Stage stage, UserBean userBean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SignEmployeePageController.class.getResource("/org/application/gameshelfapp/GUI/Starting-Page.fxml"));
        Parent root = fxmlLoader.load();

        SignEmployeePageController controller = fxmlLoader.getController();
        controller.setAdminBoundary(new AdminBoundary(userBean));
        controller.setStage(stage);
        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);

        stage.setTitle("GameShelf");
    }

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, this.adminBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    private void registerEmployee(MouseEvent event){
       try {
           this.adminBoundary.register(usernameField.getText(), emailField.getText(), passwordField.getText(), ((RadioButton) radioButtonGroup.getSelectedToggle()).getText());
       }catch(SyntaxErrorException | PersistencyErrorException | CheckFailedException | NullPasswordException e){
           ErrorPageController.displayErrorWindow(e.getMessage());
       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sellerButton.setSelected(true);
    }
}
