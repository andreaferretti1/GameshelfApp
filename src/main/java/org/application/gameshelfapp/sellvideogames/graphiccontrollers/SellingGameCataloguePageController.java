package org.application.gameshelfapp.sellvideogames.graphiccontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    private ListView<HBox> gameInCatalogue;
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
    private void addNewGameForSale(MouseEvent event){
            AddGameInfoPageController.start(this.stage, this.sellerBoundary);
    }

    @FXML
    private void searchGameByFilters(MouseEvent event){
        try {
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setNameBean(this.titleField.getText());
            filtersBean.setCategoryBean(this.categoryChoiceBox.getValue());
            filtersBean.setConsoleBean(this.platformChoiceBox.getValue());
            this.sellerBoundary.getSellingCatalogue(filtersBean);
            this.fillCatalogue();
        } catch (PersistencyErrorException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        } catch (NoGameInCatalogueException e){
            AddGameInfoPageController.start(this.stage, this.sellerBoundary);
            ErrorPageController.displayErrorWindow("No game found");
        }
    }
    public static void start(Stage myStage, SellerAddGamesBoundary sellerBoundary) throws IOException, WrongUserTypeException {
        FXMLLoader fxmlLoader = new FXMLLoader(SellingGameCataloguePageController.class.getResource("/org/application/gameshelfapp/GUI/Selling-Catalogue-Page.fxml"));
        Parent root = fxmlLoader.load();

        SellingGameCataloguePageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setSellerAddGamesBoundary(sellerBoundary);
        Scene scene = new Scene(root,1440,768);
        myStage.setScene(scene);
        myStage.show();
    }

    private void fillCatalogue(){
        if (this.sellerBoundary.getSellingGamesCatalogueBean() == null) return;
        try {
            ObservableList<HBox> objects = FXCollections.observableArrayList();
            List<VideogameBean> catalogue = this.sellerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean();
            for (VideogameBean gameBean : catalogue) {
                objects.add(createHBox(gameBean));
            }
            this.gameInCatalogue.setItems(objects);
        } catch (IOException e){
            ErrorPageController.displayErrorWindow("Couldn't show catalogue");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        this.gameInCatalogue.setCellFactory(new Callback<ListView<HBox>, ListCell<HBox>>() {
            @Override
            public ListCell<HBox> call(ListView<HBox> hBoxListView) {
                return new InfoCell();
            }
        });
        this.categoryChoiceBox.getItems().setAll(this.category);
        this.platformChoiceBox.getItems().setAll(this.platform);
        this.fillCatalogue();
    }

    private HBox createHBox(VideogameBean videogameBean) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hbox-sellingGamePage.fxml"));
        HBox hBox = fxmlLoader.load();
        ((Label) fxmlLoader.getNamespace().get("titleLabel")).setText(videogameBean.getName());
        ((Label) fxmlLoader.getNamespace().get("platformLabel")).setText(videogameBean.getPlatformBean());
        ((Label) fxmlLoader.getNamespace().get("copiesLabel")).setText(String.valueOf(videogameBean.getCopiesBean()));
        ((Button) fxmlLoader.getNamespace().get("showInfoButton")).setOnMouseClicked(event -> {
            try {
                SellingGameInfoPageController.start(this.stage, this.sellerBoundary);
            } catch (IOException e) {
                ErrorPageController.displayErrorWindow("Couldn't show the videogame");
            }
        });
        return hBox;
    }
}
class InfoCell extends ListCell<HBox>{
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


