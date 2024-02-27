package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.SellerBean;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.boundary.ShipmentCompany;
import org.application.gameshelfapp.buyvideogames.entities.*;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyGamesController {
    private final User user;
    private ShoppingCart shoppingCart;
    private List<Videogame> gameList;

    private Credentials credentials;

    private PersistencyAbstractFactory factory;

    public BuyGamesController(UserBean userBean){
        this.user = new User(userBean.getUsername(), userBean.getEmail(), userBean.getTypeOfUser());
        if(user.getTypeOfUser().equals("customer")){
            this.shoppingCart = new ShoppingCart();
        }
        else{
            new SellerBoundary(this, userBean);
        }

    }

    public List<VideogameBean> searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException, NoGamesFoundException{

        Filters filters = new Filters(filtersBean.getNameBean(), filtersBean.getConsoleBean(), filtersBean.getOnlineBean(), filtersBean.getCategoryBean());

        ItemDAO itemDao = this.factory.createItemDAO();
        try {
            this.gameList = itemDao.getVideogamesForSale(filters);
        } catch (NoGamesFoundException e) {
            Filters newFilters = new Filters("all", filters.getConsole(), filters.getOnline(), filters.getCategory());
            itemDao.getVideogamesForSale(newFilters);
        }
        List<VideogameBean> gameBeans = new ArrayList<VideogameBean>();

        for(Videogame videogame: this.gameList){
            String name = videogame.getName();
            String id = videogame.getId();
            VideogameBean gameBean = new VideogameBean(name, id);
            gameBean.setOwnerBean(new SellerBean(videogame.getOwnerName(), videogame.getOwnerEmail(), videogame.getOwnerCopies(), videogame.getOwnerPrice(), videogame.getSellerDescription()));
        }
        return gameBeans;
    }

    public void addToCart(String id, int quantity) throws CopiesException {
        for(Videogame game: this.gameList){
            if(id.equals(game.getId())){
                this.shoppingCart.addGame(game, quantity);
            }
        }
    }

    public void removeFromCart(String id, int quantity) throws CopiesException{
        this.shoppingCart.removeFromCart(id, quantity);
    }

    public void insertCredentials(CredentialsBean credentialsBean){
        this.credentials = new Credentials(credentialsBean.getTypeOfPaymentBean(), credentialsBean.getPaymentKeyBean(), credentialsBean.getAddressBean());
    }

    public void sendMoney() throws RefundException, GameSoldOutException, GmailException {
        Braintree braintree = new Braintree();

        GoogleBoundary googleBoundary = new GoogleBoundary();

        List<Videogame> videogamesToBuy = this.shoppingCart.listVideogames();
        List<Integer> quantitiesToBuy = this.shoppingCart.listQuantities();
        ItemDAO itemDAO = this.factory.createItemDAO();

        for(int i = 0; i<videogamesToBuy.size(); i++){

            try{
                Videogame temp = videogamesToBuy.get(i);
                int quantity = quantitiesToBuy.get(i);
                float amountToPay = temp.getOwnerPrice() * quantity;
                braintree.pay(amountToPay, this.credentials.getPaymentKey());
                itemDAO.saveSale(this.user, temp, quantity, amountToPay, this.credentials.getAddress());
                itemDAO.removeGameForSale(temp);
                String messageToSend = this.user.getUsername() + "bought" + quantity + "of" + temp.getName() + temp.getId() + "for" + amountToPay + ". His email address is" + this.user.getEmail();
                googleBoundary.sendMail("Videogame bought", messageToSend, temp.getOwnerEmail());
                this.shoppingCart.markAsPayed(temp);
            }catch(GmailException e){

            } catch (PersistencyErrorException e){
                braintree.refund();
                throw new RefundException("Transaction has been refunded due to problems");
            } catch(GameSoldOutException e){
                braintree.refund();
                throw  new GameSoldOutException(e.getMessage());
            }
        }
        this.shoppingCart = null;
    }

    public List<VideogameBean> getSales() throws PersistencyErrorException{

        List<VideogameBean> videogameBeans = new ArrayList<VideogameBean>();
        ItemDAO itemDAO = this.factory.createItemDAO();
        this.gameList = itemDAO.getSales(this.user.getUsername());

        for(Videogame game: this.gameList){
            String name = game.getName();
            String id = game.getId();
            videogameBeans.add(new VideogameBean(name, id));
        }
        return videogameBeans;
    }

    public void confirmDelivery(String id) throws GmailException, ConfirmDeliveryException{
        ShipmentCompany shipmentCompany = new ShipmentCompany();
        GoogleBoundary googleBoundary = new GoogleBoundary();
        Videogame gameToSend = null;

        for(Videogame game: this.gameList){
            if(id.equals(game.getId())) gameToSend = game;
        }

        assert gameToSend != null;
        CatalogueDAO catalogueDAO = this.factory.createCatalogueDAO();
        ItemDAO itemDAO = this.factory.createItemDAO();

        try{
            catalogueDAO.addVideogame(gameToSend.getOwnerName(), gameToSend, gameToSend.getOwnerCopies());
            itemDAO.updateSale(id);
            shipmentCompany.confirmDelivery(gameToSend.getCustomerAddress());

            String message = this.user.getUsername() + "has confirmed delivery of" + gameToSend.getOwnerName();
            googleBoundary.sendMail("delivery confirmed", message, gameToSend.getOwnerEmail());

        } catch (PersistencyErrorException | IOException e) {
            throw new ConfirmDeliveryException("Couldn't confirm deivery. Try later");
        }

    }
}
