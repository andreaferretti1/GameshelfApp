package org.application.gameshelfapp.sellvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SellingGameInfoPageController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Label platformLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label copiesLabel;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField copiesNumberField;
    private SellerAddGamesBoundary sellerBoundary;

    private static VideogameBean gameBean;
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSellerAddGamesBoundary(SellerAddGamesBoundary sellerBoundary){this.sellerBoundary = sellerBoundary;}

    public static void setGameBean(VideogameBean bean){ SellingGameInfoPageController.gameBean = bean; }

    private VideogameBean createVideogameBean(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName(this.titleLabel.getText());
        videogameBean.setCategoryBean(this.categoryLabel.getText());
        videogameBean.setPlatformBean(this.platformLabel.getText());
        videogameBean.setCopiesBean(Integer.parseInt(this.copiesNumberField.getText()));
        videogameBean.setPriceBean(Float.parseFloat(this.priceTextField.getText()));
        videogameBean.setDescriptionBean(this.descriptionArea.getText());
        return videogameBean;
    }
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
        } catch (IOException | WrongUserTypeException e){
            ErrorPageController.displayErrorWindow("Couldn't load catalogue");
        }
    }

    @FXML
    private void modifyVideogameInfo(MouseEvent event){
        try{
            VideogameBean videogameBean = this.createVideogameBean();
            this.sellerBoundary.updateSellingGame(videogameBean);
            SellingGameCataloguePageController.start(this.stage, this.sellerBoundary);
        } catch(PersistencyErrorException | WrongUserTypeException | CheckFailedException e){
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
            VideogameBean videogameBean = this.createVideogameBean();
            this.sellerBoundary.removeSellingGames(videogameBean);
            SellingGameCataloguePageController.start(this.stage, this.sellerBoundary);
        } catch(PersistencyErrorException | GameSoldOutException | WrongUserTypeException | CheckFailedException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch(NoGameInCatalogueException e){
            ErrorPageController.displayErrorWindow("Couldn't find the game");
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't load updated catalogue");
        }
    }

    public static void start(Stage myStage, SellerAddGamesBoundary sellerBoundary, VideogameBean videogameBean) throws IOException{
        SellingGameInfoPageController.setGameBean(videogameBean);
        FXMLLoader fxmlLoader = new FXMLLoader(SellingGameCataloguePageController.class.getResource("/org/application/gameshelfapp/GUI/Selling-Game-Info-Page.fxml"));
        Parent root = fxmlLoader.load();

        SellingGameInfoPageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setSellerAddGamesBoundary(sellerBoundary);
        Scene scene = new Scene(root,1440,768);
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.titleLabel.setText(SellingGameInfoPageController.gameBean.getName());
        this.categoryLabel.setText(SellingGameInfoPageController.gameBean.getCategoryBean());
        this.platformLabel.setText(SellingGameInfoPageController.gameBean.getPlatformBean());
        this.descriptionArea.setText(SellingGameInfoPageController.gameBean.getDescriptionBean());
        this.copiesLabel.setText(String.valueOf(SellingGameInfoPageController.gameBean.getCopiesBean()));
        this.priceTextField.setText(String.valueOf(SellingGameInfoPageController.gameBean.getPriceBean()));
    }
}
