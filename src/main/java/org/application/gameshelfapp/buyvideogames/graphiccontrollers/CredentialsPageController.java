package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;

import java.io.IOException;

public class CredentialsPageController {

    private CustomerBoundary customerBoundary;

    @FXML
    private TextField typeOfCard;
    @FXML
    private TextField paymentKey;
    @FXML
    private TextField address;



    public void setCustomerBoundary(CustomerBoundary customerBoundary) {
        this.customerBoundary = customerBoundary;
    }

    @FXML
    private void insertCredentials(){
        try {
            this.customerBoundary.insertCredentialsAndPay(typeOfCard.getText(), paymentKey.getText(), address.getText());
        } catch (SyntaxErrorException | RefundException | GameSoldOutException | GmailException | PersistencyErrorException e) {
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    public static void start(Stage myStage, CustomerBoundary boundary) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CredentialsPageController.class.getResource("/org/application/gameshelfapp/GUI/Credentials-Page.fxml"));
        Parent root = fxmlLoader.load();

        CredentialsPageController controller = fxmlLoader.getController();
        controller.setCustomerBoundary(boundary);
        Scene scene = new Scene(root, 700, 500);
        myStage.setScene(scene);
        myStage.show();
    }
}
