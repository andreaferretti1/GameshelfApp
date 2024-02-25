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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GamesFoundPageController implements Initializable {

    @FXML
    private TableView<VideogameBean> gamesFound;
    @FXML
    private TableColumn<VideogameBean, String> gameName;
    @FXML
    private TableColumn<VideogameBean, String> gameCost;
    @FXML
    private TableColumn<VideogameBean, String> seeGame;
    private CustomerBoundary customerBoundary;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCustomerBoundary(CustomerBoundary customerBoundary){
        this.customerBoundary = customerBoundary;
    }

    public static void start(Stage myStage, CustomerBoundary boundary) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(GamesFoundPageController.class.getResource("/org/application/gameshelfapp/GUI/Games-Found-Page.fxml"));
        Parent root = fxmlLoader.load();

        GamesFoundPageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setCustomerBoundary(boundary);

        Scene scene = new Scene(root, 1440, 786);
        myStage.setScene(scene);
        myStage.show();
    }
    @FXML
    private void goToHomePage(MouseEvent event){
        try {
            HomePageController.start(this.stage, this.customerBoundary.getUserBean());
        } catch (IOException e) {
            System.exit(1);
        }
    }

    @FXML
    private void goToSearchPage(MouseEvent event){
        try{
            SearchPageController.start(this.stage, this.customerBoundary);
        } catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    private void goToShoppingCart(){
        try{
            ShoppingCartPageController.start(this.stage, this.customerBoundary, "GamesFoundPage");
        } catch(IOException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<VideogameBean> gamesList = this.customerBoundary.getGamesFound();
        ObservableList<VideogameBean> gamesToShow = FXCollections.observableList(gamesList);

        gameName.setCellValueFactory(new PropertyValueFactory<VideogameBean, String>("name"));
        gameCost.setCellValueFactory(cellData -> new SimpleStringProperty(Float.toString(cellData.getValue().getSellerBean().getPrice())));

        seeGame.setCellFactory( param -> new CustomTableCellButton(gamesToShow));
        gamesFound.setItems(gamesToShow);
    }

    private class CustomTableCellButton extends TableCell<VideogameBean, String>{
        private final Button button;

        private CustomTableCellButton(ObservableList<VideogameBean> gamesToShow){
            button = new Button("See description");
            button.setPrefWidth(134);
            button.setStyle("-fx-background-color:  #2E60E1; -fx-text-fill: white; -fx-background-radius: 60");
            button.setOnMouseClicked(event -> {
                try {
                    GameInfoPageController.seeVideogame(GamesFoundPageController.this.stage, GamesFoundPageController.this.customerBoundary, gamesToShow.get(getIndex()));
                } catch (IOException e) {
                    ErrorPageController.displayErrorWindow("Couldn't access to videogame information");
                }
            });
        }
    }
}
