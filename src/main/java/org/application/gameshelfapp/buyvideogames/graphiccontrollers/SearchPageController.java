package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable{

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private ChoiceBox<String> platformChoiceBox;
    @FXML
    private VBox gamesInCatalogue;

    private static CustomerBoundary customerBoundary;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setCustomerBoundary(CustomerBoundary customerBoundary) {
        SearchPageController.customerBoundary = customerBoundary;
    }

    @FXML
    private void searchVideogame(MouseEvent event){
        String game = this.nameField.getText();
        if(game.isEmpty()) game = null;
        try{
            SearchPageController.customerBoundary.insertFilters(game, this.platformChoiceBox.getValue(), this.categoryChoiceBox.getValue());
            this.showGamesFound(SearchPageController.customerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, SearchPageController.customerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }

    private void seeGameInfo(MouseEvent event){
        try {
            Button button = (Button) event.getSource();
            HBox hBox = (HBox)button.getParent();
            for(VideogameBean videogameBean: SearchPageController.customerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean()){
                if(videogameBean.getName().equals(((Label) hBox.lookup("#gameName")).getText()) && videogameBean.getPlatformBean().equals(((Label)hBox.lookup("#platform")).getText())){
                    GameInfoPageController.seeVideogame(SearchPageController.this.stage, SearchPageController.customerBoundary, videogameBean);
                }
            }
        } catch (IOException e) {
            ErrorPageController.displayErrorWindow("Couldn't access to videogame information");
        }
    }
    public static void start(Stage myStage, CustomerBoundary customerBoundary) throws IOException, WrongUserTypeException {
        SearchPageController.setCustomerBoundary(customerBoundary);
        FXMLLoader fxmlLoader = new FXMLLoader(SearchPageController.class.getResource("/org/application/gameshelfapp/GUI/Search-Page.fxml"));
        Parent root = fxmlLoader.load();

        SearchPageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            this.categoryChoiceBox.getItems().setAll(SearchPageController.customerBoundary.getCategoriesFilters());
            this.platformChoiceBox.getItems().setAll(SearchPageController.customerBoundary.getConsoleFilters());
            SellingGamesCatalogueBean sellingGamesCatalogueBean = SearchPageController.customerBoundary.getSellingGamesCatalogueBean();
            if(sellingGamesCatalogueBean != null) this.showGamesFound(sellingGamesCatalogueBean.getSellingGamesBean());
        } catch(PersistencyErrorException | IOException e){
            System.exit(1);
        }
    }

    private void showGamesFound(List<VideogameBean> games) throws IOException{
        this.gamesInCatalogue.getChildren().clear();
        for(VideogameBean videogameBean: games){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/Hbox-gameOnSale.fxml"));
            HBox hBox = fxmlLoader.load();
            ((Label) hBox.lookup("#gameName")).setText(videogameBean.getName());
            ((Label) hBox.lookup("#platform")).setText(videogameBean.getPlatformBean());
            ((Label) hBox.lookup("#price")).setText(String.valueOf(videogameBean.getPriceBean()));
            (hBox.lookup("#seeGame")).setOnMouseClicked(event -> this.seeGameInfo(event));
            hBox.prefWidthProperty().bind(this.gamesInCatalogue.widthProperty());
            this.gamesInCatalogue.getChildren().add(hBox);
        }
    }
}