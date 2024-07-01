package org.application.gameshelfapp.sellvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SellingGameInfoPageController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label platformLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private TextField copiesTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField copiesNumberField;
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
    private void backToCatalogue(MouseEvent event) {
        try {
            SellingGameCataloguePageController.start(stage, sellerBoundary);
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't load catalogue");
        }
    }

    @FXML
    private void modifyVideogameInfo(MouseEvent event){
        try{
            VideogameBean gameBean = new VideogameBean();
            gameBean.setName(this.titleLabel.getText());
            gameBean.setCategoryBean(this.categoryLabel.getText());
            gameBean.setPlatformBean(this.platformLabel.getText());
            gameBean.setCopiesBean(Integer.parseInt(this.copiesTextField.getText()));
            gameBean.setPriceBean(Float.parseFloat(this.priceTextField.getText()));
            gameBean.setDescriptionBean(this.descriptionArea.getText());
            this.sellerBoundary.updateSellingGame(gameBean);
            SellingGameCataloguePageController.start(this.stage, this.sellerBoundary);
        } catch(PersistencyErrorException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch(NoGameInCatalogueException e){
            ErrorPageController.displayErrorWindow("Couldn't find the game in catalogue");
        } catch(IOException e){
            ErrorPageController.displayErrorWindow("Couldn't load updated catalogue");
        }
    }

    @FXML
    private void removeCopies(MouseEvent event){
        try{
            VideogameBean gameBean = new VideogameBean();
            gameBean.setName(this.titleLabel.getText());
            gameBean.setCopiesBean(Integer.parseInt(this.copiesNumberField.getText()));
            gameBean.setPlatformBean(this.platformLabel.getText());
            gameBean.setCategoryBean(this.categoryLabel.getText());
            gameBean.setDescriptionBean(this.descriptionArea.getText());
            gameBean.setPriceBean(Float.parseFloat(this.priceTextField.getText()));
            this.sellerBoundary.removeSellingGames(gameBean);
            SellingGameCataloguePageController.start(this.stage, this.sellerBoundary);
        } catch(PersistencyErrorException | GameSoldOutException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch(NoGameInCatalogueException e){
            ErrorPageController.displayErrorWindow("Couldn't find the game");
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't load updated catalogue");
        }
    }

    public static void start(Stage myStage, SellerAddGamesBoundary sellerBoundary) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(SellingGameCataloguePageController.class.getResource("/org/application/gameshelfapp/GUI/Selling-Game-Info-Page.fxml"));
        Parent root = fxmlLoader.load();

        SellingGameInfoPageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setSellerAddGamesBoundary(sellerBoundary);
        Scene scene = new Scene(root,1440,768);
        myStage.setScene(scene);
        myStage.show();
    }

}
