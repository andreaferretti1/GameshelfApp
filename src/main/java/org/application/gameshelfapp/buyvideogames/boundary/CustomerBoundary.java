package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;

public class CustomerBoundary {
    private final BuyGamesController buyGamesController;
    private SellingGamesCatalogueBean sellingGamesCatalogueBean;
    private final UserBean userBean;
    private VideogameBean gameToBuy;

    public CustomerBoundary(UserBean userBean) throws WrongUserTypeException {
        this.userBean = userBean;
        this.buyGamesController = new BuyGamesController(userBean);
    }

    public UserBean getUserBean(){
        return this.userBean;
    }
    public void setSellingGamesCatalogueBean(SellingGamesCatalogueBean sellingGamesCatalogueBean) {
        this.sellingGamesCatalogueBean = sellingGamesCatalogueBean;
    }
    public SellingGamesCatalogueBean getSellingGamesCatalogueBean() {
        return this.sellingGamesCatalogueBean;
    }
    public void insertFilters(String name, String console, String category) throws PersistencyErrorException, NoGameInCatalogueException {
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setNameBean(name);
        filtersBean.setConsoleBean(console);
        filtersBean.setCategoryBean(category);
        this.sellingGamesCatalogueBean = this.buyGamesController.searchVideogame(filtersBean);
    }
    public void setGameToBuy(VideogameBean videogameBean){
        this.gameToBuy = videogameBean;
    }
    public void insertCredentialsAndPay(String name, String typeOfCard, String paymentKey, String street, String region, String country) throws RefundException, GameSoldOutException, SyntaxErrorException, PersistencyErrorException, InvalidAddressException, NoGameInCatalogueException{
        CredentialsBean credentialsBean = new CredentialsBean();
        credentialsBean.setNameBean(name);
        credentialsBean.setTypeOfPaymentBean(typeOfCard);
        credentialsBean.setPaymentKeyBean(paymentKey);
        credentialsBean.setAddressBean(street, region, country);
        this.buyGamesController.sendMoney(credentialsBean, this.gameToBuy, this.userBean);
    }

    public List<String> getConsoleFilters() throws PersistencyErrorException{
        return this.buyGamesController.getConsoles();
    }

    public List<String> getCategoriesFilters() throws PersistencyErrorException{
        return this.buyGamesController.getCategories();
    }
}
