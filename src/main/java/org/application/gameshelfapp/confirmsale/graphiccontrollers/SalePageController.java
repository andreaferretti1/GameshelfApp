package org.application.gameshelfapp.confirmsale.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.boundary.SellerBoundary;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.graphiccontrollers.SellingGameCataloguePageController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalePageController implements Initializable {

    @FXML
    private VBox gamesSold;
    private static SellerBoundary sellerBoundary;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setSellerBoundary(SellerBoundary sellerBoundary) {
        SalePageController.sellerBoundary = sellerBoundary;
    }

    @FXML
    private void goToHomePage() {
        try {
            HomePageController.start(this.stage, SalePageController.sellerBoundary.getUserBean());
        } catch (IOException e) {
            System.exit(1);
        }
    }
    @FXML
    private void goToSellGame(){
        try{
            SellingGameCataloguePageController.start(this.stage, new SellerAddGamesBoundary(SalePageController.sellerBoundary.getUserBean()));
        } catch(IOException | WrongUserTypeException e){
            System.exit(1);
        }
    }

    public static void start(Stage myStage, SellerBoundary boundary) throws IOException {
        SalePageController.setSellerBoundary(boundary);
        FXMLLoader fxmlLoader = new FXMLLoader(SalePageController.class.getResource("/org/application/gameshelfapp/GUI/Sale-Page.fxml"));
        Parent root = fxmlLoader.load();

        SalePageController controller = fxmlLoader.getController();
        controller.setStage(myStage);

        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SalePageController.sellerBoundary.getGamesToSend();
            List<SaleBean> gamesSoldBean = SalePageController.sellerBoundary.getSalesBean();
            this.showSales(gamesSoldBean);
        } catch (PersistencyErrorException | WrongUserTypeException e) {
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    private HBox createHBox(SaleBean saleBean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/hbox-salePage.fxml"));
        HBox hBox = fxmlLoader.load();
        ((Label) hBox.lookup("#id")).setText(String.valueOf(saleBean.getIdBean()));
        ((Label) hBox.lookup("#gameName")).setText(saleBean.getGameSoldBean().getName());
        ((Label) hBox.lookup("#gamePlatform")).setText(saleBean.getGameSoldBean().getPlatformBean());
        ((Label) hBox.lookup("#copies")).setText(String.valueOf(saleBean.getGameSoldBean().getCopiesBean()));
        ((Label) hBox.lookup("#price")).setText(saleBean.getGameSoldBean().getPriceBean() + " €");
        ((Label) hBox.lookup("#name")).setText(saleBean.getCredentialsBean().getNameBean());
        ((Label) hBox.lookup("#address")).setText(saleBean.getCredentialsBean().getAddressBean());
        ((Label) hBox.lookup("#email")).setText(saleBean.getCredentialsBean().getEmailBean());
        (hBox.lookup("#button")).setOnMouseClicked(event -> {
            try {
                HBox sourceHBox = (HBox) ((Button)event.getSource()).getParent();
                SalePageController.sellerBoundary.sendGame(Long.parseLong(((Label) sourceHBox.lookup("#id")).getText()));
                SalePageController.sellerBoundary.getGamesToSend();
                this.showSales(SalePageController.sellerBoundary.getSalesBean());
            } catch (ConfirmDeliveryException | GmailException | WrongSaleException | PersistencyErrorException |
                     WrongUserTypeException e) {
                ErrorPageController.displayErrorWindow(e.getMessage());
            }
        });
        return hBox;
    }

    private void showSales(List<SaleBean> sales){
        try {
            for (SaleBean saleBean : sales) {
                saleBean.getInformationFromModel();
                this.gamesSold.getChildren().add(this.createHBox(saleBean));
            }
        } catch (IOException e) {
            ErrorPageController.displayErrorWindow("Couldn't show sales.");
        }
    }
}
