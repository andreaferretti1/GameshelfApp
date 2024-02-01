package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.SellerBean;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.boundary.ShipmentCompany;
import org.application.gameshelfapp.buyvideogames.entities.*;
import org.application.gameshelfapp.buyvideogames.exception.CopiesException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyGamesController {
    private  CustomerBoundary customerBoundary;
    private  SellerBoundary sellerBoundary;
    private ShipmentCompany shipmentCompany;
    private Braintree braintree;
    private GoogleBoundary googleBoundary;
    private final User user;
    private ShoppingCart shoppingCart = null;
    private List<Videogame> gameList;

    private PersistencyAbstractFactory factory;

    public BuyGamesController(User user){
        this.user = user;
        if(user.getTypeOfUser().equals("customer")){
            this.shoppingCart = new ShoppingCart();
            this.customerBoundary = new CustomerBoundary(this);

        }
        else{
            this.sellerBoundary = new SellerBoundary(this);
        }
    }

    public List<VideogameBean> searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException, NoGamesFoundException{

        Filters filters = new Filters(filtersBean.getName(), filtersBean.getConsole(), filtersBean.getOnline(), filtersBean.getCategory());

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
            float price = videogame.getOwnerPrice();
            gameBeans.add(new VideogameBean(name, id, price));
        }
        return gameBeans;
    }


    public SellerBean selectVideogame(VideogameBean gameBean){
        String id = gameBean.getId();
        for(Videogame game: this.gameList){
            String idToCompare = game.getId();
            if(id.equals(idToCompare)){
                    String name = game.getName();
                    String email = game.getOwnerEmail();
                    String description = game.getSellerDescription();
                    int copies = game.getOwnerCopies();
                    return new SellerBean(name, email, copies, description);
            }
        }
        return null;
    }

    public void addToCart(String id, int quantity) throws CopiesException {
        for(Videogame game: this.gameList){
            if(id.equals(game.getId())){
                this.shoppingCart.addGame(game, quantity);
            }
        }
    }

    public void insertCredentials(String address, String paymentKey){
        this.user.setAddress(address);
        this.user.setPaymentKey(paymentKey);
    }

    public void sendMoney() throws RefundException, GameSoldOutException, GmailException {
        this.braintree = new Braintree();

        this.googleBoundary = new GoogleBoundary();

        List<Videogame> videogamesToBuy = this.shoppingCart.listVideogames();
        List<Integer> quantitiesToBuy = this.shoppingCart.listQuantities();
        ItemDAO itemDAO = this.factory.createItemDAO();

        for(int i = 0; i<videogamesToBuy.size(); i++){

            try{
                Videogame temp = videogamesToBuy.get(i);
                int quantity = quantitiesToBuy.get(i);
                float amountToPay = temp.getOwnerPrice() * quantity;
                this.braintree.pay(amountToPay, this.user.getPaymentKey());
                itemDAO.saveSale(this.user, temp, quantity);
                itemDAO.removeGameForSale(temp);
                String messageToSend = this.user.getUsername() + "bought" + quantity + "of" + temp.getName() + temp.getId() + "for" + amountToPay + ". His email address is" + this.user.getEmail();
                this.googleBoundary.sendMail("Videogame bought", messageToSend, temp.getOwnerEmail());
                this.shoppingCart.markAsPayed(temp, quantity);
            }catch(GmailException e){

            } catch (PersistencyErrorException e) {
                this.braintree.refund();
                throw new RefundException("Transaction has been refunded due to problems");
            } catch(GameSoldOutException e){
                this.braintree.refund();
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
            videogameBeans.add(new VideogameBean(name, id, -1));
        }
        return videogameBeans;
    }

    public void confirmDelivery(String id) throws GmailException{
        this.shipmentCompany = new ShipmentCompany();

        Videogame gameToSend = null;

        for(Videogame game: this.gameList){
            if(id.equals(game.getId())) gameToSend = game;
        }

        assert gameToSend != null;
        String message = this.user.getUsername() + "has confirmed delivery of" + gameToSend.getOwnerName();
        this.shipmentCompany.confirmDelivery(gameToSend.getCustomerAddress());
        this.googleBoundary.sendMail("delivery confirmed", message, gameToSend.getOwnerEmail());

        CatalogueDAO catalogueDAO = this.factory.createCatalogueDAO();
        try {
            catalogueDAO.addVideogame(gameToSend.getOwnerName(), gameToSend, gameToSend.getOwnerCopies());
        } catch (PersistencyErrorException | IOException e) {
            String messageToSend = "Couldn't add videogame to your catalogue. Please do it manually\n" +
                    "Videogame name: "+ gameToSend.getName() + "\n" +
                    "copies: " + gameToSend.getOwnerCopies();
            this.googleBoundary.sendMail("catalogue error", messageToSend, gameToSend.getOwnerEmail());
        }

    }


}
