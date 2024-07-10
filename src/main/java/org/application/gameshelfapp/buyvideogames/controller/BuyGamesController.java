package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.Geocoder;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.controller.ConfirmSaleController;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.seevideogamecatalogue.SeeGameCatalogueController;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.io.IOException;
import java.util.List;

public class BuyGamesController {
    private ConfirmSaleController confirmSaleController;

    public BuyGamesController(UserBean userBean) throws WrongUserTypeException{
        if(userBean == null) throw new WrongUserTypeException("Access not allowed");
    }

    public SellingGamesCatalogueBean searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {
        SeeGameCatalogueController seeGameCatalogueController = new SeeGameCatalogueController();
        return seeGameCatalogueController.displaySellingGamesCatalogue(filtersBean);
    }

    public List<String> getCategories() throws PersistencyErrorException{
        SeeGameCatalogueController controller = new SeeGameCatalogueController();
        return controller.getCategoriesValue();
    }

    public List<String> getConsoles() throws PersistencyErrorException{
        SeeGameCatalogueController controller = new SeeGameCatalogueController();
        return controller.getConsolesValue();
    }

    public void sendMoney(CredentialsBean credentialsBean, VideogameBean videogameBean, UserBean userBean) throws RefundException, GameSoldOutException, PersistencyErrorException, InvalidAddressException, NoGameInCatalogueException {
        Braintree braintree = new Braintree();
        Geocoder geocoder = new Geocoder();

        PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
        ItemDAO itemDAO = factory.createItemDAO();
        SaleDAO saleDAO = factory.createSaleDAO();

        Filters filters = new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());
        List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
        Videogame game = games.getFirst();
        if(game.getPrice() != videogameBean.getPriceBean()) throw new RefundException("Price has been changed");
        game.setCopies(videogameBean.getCopiesBean());
        Sale sale = new Sale(-1, game, userBean.getEmail(), credentialsBean.getAddressBean(), Sale.TO_CONFIRM, credentialsBean.getNameBean());
        itemDAO.removeGameForSale(game);
            try{
                geocoder.checkAddress(credentialsBean.getAddressBean());

                int quantity = game.getCopies();
                float amountToPay = game.getPrice() * quantity;
                braintree.pay(amountToPay, credentialsBean.getPaymentKeyBean(), credentialsBean.getTypeOfPaymentBean());

                GoogleBoundary googleBoundary = new GoogleBoundary();
                String receipt = "You have bought " + quantity + " of " + game.getName() + " for " + amountToPay;
                googleBoundary.setMessageToSend(receipt);
                googleBoundary.sendMail("Receipt", userBean.getEmail());
                String messageToSend = userBean.getUsername() + " bought " + quantity + " of " + game.getName() + " for " + amountToPay;
                googleBoundary.setMessageToSend(messageToSend);
                googleBoundary.sendMail("Videogame bought", "fer.andrea35@gmail.com");

                saleDAO.saveSale(sale);
            }catch(GmailException | PersistencyErrorException e){
                itemDAO.addGameForSale(game);
                braintree.refund();
                throw new RefundException("Transaction has been refunded due to problems.");
            } catch(IOException e){
                throw new RefundException("Couldn't buy videogame");
            }
    }

    public List<SaleBean> getSales(UserBean userBean) throws PersistencyErrorException, WrongUserTypeException{
        this.confirmSaleController = new ConfirmSaleController(userBean);
        return this.confirmSaleController.getSales();
    }

    public void confirmDelivery(long id) throws GmailException, ConfirmDeliveryException, PersistencyErrorException, WrongSaleException {
        this.confirmSaleController.confirmDelivery(id);
    }
}

