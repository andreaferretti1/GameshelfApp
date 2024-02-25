package org.application.gameshelfapp.buyvideogames.graphicControllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.ShoppingCartBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.CopiesException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingCartPageController implements Initializable{

    private Stage stage;
    private CustomerBoundary customerBoundary;
    private String formerPage;

    @FXML
    private TableView<VideogameBean> cart;
    @FXML
    private TableColumn<VideogameBean, String> copies;
    @FXML
    private TableColumn<VideogameBean, String> gameName;
    @FXML
    private TableColumn<VideogameBean, String> price;
    @FXML
    private TableColumn<VideogameBean, String> sellerEmail;
    @FXML
    private TableColumn<VideogameBean, String> sellerName;
    @FXML
    private TableColumn<VideogameBean, VideogameBean> copiesToDelete;
    @FXML
    private TableColumn<VideogameBean, VideogameBean> delete;
    @FXML
    private Label totalCost;



    public void setFormerPage(String page){
        this.formerPage = page;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setCustomerBoundary(CustomerBoundary boundary){
        this.customerBoundary = boundary;
    }


    @FXML
    private void goBack(){
        try{
            if(this.formerPage.equals("SearchPage")) SearchPageController.start(this.stage, this.customerBoundary);
            else if(this.formerPage.equals("GamesFoundPage")) GamesFoundPageController.start(this.stage, this.customerBoundary);
            else SearchPageController.start(this.stage, this.customerBoundary);
        } catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    private void goToSearchPage(){
        try {
            SearchPageController.start(this.stage, this.customerBoundary);
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @FXML
    private void goToHomePage(){
        try{
            HomePageController.start(this.stage, this.customerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }

     @FXML
     private void pay(){
         try {
             CredentialsPageController.start(new Stage(), this.customerBoundary);
         } catch (IOException e) {
             System.exit(1);
         }

     }
    public static void start(Stage myStage, CustomerBoundary boundary, String formerPage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(ShoppingCartBean.class.getResource("/org/application/gameshelfapp/GUI/ShoppingCart-Page.fxml"));
        Parent root = fxmlLoader.load();

        ShoppingCartPageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setCustomerBoundary(boundary);
        controller.setFormerPage(formerPage);
        Scene scene = new Scene(root, 1449, 768);
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ShoppingCartBean cartBean = this.customerBoundary.getShoppingCartBean();
        List<VideogameBean> gamesInCart = cartBean.getItems();
        List<Integer> quantities = cartBean.getQuantities();

        ObservableList<VideogameBean> gamesToShow = FXCollections.observableList(gamesInCart);

        gameName.setCellValueFactory(new PropertyValueFactory<VideogameBean, String>("name"));

        sellerName.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSellerBean().getPrice())));

        sellerEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSellerBean().getEmail()));

        copies.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(quantities.get(gamesInCart.indexOf(cellData.getValue())))));

        price.setCellValueFactory(cellData -> {
          int copies = quantities.get(gamesInCart.indexOf(cellData.getValue()));
          float totalPrice = cellData.getValue().getSellerBean().getPrice() * copies;
          return new SimpleStringProperty(Float.toString(totalPrice));
        });

        delete.setCellFactory((param) -> new TableCell<VideogameBean, VideogameBean>(){
            private final Button deleteButton = new Button("delete");

            {
                deleteButton.setPrefWidth(117);
                deleteButton.setStyle("-fx-text-fill: white; -fx-background-color: #2E60E1; -fx-background-radius: 60");

                deleteButton.setOnMouseClicked(event -> {
                    TableRow<VideogameBean> row = getTableRow();
                    if(row != null){
                        ObservableList<Node> cells = row.getChildrenUnmodifiable();
                        VideogameBean gameInRow = row.getItem();
                        TextField copies = (TextField) cells.get(5);

                        try {
                            ShoppingCartPageController.this.customerBoundary.removeCopiesFromCart(gameInRow.getId(), copies.getText());
                            gamesToShow.setAll(ShoppingCartPageController.this.customerBoundary.getShoppingCartBean().getItems());
                        } catch (SyntaxErrorException | CopiesException e) {
                            ErrorPageController.displayErrorWindow(e.getMessage());
                        }
                    }
                });
            }


            @Override
            protected void updateItem(VideogameBean gameBean, boolean empty){
                super.updateItem(gameBean, empty);
                if(empty){
                    setGraphic(null);
                } else{
                    setGraphic(deleteButton);
                }
            }
        });

        copiesToDelete.setCellFactory(param -> new TableCell<VideogameBean, VideogameBean>(){
            private final TextField text = new TextField();

            {
                text.setPromptText("Copies to delete");
                text.setPrefWidth(112);
                text.setStyle("-fx-background-radius: 60; -fx-prompt-text-fill: #2E60E1");
            }

            @Override
            protected void updateItem(VideogameBean gameBean, boolean empty){
                super.updateItem(gameBean, empty);
                if(empty){
                    setGraphic(null);
                } else{
                    setGraphic(text);
                }
            }
        });
        cart.setItems(gamesToShow);
    }
}
