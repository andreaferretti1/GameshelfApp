package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;



public class CustomerBoundary {

    private final BuyGamesController buyGamesController;
    private VideogamesFoundBean videogamesFoundBean;
    private FiltersBean filtersBean;

    private final UserBean userBean;

    public CustomerBoundary(UserBean userBean){
        this.userBean = userBean;
        this.buyGamesController = new BuyGamesController();
    }

    public VideogamesFoundBean getGamesFound() {
        return this.videogamesFoundBean;
    }

    public UserBean getUserBean(){
        return this.userBean;
    }
    public FiltersBean getFiltersBean() {
        return this.filtersBean;
    }
    public void insertFilters(String name, String console, String category) throws FiltersException, PersistencyErrorException, NoGamesFoundException {
        this.filtersBean = new FiltersBean();
        this.filtersBean.setNameBean(name);
        this.filtersBean.setConsoleBean(console);
        this.filtersBean.setCategoryBean(category);
        this.videogamesFoundBean = this.buyGamesController.searchVideogame(filtersBean);
    }

    public void insertCredentialsAndPay(String typeOfCard, String paymentKey, String address, VideogameBean videogameBean) throws RefundException, GameSoldOutException, GmailException, SyntaxErrorException, PersistencyErrorException {
        CredentialsBean credentialsBean = new CredentialsBean();
        credentialsBean.setTypeOfPaymentBean(typeOfCard);
        credentialsBean.setPaymentKeyBean(paymentKey);
        credentialsBean.setAddressBean(address);
        this.buyGamesController.sendMoney(credentialsBean, videogameBean, this.userBean);
    }

}
