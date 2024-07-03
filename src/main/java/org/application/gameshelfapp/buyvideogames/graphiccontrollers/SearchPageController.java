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
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;
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
    private TableView<VideogameBean> gamesFound;
    @FXML
    private TableColumn<VideogameBean, String> gameName;
    @FXML
    private TableColumn<VideogameBean, String> gameCost;
    @FXML
    private TableColumn<VideogameBean, String> seeGame;
    private final String[] category = {"Action", "Adventure", "Arcade", "Simulation", "Sport"};
    private final String[] platform = {"Playstation 4", "Playstation 5", "Xbox Series X", "Xbox Series S", "Pc"};

    private CustomerBoundary customerBoundary;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCustomerBoundary(CustomerBoundary customerBoundary) {
        this.customerBoundary = customerBoundary;
    }

    @FXML
    private void searchVideogame(MouseEvent event){
        String game = nameField.getText();
        try{
            this.customerBoundary.insertFilters(game, this.platformChoiceBox.getValue(), this.categoryChoiceBox.getValue());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
        this.customerBoundary.getVideogamesFoundBean().getInformationFromModel();
        this.showGamesFound(customerBoundary.getVideogamesFoundBean().getVideogamesFoundBean());
    }

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, this.customerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }
    public static void start(Stage myStage, CustomerBoundary boundary) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SearchPageController.class.getResource("/org/application/gameshelfapp/GUI/Search-Page.fxml"));
        Parent root = fxmlLoader.load();

        SearchPageController controller = fxmlLoader.getController();
        controller.setStage(myStage);
        controller.setCustomerBoundary(boundary);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.categoryChoiceBox.getItems().setAll(category);
        this.platformChoiceBox.getItems().setAll(platform);
        VideogamesFoundBean videogamesFoundBean = this.customerBoundary.getVideogamesFoundBean();
        if(videogamesFoundBean != null) this.showGamesFound(videogamesFoundBean.getVideogamesFoundBean());
    }

    private void showGamesFound(List<VideogameBean> games){
        ObservableList<VideogameBean> gamesToShow = FXCollections.observableList(games);

        this.gameName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.gameCost.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPriceBean() + "€"));

        this.seeGame.setCellFactory( param -> new SearchPageController.CustomTableCellButton(gamesToShow));
        this.gamesFound.setItems(gamesToShow);
    }

    private class CustomTableCellButton extends TableCell<VideogameBean, String> {
        private final Button button;

        public CustomTableCellButton(ObservableList<VideogameBean> gamesToShow){
            button = new Button("See description");
            button.setPrefWidth(134);
            button.setStyle("-fx-background-color:  #2E60E1; -fx-text-fill: white; -fx-background-radius: 60");
            button.setOnMouseClicked(event -> {
                try {
                    GameInfoPageController.seeVideogame(SearchPageController.this.stage, SearchPageController.this.customerBoundary, gamesToShow.get(getIndex()));
                } catch (IOException e) {
                    ErrorPageController.displayErrorWindow("Couldn't access to videogame information");
                }
            });
        }
    }
}
