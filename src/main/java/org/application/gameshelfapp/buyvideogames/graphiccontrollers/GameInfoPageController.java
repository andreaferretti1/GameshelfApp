package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameInfoPageController implements Initializable {

   @FXML
   private Label gameName;
   @FXML
   private Label console;
   @FXML
   private Label category;
   @FXML
   private Label availableCopies;
   @FXML
   private Label price;
   @FXML
   private TextArea description;
   @FXML
   private TextField copiesToBuy;

   private Stage stage;
   private static CustomerBoundary customerBoundary;
   private static VideogameBean gameBean;

   public static void setGameBean(VideogameBean gameBean){
        GameInfoPageController.gameBean = gameBean;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setCustomerBoundary(CustomerBoundary customerBoundary) {
        GameInfoPageController.customerBoundary = customerBoundary;
    }

    @FXML
    private void goToGameList(MouseEvent event){
        try {
            SearchPageController.start(this.stage, GameInfoPageController.customerBoundary);
        } catch (IOException | WrongUserTypeException e) {
            System.exit(-1);
        }
    }

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, GameInfoPageController.customerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(-1);
        }
    }
    @FXML
    private void buy(){
        GameInfoPageController.gameBean.setCopiesBean(Integer.parseInt(this.copiesToBuy.getText()));
        GameInfoPageController.customerBoundary.setGameToBuy(GameInfoPageController.gameBean);
        try{
            CredentialsPageController.start(this.stage, GameInfoPageController.customerBoundary);
        } catch(IOException e){
            ErrorPageController.displayErrorWindow("Couldn't buy videogame");
        }
    }
    public static void seeVideogame(Stage stage, CustomerBoundary boundary, VideogameBean gameBean) throws IOException {
        GameInfoPageController.setCustomerBoundary(boundary);
        GameInfoPageController.setGameBean(gameBean);
        FXMLLoader fxmlLoader = new FXMLLoader(GameInfoPageController.class.getResource("/org/application/gameshelfapp/GUI/Game-Info-Page.fxml"));
        Parent root = fxmlLoader.load();

        GameInfoPageController controller = fxmlLoader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.console.setText(GameInfoPageController.gameBean.getPlatformBean());
        this.category.setText(GameInfoPageController.gameBean.getCategoryBean());
        this.price.setText(String.valueOf(GameInfoPageController.gameBean.getPriceBean()) + " €");
        this.availableCopies.setText(String.valueOf(GameInfoPageController.gameBean.getCopiesBean()));
        this.gameName.setText(GameInfoPageController.gameBean.getName());
        this.description.setText(GameInfoPageController.gameBean.getDescriptionBean());
    }
}
