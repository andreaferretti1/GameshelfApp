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
import org.application.gameshelfapp.buyvideogames.bean.OwnerBean;
import org.application.gameshelfapp.buyvideogames.bean.ShoppingCartBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AccessPageController extends Application implements Initializable {
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    private UserLogInBoundary userLogInBoundary;
    private CustomerBoundary customerBoundary;
    private SellerBoundary sellerBoundary;
    private Stage stage;
    private VideogameBean gameSelected;
    private String[] command;

    @FXML
    private void access(KeyEvent event){

        try {
               if (event.getCode() == KeyCode.ENTER) {
                    this.command = textField.getText().split(" ");
                    if (this.command[0].equals("login")) {
                        this.userLogInBoundary.log(command[1], command[2]);
                        textArea.appendText("\n\nType <buy> or <catalogue>\n");
                    }
                    else if(this.command[0].equals("register")) {
                      this.userLogInBoundary.register(command[1], command[2], command[3], command[4]);
                      textArea.appendText("\nInsert the code we sent you through email <code digits>\n");
                    }
                    else if(this.command[0].equals("code")){
                      this.userLogInBoundary.checkCode(command[1]);
                      textArea.appendText("\n\nType <buy> or <catalogue>\n");
                    }
                    else if(this.command[0].equals("buy") && this.userLogInBoundary.getUserBean() != null) {
                        this.customerBoundary = new CustomerBoundary(this.userLogInBoundary.getUserBean());
                        this.userLogInBoundary = null;
                        textArea.appendText("\n\n Type <search videogame console(PlayStation, XBox, Pc) connection(Online, Offline) genre(Sport, Shooting, Adventure)>\n");
                    }
                    else if(this.command[0].equals("search")){
                        this.customerBoundary.insertFilters(command[1], command[2], command[3], command[4]);
                        textArea.appendText("\n\nEnter <see videogameId\n>");
                        this.showVideogames(this.customerBoundary.getGamesFound());
                    }
                    else if(this.command[0].equals("see")){
                        this.seeVideogame(Integer.parseInt(command[1]));
                        textArea.appendText("\n\nType <add copies> to add the videogame to the cart\n");
                    }
                    else if(this.command[0].equals("add")){
                        this.customerBoundary.addGameToCart(this.gameSelected , command[1]);
                        textArea.appendText("\n\nSearch another videogame or enter <cart> to see your shopping cart\n");
                    }
                    else if(this.command[0].equals("cart")){
                        this.showShoppingCart();
                        this.textArea.appendText("\n\ntype <removeFromCart videogameId numberOfCopies> to remove an item\n");
                        this.textArea.appendText("\n\n type <pay typeOfcard paymentKey addressOfDelivery> to pay\n");
                    }
                    else if(this.command[0].equals("removeFromCart")){
                        this.customerBoundary.removeCopiesFromCart(command[1], command[2]);
                    }
                    else if(this.command[0].equals("pay")){
                        this.customerBoundary.insertCredentials(command[1], command[2], command[3]);
                        textArea.appendText("\n\npayment successful\n");
                    }
                    else if(this.command[0].equals("seeSales")){
                        this.showSales(this.sellerBoundary.getGamesToSend());
                    }
               }
        } catch (SyntaxErrorException | PersistencyErrorException | CheckFailedException | GmailException |
                 FiltersException | NoGamesFoundException | NumberFormatException | CopiesException | RefundException |
                 GameSoldOutException e){
                    textArea.appendText("\n\n" + e.getMessage());
        }
    }

    private void showVideogames(List<VideogameBean> videogames){
        for(VideogameBean game: videogames){
            String lineToShow = game.getId() + " " + game.getName() + " " + game.getOwnerBean().getPriceBean() + "€\n";
            textArea.appendText(lineToShow);
        }
    }

    private void seeVideogame(int index){
        this.gameSelected = this.customerBoundary.getGamesFound().get(index);
        FiltersBean filters = this.customerBoundary.getFiltersBean();
        OwnerBean seller = this.gameSelected.getOwnerBean();
        String textToShow = this.gameSelected.getName() + "\n" +
                "Filters: " + filters.getConsoleBean() + " " + filters.getOnlineBean() + " " + filters.getCategoryBean() + "\n"
                + "Price per copy: " + seller.getPriceBean() + "€ " + "Copies: " + seller.getNumberOfCopiesBean() + "\n"
                + seller.getSpecificAttributeBean() + "\n";
        textArea.appendText(textToShow);
    }

    private void showShoppingCart(){
        ShoppingCartBean cartBean = this.customerBoundary.getShoppingCartBean();
        for(VideogameBean game: cartBean.getItems()){
            String lineToShow = game.getId() + " " + game.getName() + " " + game.getOwnerBean().getNameBean() + " " + game.getOwnerBean().getEmailBean() + " " + game.getOwnerBean().getNumberOfCopiesBean() + " " + game.getOwnerBean().getPriceBean() * game.getOwnerBean().getNumberOfCopiesBean() + "\n";
            textArea.appendText(lineToShow);
        }
        textArea.appendText("Total price: " + cartBean.getTotalCost());
    }

    private void showSales(List<VideogameBean> gamesSold){
        for(VideogameBean game: gamesSold){
            String[] components ={game.getId(), game.getName(), game.getOwnerBean().getNameBean(), game.getOwnerBean().getEmailBean(), game.getOwnerBean().getSpecificAttributeBean(), String.valueOf(game.getOwnerBean().getNumberOfCopiesBean()), String.valueOf(game.getOwnerBean().getPriceBean())};
            String line = String.join(" ", components) + "\n";
            textArea.appendText(line);
        }
    }


    @Override
    public void start(Stage stage) throws IOException, PersistencyErrorException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/org/application/gameshelfapp/GUI2/Access-Page.fxml"));
        Parent root = fxmlLoader.load();

        this.stage = stage;

        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);
        stage.setTitle("Gameshelfapp");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.setText("digit <login email password>\n or <register username email password typeOfClient(customer or seller)>");
        try {
            this.userLogInBoundary = new UserLogInBoundary();
        } catch (PersistencyErrorException e) {
            System.exit(1);
        }
    }

}
