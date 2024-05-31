package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.buyvideogames.exception.FiltersException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public class CustomerBoundary {

    public BuyGamesController getBuyGamesController() {
        return this.buyGamesController;
    }

    private final BuyGamesController buyGamesController;
    private VideogamesFoundBean videogamesFoundBean;
    private FiltersBean filtersBean;
    private final UserBean userBean;

    public CustomerBoundary(UserBean userBean){
        this.userBean = userBean;
        this.buyGamesController = new BuyGamesController();
    }

    public UserBean getUserBean(){
        return this.userBean;
    }
    public void setVideogamesFoundBean(VideogamesFoundBean videogamesFoundBean) {
        this.videogamesFoundBean = videogamesFoundBean;
    }
    public VideogamesFoundBean getVideogamesFoundBean() {
        return this.videogamesFoundBean;
    }
    public void setFiltersBean(FiltersBean filtersBean) {
        this.filtersBean = filtersBean;
    }
    public FiltersBean getFiltersBean() {
        return this.filtersBean;
    }
    public void insertFilters(String name, String console, String category) throws FiltersException, PersistencyErrorException{
        this.filtersBean = new FiltersBean();
        this.filtersBean.setNameBean(name);
        this.filtersBean.setConsoleBean(console);
        this.filtersBean.setCategoryBean(category);
        this.videogamesFoundBean = this.buyGamesController.searchVideogame(filtersBean);
    }

    public void insertCredentialsAndPay(String typeOfCard, String paymentKey, String street, String region, String country, VideogameBean gameToBuy) throws RefundException, GameSoldOutException, SyntaxErrorException, PersistencyErrorException, InvalidAddressException{
        CredentialsBean credentialsBean = new CredentialsBean();
        credentialsBean.setTypeOfPaymentBean(typeOfCard);
        credentialsBean.setPaymentKeyBean(paymentKey);
        credentialsBean.setAddressBean(street, region, country);
        this.buyGamesController.sendMoney(credentialsBean, gameToBuy, this.userBean, this.filtersBean);
    }
}
