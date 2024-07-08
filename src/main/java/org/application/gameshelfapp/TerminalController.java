package org.application.gameshelfapp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.boundary2.TerminalCustomerBoundary;
import org.application.gameshelfapp.confirmsale.boundary2.TerminalSellerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.boundary2.TerminalUserLogInBoundary;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.registration.boundary2.TerminalRegistrationBoundary;
import org.application.gameshelfapp.sellvideogames.boundary2.TerminalSellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.exception.*;
import org.application.gameshelfapp.signemployee.boundary2.TerminalAdminBoundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TerminalController extends Application implements Initializable{
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    private TerminalBoundary boundary;
    private String[] command;
    @FXML
    private void execute(KeyEvent event) {

        try {
            if (event.getCode() == KeyCode.ENTER) {
                this.readLine();
                switch(this.command[0]){
                    case "login" -> {
                            this.boundary = new TerminalUserLogInBoundary();
                            this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                    case "register" -> {
                        this.boundary = new TerminalRegistrationBoundary();
                        this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                    case "see catalogue" -> {
                        this.boundary = new TerminalCustomerBoundary(this.boundary.getUserBean());
                        this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                    case "see sales" -> {
                        this.boundary = new TerminalSellerBoundary(this.boundary.getUserBean());
                        this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                    case "show" -> {
                        this.boundary = new TerminalSellerAddGamesBoundary(this.boundary.getUserBean());
                        this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                    case "sign employee" -> {
                        this.boundary = new TerminalAdminBoundary(this.boundary.getUserBean());
                        this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                    default -> this.textArea.appendText(this.boundary.executeCommand(this.command));
                    }
                }
            } catch (SyntaxErrorException | PersistencyErrorException | CheckFailedException | GmailException |
                     NumberFormatException | RefundException | GameSoldOutException | NullPasswordException |
                     InvalidAddressException | ConfirmDeliveryException | NoGameInCatalogueException |
                     InvalidTitleException | AlreadyExistingVideogameException | WrongSaleException | WrongUserTypeException e) {
                this.textArea.appendText("\n\n" + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                this.textArea.appendText("You should insert the right number of parameters");
            } catch(NullPointerException e){
            this.textArea.appendText(String.join(" or ", TerminalUserLogInBoundary.START_COMMAND, TerminalRegistrationBoundary.START_COMMAND, TerminalCustomerBoundary.START_COMMAND, TerminalSellerBoundary.START_COMMAND, TerminalSellerAddGamesBoundary.START_COMMAND, TerminalAdminBoundary.START_COMMAND));
        }
    }

    private void readLine() {
        this.command = textField.getText().split(", ");
        this.textArea.appendText("\n" + textField.getText() + "\n");
        this.textField.clear();
    }

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(TerminalController.class.getResource("/org/application/gameshelfapp/GUI2/Access-Page.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);
        stage.setTitle("Gameshelfapp");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.textArea.setText(TerminalUserLogInBoundary.START_COMMAND + " or " + TerminalRegistrationBoundary.START_COMMAND);
    }

    public static void main(String[] args){
        launch();
    }
}

