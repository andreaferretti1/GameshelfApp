package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.io.IOException;

public class CredentialsPageController {

    private CustomerBoundary customerBoundary;

    @FXML
    private TextField name;
    @FXML
    private TextField typeOfCard;
    @FXML
    private TextField paymentKey;
    @FXML
    private TextField address;
    @FXML
    private TextField regionField;
    @FXML
    private TextField countryField;
    private Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setCustomerBoundary(CustomerBoundary customerBoundary) {
        this.customerBoundary = customerBoundary;
    }

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, this.customerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }
    @FXML
    private void goBack(MouseEvent event){
        try{
            SearchPageController.start(this.stage, this.customerBoundary);
        } catch(IOException | WrongUserTypeException e){
            System.exit(1);
        }
    }
    @FXML
    private void insertCredentials(){
        try {
            this.customerBoundary.insertCredentialsAndPay(name.getText(), typeOfCard.getText(), paymentKey.getText(), address.getText(), regionField.getText(), countryField.getText());
        } catch (SyntaxErrorException | RefundException | GameSoldOutException | PersistencyErrorException | InvalidAddressException | NoGameInCatalogueException e) {
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    public static void start(Stage myStage, CustomerBoundary boundary) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CredentialsPageController.class.getResource("/org/application/gameshelfapp/GUI/Credentials-Page.fxml"));
        Parent root = fxmlLoader.load();

        CredentialsPageController controller = fxmlLoader.getController();
        controller.setCustomerBoundary(boundary);
        controller.setStage(myStage);
        Scene scene = new Scene(root, 700, 500);
        myStage.setScene(scene);
        myStage.show();
    }
}
