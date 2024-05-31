package org.application.gameshelfapp.login.graphiccontroller2;

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
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class TerminalController extends Application implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    private UserLogInBoundary userLogInBoundary;
    private UserBean userBean;
    private CustomerBoundary customerBoundary;
    private SellerBoundary sellerBoundary;
    private String[] command;
    private status state;

    @FXML
    private void execute(KeyEvent event) {

        try {
            if (event.getCode() == KeyCode.ENTER) {
                this.readLine();
                if (this.command[0].equals("login")) {
                    UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
                    userLogInBoundary.log(command[1], command[2]);
                    this.userBean = userLogInBoundary.getUserBean();
                    switch (this.userBean.getTypeOfUser()) {
                        case "Customer" -> textArea.appendText("\n\nType <buy> or <catalogue>\n");
                        case "Seller" -> textArea.appendText("\n\nType <sell> or <sales>\n");
                        case "Administrator" -> textArea.appendText("\n\nType <sell> or <sales> or <sign>\n");
                    }
                } else if (this.command[0].equals("register")) {
                    this.userLogInBoundary = new UserLogInBoundary();
                    this.userLogInBoundary.register(command[1], command[2], command[3]);
                    this.state = status.REGISTER;
                    textArea.appendText("\nInsert the code we sent you through email <code digits>\n");
                } else if (this.command[0].equals("code") && this.state == status.REGISTER) {
                    this.userLogInBoundary.checkCode(command[1]);
                    switch (this.userBean.getTypeOfUser()) {
                        case "Customer" -> textArea.appendText("\n\nType <buy> or <catalogue>\n");
                        case "Seller" -> textArea.appendText("\n\nType <sell> or <sales>\n");
                        case "Administrator" -> textArea.appendText("\n\nType <sell> or <sales> or <sign>\n");
                    }
                } else if (this.command[0].equals("buy") && this.userLogInBoundary.getUserBean() != null && this.userLogInBoundary.getUserBean().getTypeOfUser().equals("Customer")) {
                    this.customerBoundary = new CustomerBoundary(this.userBean);
                    this.userLogInBoundary = null;
                    this.state = status.BUY;
                    textArea.appendText("\n\n Type <search videogame console genre>\n");
                } else if (this.command[0].equals("search") && this.state == status.BUY) {
                    this.customerBoundary.insertFilters(command[1], command[2], command[3]);
                    VideogamesFoundBean videogamesFoundBean = this.customerBoundary.getVideogamesFoundBean();
                    videogamesFoundBean.getInformationFromModel();
                    this.showVideogames(videogamesFoundBean.getVideoamesFoundBean());
                    textArea.appendText("\n\nEnter <see numberOfLine\n>");
                } else if (this.command[0].equals("see") && this.state == status.BUY) {
                    this.seeVideogame(Integer.parseInt(command[1]));
                    textArea.appendText("\n\nType <buy typeOfCard paymentkey street region country videogameNumber> to buy the videogame\n");
                } else if (this.command[0].equals("buy") && this.state == status.BUY){
                    this.customerBoundary.insertCredentialsAndPay(command[1], command[2], command[3], command[4], command[5], this.customerBoundary.getVideogamesFoundBean().getVideoamesFoundBean().get(Integer.parseInt(command[6])));
                    textArea.appendText("\n\npayment successful\n");
                } else if (this.command[0].equals("seeSales")){
                    this.sellerBoundary = new SellerBoundary(this.userBean);
                    this.sellerBoundary.getGamesToSend();
                    this.showSales(this.sellerBoundary.getSalesBean());
                    this.state = status.SALES;
                    this.textArea.appendText("\n\nType <confirm line>");
                } else if (this.command[0].equals("command") && this.state == status.SALES){
                    this.sellerBoundary.sendGame(Integer.parseInt(command[0]));
                }
            }
        } catch (SyntaxErrorException | PersistencyErrorException | CheckFailedException | GmailException |
                 FiltersException | NumberFormatException | RefundException | GameSoldOutException |
                 NullPasswordException | InvalidAddressException | ConfirmDeliveryException e) {
            this.textArea.appendText("\n\n" + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            this.textArea.appendText("You should insert the right number of parameters");
        }
    }

    private void readLine() {
        this.command = textField.getText().split(" ");
        this.textArea.appendText("\n" + textField.getText() + "\n");
    }
    private void showVideogames(List<VideogameBean> videogames) {
        for (VideogameBean game : videogames) {
            String lineToShow = game.getName() + " " + " " + game.getCopiesBean() + " " + game.getPriceBean() + "€\n";
            this.textArea.appendText(lineToShow);
        }
    }
    private void seeVideogame(int index) {
        VideogameBean gameSelected = this.customerBoundary.getVideogamesFoundBean().getVideoamesFoundBean().get(index);
        FiltersBean filters = this.customerBoundary.getFiltersBean();
        String textToShow = gameSelected.getName() + "\n" +
                "Filters: " + filters.getConsoleBean() + " " + filters.getCategoryBean() + "\n"
                + "Price per copy: " + gameSelected.getPriceBean() + "€ " + "Copies: " + gameSelected.getCopiesBean() + "\n"
                + gameSelected.getDescriptionBean() + "\n";
        this.textArea.appendText(textToShow);
    }


    private void showSales(List<SaleBean> sales) {
        for (SaleBean saleBean : sales) {
            saleBean.getInformationFromModel();
            String[] components = {String.valueOf(saleBean.getIdBean()), saleBean.getGameSoldBean().getName(), String.valueOf(saleBean.getGameSoldBean().getCopiesBean()), String.valueOf(saleBean.getGameSoldBean().getPriceBean()), saleBean.getEmailBean(), saleBean.getAddressBean(), saleBean.getPlatformBean(), saleBean.getStateBean()};
            String line = String.join(" ", components) + "\n";
            this.textArea.appendText(line);
        }
    }


    @Override
    public void start(Stage stage) throws IOException, PersistencyErrorException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/org/application/gameshelfapp/GUI2/Access-Page.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);
        stage.setTitle("Gameshelfapp");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.textArea.setText("digit <login email password>\n or <register username email password>");
        try {
            this.userLogInBoundary = new UserLogInBoundary();
        } catch (PersistencyErrorException e) {
            System.exit(1);
        }
    }

    private enum status {
        REGISTER, BUY, SALES, SELL
    }
}

