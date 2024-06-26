package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.application.gameshelfapp.buyvideogames.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalePageController implements Initializable {

    @FXML
    private ListView<HBox> gamesSold;
    private SellerBoundary sellerBoundary;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSellerBoundary(SellerBoundary sellerBoundary) {
        this.sellerBoundary = sellerBoundary;
    }

    @FXML
    private void goToHomePage(){
        try{
            HomePageController.start(this.stage, this.sellerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }
    public void start(Stage myStage, SellerBoundary boundary) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/org/application/gameshelfapp/GUI/Sale-Page.fxml"));
        Parent root = fxmlLoader.load();

        SalePageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setSellerBoundary(boundary);

        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<HBox> objects = FXCollections.observableArrayList();
        gamesSold.setCellFactory(new Callback<ListView<HBox>, ListCell<HBox>>() {
            @Override
            public ListCell<HBox> call(ListView<HBox> hBoxListView) {
                return new MyCell();
            }
        });
        List<SaleBean> gamesSoldBean = this.sellerBoundary.getSalesBean();
        for(SaleBean saleBean : gamesSoldBean){
            saleBean.getInformationFromModel();
            objects.add(this.createHBox(saleBean));
        }
        gamesSold.setItems(objects);

    }
    private HBox createHBox(SaleBean saleBean){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hbox-salePage.fxml"));
            HBox hBox = fxmlLoader.load();
            ((Label) fxmlLoader.getNamespace().get("gameName")).setText(saleBean.getGameSoldBean().getName());
            ((Label) fxmlLoader.getNamespace().get("gamePlatform")).setText(saleBean.getNameBean());
            ((Label) fxmlLoader.getNamespace().get("copies")).setText(String.valueOf(saleBean.getGameSoldBean().getCopiesBean()));
            ((Label) fxmlLoader.getNamespace().get("name")).setText(saleBean.getNameBean());
            ((Label) fxmlLoader.getNamespace().get("address")).setText(saleBean.getAddressBean());
            ((Label) fxmlLoader.getNamespace().get("email")).setText(saleBean.getEmailBean());
            if (saleBean.getStateBean().equals("To confirm")) {
                ((Button) fxmlLoader.getNamespace().get("button")).setOnMouseClicked(event -> {
                    try {
                        this.sellerBoundary.sendGame(gamesSold.getSelectionModel().getSelectedIndex());
                    } catch (ConfirmDeliveryException | GmailException e) {
                        ErrorPageController.displayErrorWindow(e.getMessage());
                    }
                });
            } else {
                ((Button) fxmlLoader.getNamespace().get("button")).setDisable(true);
            }
            return hBox;
        } catch(IOException e){
            ErrorPageController.displayErrorWindow("Couldn't show sales");
        }
        return null;
    }
}
class MyCell extends ListCell<HBox>{
    @Override
    protected void updateItem(HBox hBox, boolean b) {
        super.updateItem(hBox, b);

        if(hBox == null || b){
            setGraphic(null);
        }
        if(hBox != null){
            setGraphic(hBox);
        }
    }
}