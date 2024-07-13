package org.application.gameshelfapp.sellvideogames.graphiccontrollers;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SellingGameCataloguePageController implements Initializable{

    @FXML
    private TextField titleField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private ChoiceBox<String> platformChoiceBox;
    @FXML
    private VBox gameInCatalogue;
    private static SellerAddGamesBoundary sellerBoundary;
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public static void setSellerAddGamesBoundary(SellerAddGamesBoundary sellerBoundary){ SellingGameCataloguePageController.sellerBoundary = sellerBoundary;}
    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, SellingGameCataloguePageController.sellerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    private void addNewGameForSale(MouseEvent event){
            AddGameInfoPageController.start(this.stage, SellingGameCataloguePageController.sellerBoundary);
    }

    @FXML
    private void searchGameByFilters(MouseEvent event){
        try {
            String title = this.titleField.getText();
            FiltersBean filtersBean = new FiltersBean();
            if (title.isEmpty()){ title = null; }
            filtersBean.setNameBean(title);
            filtersBean.setCategoryBean(this.categoryChoiceBox.getValue());
            filtersBean.setConsoleBean(this.platformChoiceBox.getValue());
            SellingGameCataloguePageController.sellerBoundary.getSellingCatalogue(filtersBean);
            this.fillCatalogue();
        } catch (PersistencyErrorException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch (NoGameInCatalogueException e){
            AddGameInfoPageController.start(this.stage, SellingGameCataloguePageController.sellerBoundary);
            ErrorPageController.displayErrorWindow("No game found");
        }
    }
    public static void start(Stage myStage, SellerAddGamesBoundary sellerBoundary) throws IOException, WrongUserTypeException {
        SellingGameCataloguePageController.setSellerAddGamesBoundary(sellerBoundary);
        FXMLLoader fxmlLoader = new FXMLLoader(SellingGameCataloguePageController.class.getResource("/org/application/gameshelfapp/GUI/Selling-Catalogue-Page.fxml"));
        Parent root = fxmlLoader.load();

        SellingGameCataloguePageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        Scene scene = new Scene(root,1440,768);
        myStage.setScene(scene);
        myStage.show();
    }

    private void fillCatalogue(){
        if (SellingGameCataloguePageController.sellerBoundary.getSellingGamesCatalogueBean() == null) return;
        try {
            List<VideogameBean> catalogue = SellingGameCataloguePageController.sellerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean();
            for (VideogameBean gameBean : catalogue) {
                this.gameInCatalogue.getChildren().add(this.createHBox(gameBean));
            }
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't show catalogue");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        try {
            this.categoryChoiceBox.getItems().setAll(SellingGameCataloguePageController.sellerBoundary.getCategories());
            this.platformChoiceBox.getItems().setAll(SellingGameCataloguePageController.sellerBoundary.getConsoles());
        } catch (PersistencyErrorException e) {
            System.exit(1);
        }
        this.fillCatalogue();
    }

    private HBox createHBox(VideogameBean videogameBean) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/application/gameshelfapp/GUI/hbox-sellingGamePage.fxml"));
        HBox hBox = fxmlLoader.load();
        ((Label) hBox.lookup("#titleLabel")).setText(videogameBean.getName());
        ((Label) hBox.lookup("#platformLabel")).setText(videogameBean.getPlatformBean());
        ((Label) hBox.lookup("#copiesLabel")).setText(String.valueOf(videogameBean.getCopiesBean()));
        (hBox.lookup("#showInfoButton")).setOnMouseClicked(event -> {
            try {
                for(VideogameBean gameBean: SellingGameCataloguePageController.sellerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean()) {
                    if (gameBean.getName().equals(((Label) hBox.lookup("#titleLabel")).getText()) && gameBean.getPlatformBean().equals(((Label) hBox.lookup("#platformLabel")).getText())) {
                        SellingGameInfoPageController.start(this.stage, SellingGameCataloguePageController.sellerBoundary, gameBean);
                    }
                }
            } catch (IOException e) {
                ErrorPageController.displayErrorWindow("Couldn't show the videogame");
            }
        });
        return hBox;
    }
}


