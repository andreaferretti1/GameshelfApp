package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.application.gameshelfapp.StartingPageController;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.graphiccontrollers.SearchPageController;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.graphiccontrollers.SellingGameCataloguePageController;
import org.application.gameshelfapp.signemployee.graphiccontrollers.SignEmployeePageController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Label accountName;
    private Stage stage;
    private UserBean userBean;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public static void start(Stage myStage, UserBean userBean) throws IOException {
        FXMLLoader fxmlLoader;
        switch (userBean.getTypeOfUser()){
            case("Customer") -> fxmlLoader = new FXMLLoader(HomePageController.class.getResource("/org/application/gameshelfapp/GUI/Home-Page-Customer.fxml"));

            case("Seller") -> fxmlLoader = new FXMLLoader(HomePageController.class.getResource("/org/application/gameshelfapp/GUI/Home-Page-Seller.fxml"));

            case("Admin") -> fxmlLoader = new FXMLLoader(HomePageController.class.getResource("/org/application/gameshelfapp/GUI/Home-Page-Admin.fxml"));
            default -> throw new IOException("File not found");
        }
        Parent root = fxmlLoader.load();
        HomePageController controller = fxmlLoader.getController();
        controller.setUserBean(userBean);
        controller.setStage(myStage);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { this.accountName.setText(this.userBean.getUsername());}

    @FXML
    private void logout(MouseEvent event){
        StartingPageController startingPageController = new StartingPageController();
        startingPageController.start(this.stage);
    }
    @FXML
    private void goToSearchPage(MouseEvent event){
        try{
            SearchPageController.start(this.stage, new CustomerBoundary(this.userBean));
        } catch (IOException | WrongUserTypeException e){
            System.exit(1);
        }
    }
    @FXML
    private void goToSellingCatalogue(MouseEvent event){
        try{
            SellingGameCataloguePageController.start(this.stage,new SellerAddGamesBoundary(this.userBean));
        } catch (WrongUserTypeException | IOException e){
            System.exit(1);
        }
    }
    @FXML
    private void goToSignEmployeePage(MouseEvent event){
        try{
            SignEmployeePageController.start(this.stage, this.userBean);
        } catch (IOException | WrongUserTypeException e){
            System.exit(1);
        }
    }


}
