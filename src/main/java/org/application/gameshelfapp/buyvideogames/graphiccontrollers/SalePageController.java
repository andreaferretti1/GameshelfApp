package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SalePageController implements Initializable {

    @FXML
    private TableView<VideogameBean> gamesToSend;
    @FXML
    private TableColumn<VideogameBean, String> gameName;
    @FXML
    private TableColumn<VideogameBean, String> customerName;
    @FXML
    private TableColumn<VideogameBean, String> customerEmail;
    @FXML
    private TableColumn<VideogameBean, String> customerAddress;
    @FXML
    private TableColumn<VideogameBean, String> copiesToSend;
    @FXML
    private TableColumn <VideogameBean, String> priceSold;
    @FXML
    private TableColumn<VideogameBean, VideogameBean> sendButton;

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

        try {
            ObservableList<VideogameBean> gamesSold = FXCollections.observableList(this.sellerBoundary.getGamesToSend());

            gameName.setCellValueFactory(new PropertyValueFactory<VideogameBean, String>("name"));
            customerName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerBean().getNameBean()));
            customerEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerBean().getEmailBean()));
            customerAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwnerBean().getSpecificAttributeBean()));
            copiesToSend.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOwnerBean().getNumberOfCopiesBean())));
            priceSold.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOwnerBean().getPriceBean())));
            sendButton.setCellFactory(cellData -> new CustomTableCellButton(gamesSold));

            gamesToSend.setItems(gamesSold);
        } catch (PersistencyErrorException e) {
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    private class CustomTableCellButton extends TableCell<VideogameBean, VideogameBean>{

        private final Button sendButton;

        public CustomTableCellButton(ObservableList<VideogameBean> games){
            sendButton = new Button("send");
            sendButton.setStyle("-fx-background-color: #2E60E1; -fx-background-radius: 60; -fx-text-fill: white");
            sendButton.setPrefWidth(193);

            sendButton.setOnMouseClicked(event -> {
                TableRow<VideogameBean> row = getTableRow();
                VideogameBean gameBean = row.getItem();
                try {
                    SalePageController.this.sellerBoundary.sendGame(gameBean);
                    games.remove(gameBean);
                } catch (ConfirmDeliveryException | GmailException e) {
                    ErrorPageController.displayErrorWindow(e.getMessage());
                }
            });
        }

        @Override
        protected void updateItem(VideogameBean videogameBean, boolean empty) {
            super.updateItem(videogameBean, empty);
            if(empty){
                setGraphic(null);
            } else{
                setGraphic(sendButton);
            }
        }
    }
}
