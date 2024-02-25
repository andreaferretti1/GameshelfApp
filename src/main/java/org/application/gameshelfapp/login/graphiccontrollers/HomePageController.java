package org.application.gameshelfapp.login.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.graphiccontrollers.SearchPageController;
import org.application.gameshelfapp.login.bean.UserBean;


import java.io.IOException;

public class HomePageController {

    private Stage stage;
    private UserBean userBean;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public static void start(Stage myStage, UserBean userBean) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomePageController.class.getResource("/org/application/gameshelfapp/GUI/Home-Page.fxml"));
        Parent root = fxmlLoader.load();
        HomePageController controller = fxmlLoader.getController();
        controller.setUserBean(userBean);
        controller.setStage(myStage);
        Scene scene = new Scene(root, 1440, 768);
        myStage.setScene(scene);
        myStage.show();
    }

    @FXML
    private void goToSearchPage(MouseEvent event){
        try{
            SearchPageController.start(this.stage, new CustomerBoundary(this.userBean));
        } catch (IOException e){
            System.exit(1);
        }
    }
}
