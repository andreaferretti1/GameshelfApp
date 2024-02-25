package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.SellerBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.CopiesException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameInfoPageController implements Initializable {

    @FXML
    private Label category;

    @FXML
    private Label console;

    @FXML
    private Text description;

    @FXML
    private Label gameName;

    @FXML
    private Label online;

    @FXML
    private Label price;

    @FXML
    private Label sellerEmail;

    @FXML
    private Label sellerName;
    @FXML
    private TextField copiesSelected;
    private Stage stage;
    private CustomerBoundary customerBoundary;
    private VideogameBean gameBean;
    private FiltersBean filters;

    private void setGameBean(VideogameBean gameBean){
        this.gameBean = gameBean;
    }
    private void setFilters(FiltersBean filters) {
        this.filters = filters;
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setCustomerBoundary(CustomerBoundary customerBoundary) {
        this.customerBoundary = customerBoundary;
    }

    @FXML
    private void goToGameList(MouseEvent event){
        try {
            GamesFoundPageController.start(this.stage, this.customerBoundary);
        } catch (IOException e) {
            System.exit(1);
        }
    }
    @FXML
    private void addGameToCart(MouseEvent event) {
        try{
            this.customerBoundary.addGameToCart(this.gameBean, this.copiesSelected.getText());
        } catch(CopiesException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    public static void seeVideogame(Stage stage, CustomerBoundary boundary, VideogameBean gameBean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameInfoPageController.class.getResource("/org/application/gameshelfapp/GUI/Game-Info-Page.fxml"));
        Parent root = fxmlLoader.load();

        GameInfoPageController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setCustomerBoundary(boundary);
        controller.setGameBean(gameBean);
        controller.setFilters(controller.customerBoundary.getFiltersBean());
        Scene scene = new Scene(root, 1440, 768);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.console.setText(this.filters.getConsole());
        this.online.setText(this.filters.getOnline());
        this.category.setText(this.filters.getCategory());

        this.gameName.setText(this.gameBean.getName());
        this.sellerName.setText(this.gameBean.getSellerBean().getName());
        this.sellerEmail.setText(this.gameBean.getSellerBean().getEmail());
        this.description.setText(this.gameBean.getSellerBean().getDescription());

    }
}
