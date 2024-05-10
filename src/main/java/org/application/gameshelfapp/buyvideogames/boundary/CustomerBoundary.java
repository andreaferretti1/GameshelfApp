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
    private VideogameBean gameToBuy;
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
    public void setGameToBuy(VideogameBean gameToBuy) {
        this.gameToBuy = gameToBuy;
    }

    public void insertFilters(String name, String console, String category) throws FiltersException, PersistencyErrorException{
        this.filtersBean = new FiltersBean();
        this.filtersBean.setNameBean(name);
        this.filtersBean.setConsoleBean(console);
        this.filtersBean.setCategoryBean(category);
        this.videogamesFoundBean = this.buyGamesController.searchVideogame(filtersBean);
    }

    public void insertCredentialsAndPay(String typeOfCard, String paymentKey, String street, String region, String country) throws RefundException, GameSoldOutException, GmailException, SyntaxErrorException, PersistencyErrorException, InvalidAddressException{
        CredentialsBean credentialsBean = new CredentialsBean();
        credentialsBean.setTypeOfPaymentBean(typeOfCard);
        credentialsBean.setPaymentKeyBean(paymentKey);
        credentialsBean.setAddressBean(street, region, country);
        this.buyGamesController.sendMoney(credentialsBean, this.gameToBuy, this.userBean, this.filtersBean);
    }

}
