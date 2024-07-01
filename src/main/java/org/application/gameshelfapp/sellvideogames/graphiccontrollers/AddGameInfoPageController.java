package org.application.gameshelfapp.sellvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGameInfoPageController implements Initializable {
    @FXML
    private TextField videogameTitleField;
    @FXML
    private ChoiceBox<String> platformChoiceBox;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextField copiesField;
    @FXML
    private TextField priceField;
    @FXML
    private TextArea descriptionArea;
    private final String[] category = {"Action", "Adventure", "Arcade", "Simulation", "Sport"};
    private final String[] platform = {"Playstation 4", "Playstation 5", "Xbox Series X", "Xbox Series S", "Pc"};
    private SellerAddGamesBoundary sellerBoundary;
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSellerAddGamesBoundary(SellerAddGamesBoundary sellerBoundary){ this.sellerBoundary = sellerBoundary;}

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, this.sellerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    private void confirmVideogameInfo(MouseEvent event){
        try {
            VideogameBean gameBean = new VideogameBean();
            gameBean.setName(this.videogameTitleField.getText());
            gameBean.setPlatformBean(this.platformChoiceBox.getValue());
            gameBean.setCategoryBean(this.categoryChoiceBox.getValue());
            gameBean.setCopiesBean(Integer.parseInt(this.copiesField.getText()));
            gameBean.setDescriptionBean(this.descriptionArea.getText());
            this.sellerBoundary.addSellingGames(gameBean);
            SellingGameCataloguePageController.start(this.stage, this.sellerBoundary);
        } catch (PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException |
                 GmailException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't show updated catalogue");
        }
    }

    @FXML
    private void backToCatalogue(MouseEvent event) {
        try {
            SellingGameCataloguePageController.start(stage, sellerBoundary);
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't load catalogue");
        }
    }

    public static void start(Stage myStage, SellerAddGamesBoundary sellerBoundary) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(SellingGameCataloguePageController.class.getResource("/org/application/gameshelfapp/GUI/Add-GameInfo-Page.fxml"));
            Parent root = fxmlLoader.load();

            AddGameInfoPageController controller = fxmlLoader.getController();
            controller.setStage(myStage);
            controller.setSellerAddGamesBoundary(sellerBoundary);
            Scene scene = new Scene(root,1440,768);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            ErrorPageController.displayErrorWindow("Couldn't load new game page");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.categoryChoiceBox.getItems().setAll(this.category);
        this.platformChoiceBox.getItems().setAll(this.platform);
    }
}
