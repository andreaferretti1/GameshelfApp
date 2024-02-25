package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.FiltersException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontrollers.ErrorPageController;
import org.application.gameshelfapp.login.graphiccontrollers.HomePageController;

import java.io.IOException;

public class SearchPageController{

    @FXML
    private TextField nameField;
    @FXML
    private CheckBox cbPlay;
    @FXML
    private CheckBox cbXbox;
    @FXML
    private CheckBox cbPc;
    @FXML
    private CheckBox cbOn;
    @FXML
    private CheckBox cbOff;
    @FXML
    private CheckBox cbSport;
    @FXML
    private CheckBox cbShoot;
    @FXML
    private CheckBox cbAdv;
    private String consoleSelected = null;
    private String playableSelected = null;
    private String genreSelected = null;
    private CustomerBoundary customerBoundary;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCustomerBoundary(CustomerBoundary customerBoundary) {
        this.customerBoundary = customerBoundary;
    }

    @FXML
    private void consoleSelected(MouseEvent event){
        CheckBox eventSource =(CheckBox)event.getSource();
        if(!cbPlay.equals(eventSource)) cbPlay.setSelected(false);
        if(!cbXbox.equals(eventSource)) cbXbox.setSelected(false);
        if(!cbPc.equals(eventSource)) cbPc.setSelected(false);
        this.consoleSelected = eventSource.getText();
    }


    @FXML
    private void playableSelected(MouseEvent event){
        CheckBox eventSource = (CheckBox)event.getSource();
        if(!cbOn.equals(eventSource)) cbOn.setSelected(false);
        if(!cbOff.equals(eventSource)) cbOff.setSelected(false);
        this.playableSelected = eventSource.getText();
    }

    @FXML
    private void genreSelected(MouseEvent event){
        CheckBox eventSource = (CheckBox)event.getSource();
        if(!cbSport.equals(eventSource)) cbSport.setSelected(false);
        if(!cbShoot.equals(eventSource)) cbShoot.setSelected(false);
        if(!cbAdv.equals(eventSource)) cbAdv.setSelected(false);
        this.genreSelected = eventSource.getText();
    }

    @FXML
    private void searchVideogame(MouseEvent event){
        String gameName = nameField.getText();
        try{
            this.customerBoundary.insertFilters(gameName, this.consoleSelected, this.playableSelected, this.genreSelected);
        } catch(FiltersException | PersistencyErrorException | NoGamesFoundException e){
            ErrorPageController.displayErrorWindow(e.getMessage());
        }
    }

    @FXML
    private void goToHomePage(MouseEvent event){
        try{
            HomePageController.start(this.stage, this.customerBoundary.getUserBean());
        } catch(IOException e){
            System.exit(1);
        }
    }

    @FXML
    private void seeCart(){
        try{
            ShoppingCartPageController.start(this.stage, this.customerBoundary, "SearchPage");
        } catch(IOException e){
            ErrorPageController.displayErrorWindow("Couldn't load cart");
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
}
