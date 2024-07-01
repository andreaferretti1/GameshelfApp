package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.Geocoder;
import org.application.gameshelfapp.buyvideogames.boundary.ShipmentCompany;
import org.application.gameshelfapp.buyvideogames.dao.CatalogueDAO;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.dao.SaleDAO;
import org.application.gameshelfapp.buyvideogames.entities.*;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyGamesController {
    public VideogamesFoundBean searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {

        Filters filters = new Filters(filtersBean.getNameBean(), filtersBean.getConsoleBean(), filtersBean.getCategoryBean());

        ItemDAO itemDao = PersistencyAbstractFactory.getFactory().createItemDAO();
        VideogamesFound gamesFound = new VideogamesFound();
        VideogamesFoundBean gamesFoundBean = new VideogamesFoundBean();

        gamesFound.setGamesFound(itemDao.getVideogamesForSale(filters));
        gamesFoundBean.setVideogamesFound(gamesFound);
        return gamesFoundBean;
    }

    public void sendMoney(CredentialsBean credentialsBean, VideogameBean videogameBean, UserBean userBean) throws RefundException, GameSoldOutException, PersistencyErrorException, InvalidAddressException {
        Braintree braintree = new Braintree();
        Geocoder geocoder = new Geocoder();

        PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
        ItemDAO itemDAO = factory.createItemDAO();
        SaleDAO saleDAO = factory.createSaleDAO();

        Videogame game = new Videogame(videogameBean.getName(), videogameBean.getCopiesBean(), videogameBean.getPriceBean(), videogameBean.getDescriptionBean(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());
        Sale sale = new Sale(game, userBean.getEmail(), credentialsBean.getAddressBean(), Sale.TO_CONFIRM, credentialsBean.getNameBean());
            try{
                geocoder.checkAddress(credentialsBean.getAddressBean());
                int quantity = game.getCopies();
                float amountToPay = game.getPrice() * quantity;
                braintree.pay(amountToPay, credentialsBean.getPaymentKeyBean(), credentialsBean.getTypeOfPaymentBean());
                itemDAO.removeGameForSale(game);
                GoogleBoundary googleBoundary = new GoogleBoundary();
                String messageToSend = userBean.getUsername() + "bought" + quantity + "of" + game.getName() + "for" + amountToPay;
                googleBoundary.setMessageToSend(messageToSend);
                googleBoundary.sendMail("Videogame bought", "gameshelfApp2024@gmail.com");
                saleDAO.saveSale(sale);
            }catch(GmailException e){
                itemDAO.addGameForSale(game);
                braintree.refund();
                throw new RefundException("Transaction has been refunded.");
            } catch (PersistencyErrorException e){
                braintree.refund();
                throw new RefundException("Transaction has been refunded due to problems");
            } catch(GameSoldOutException e){
                braintree.refund();
                throw new GameSoldOutException(e.getMessage());
            } catch(IOException e){
                throw new RefundException("Couldn't buy videogame");
            }
    }

    public List<SaleBean> getSales() throws PersistencyErrorException{
        SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
        List<Sale> sales = saleDAO.getToConfirmSales();
        List<SaleBean> salesBean = new ArrayList<>();
        for(Sale sale: sales){
            SaleBean saleBean = new SaleBean();
            saleBean.setSale(sale);
            salesBean.add(saleBean);
        }
        return salesBean;
    }

    public void confirmDelivery(SaleBean saleBean) throws GmailException, ConfirmDeliveryException{
        ShipmentCompany shipmentCompany = new ShipmentCompany();
        GoogleBoundary googleBoundary = new GoogleBoundary();
        Videogame gameToSend = new Videogame(saleBean.getGameSoldBean().getName(), saleBean.getGameSoldBean().getCopiesBean(), saleBean.getGameSoldBean().getPriceBean(), null, saleBean.getGameSoldBean().getPlatformBean(), null);
        Sale sale = new Sale(gameToSend, saleBean.getEmailBean(), saleBean.getAddressBean(), Sale.CONFIRMED, saleBean.getNameBean());

        try{
            PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
            CatalogueDAO catalogueDAO = factory.createCatalogueDAO();
            SaleDAO saleDAO = factory.createSaleDAO();
            catalogueDAO.addVideogame(saleBean.getEmailBean(), gameToSend);
            saleDAO.updateSale(sale);
            shipmentCompany.confirmDelivery(sale.getAddress());

            String message = "Your order has been confirmed.";
            googleBoundary.setMessageToSend(message);
            googleBoundary.sendMail("delivery CONFIRMED", sale.getEmail());

        } catch (PersistencyErrorException e) {
            throw new ConfirmDeliveryException("Couldn't confirm delivery. Try later");
        }
    }
}

