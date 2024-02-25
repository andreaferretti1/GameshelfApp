package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

import java.util.ArrayList;
import java.util.List;

public class CustomerBoundary {

    private final BuyGamesController buyGamesController;
    private List<SellerBean> sellers;
    private List<VideogameBean> gamesFound;
    private FiltersBean filtersBean;
    private ShoppingCartBean shoppingCartBean;
    private final UserBean userBean;

    public CustomerBoundary(UserBean userBean){
        this.userBean = userBean;
        this.buyGamesController = new BuyGamesController(userBean);
        this.shoppingCartBean = new ShoppingCartBean(new ArrayList<VideogameBean>(), new ArrayList<Integer>());
    }

    public void setSellers(List<SellerBean> sellerBeans){
        this.sellers = sellerBeans;
    }

    public void setGamesFound(List<VideogameBean> videogameBeans){
        this.gamesFound = videogameBeans;
    }

    public List<VideogameBean> getGamesFound() {
        return this.gamesFound;
    }

    public void insertFilters(String name, String console, String online, String category) throws FiltersException, PersistencyErrorException, NoGamesFoundException {
        this.filtersBean = new FiltersBean(name, console, online, category);
        this.gamesFound = this.buyGamesController.searchVideogame(this.filtersBean);
    }

    public UserBean getUserBean(){
        return this.userBean;
    }
    public FiltersBean getFiltersBean() {
        return this.filtersBean;
    }

    public ShoppingCartBean getShoppingCartBean() {
        return this.shoppingCartBean;
    }


    public void insertCredentials(String typeOfCard, String paymentKey, String address) throws RefundException, GameSoldOutException, GmailException, SyntaxErrorException {
        this.buyGamesController.insertCredentials(new CredentialsBean(typeOfCard, paymentKey, address));
        this.pay();
    }

    private void pay() throws RefundException, GameSoldOutException, GmailException {
        this.buyGamesController.sendMoney();
    }


    public void addGameToCart(VideogameBean videogameBean, String quantitySelected) throws CopiesException {
        try {
            this.buyGamesController.addToCart(videogameBean.getId(), Integer.parseInt(quantitySelected));
        } catch(NumberFormatException e){
            throw new CopiesException("You should type a number");
        }
    }

    public void removeCopiesFromCart(String id, String quantity) throws CopiesException, SyntaxErrorException{
        if(id.isEmpty()) throw new SyntaxErrorException("You should select the videogame");
        try {
            this.buyGamesController.removeFromCart(id, Integer.parseInt(quantity));
        } catch(NumberFormatException e){
            throw new SyntaxErrorException("You should give me the number of copies to insert");
        }
    }
}
