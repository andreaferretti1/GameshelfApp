package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.Geocoder;
import org.application.gameshelfapp.buyvideogames.boundary.ShipmentCompany;
import org.application.gameshelfapp.buyvideogames.entities.*;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyGamesController {
    public VideogamesFoundBean searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException{

        Filters filters = new Filters(filtersBean.getNameBean(), filtersBean.getConsoleBean(), filtersBean.getCategoryBean());

        ItemDAO itemDao = PersistencyAbstractFactory.getFactory().createItemDAO();
        VideogamesFound gamesFound = new VideogamesFound();
        VideogamesFoundBean gamesFoundBean = new VideogamesFoundBean();

        gamesFound.setGamesFound(itemDao.getVideogamesForSale(filters));
        gamesFoundBean.setVideogamesFound(gamesFound);
        return gamesFoundBean;
    }

    public void sendMoney(CredentialsBean credentialsBean, VideogameBean videogameBean, UserBean userBean, FiltersBean filtersBean) throws RefundException, GameSoldOutException, PersistencyErrorException, InvalidAddressException {
        Braintree braintree = new Braintree();
        Geocoder geocoder = new Geocoder();

        PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
        ItemDAO itemDAO = factory.createItemDAO();
        SaleDAO saleDAO = factory.createSaleDAO();

        Videogame game = new Videogame(videogameBean.getName(), videogameBean.getCopiesBean(), videogameBean.getPriceBean(), videogameBean.getDescriptionBean());
        Filters filters = new Filters(null, filtersBean.getConsoleBean(), null);
        Sale sale = new Sale(game, userBean.getEmail(), credentialsBean.getAddressBean(), Sale.TO_CONFIRM, filtersBean.getConsoleBean());
            try{
                geocoder.checkAddress(credentialsBean.getAddressBean());
                int quantity = game.getCopies();
                float amountToPay = game.getPrice() * quantity;
                braintree.pay(amountToPay, credentialsBean.getPaymentKeyBean(), credentialsBean.getTypeOfPaymentBean());
                itemDAO.removeGameForSale(game, filters);
                GoogleBoundary googleBoundary = new GoogleBoundary();
                String messageToSend = userBean.getUsername() + "bought" + quantity + "of" + game.getName() + "for" + amountToPay;
                googleBoundary.sendMail("Videogame bought", messageToSend, "gameshelfApp2024@gmail.com");
                saleDAO.saveSale(sale);
            }catch(GmailException e){
                itemDAO.addGameForSale(game, filters);
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
        List<Sale> sales = saleDAO.getSales();
        List<SaleBean> salesBean = new ArrayList<SaleBean>();
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
        Videogame gameToSend = new Videogame(saleBean.getGameSoldBean().getName(), saleBean.getGameSoldBean().getCopiesBean(), saleBean.getGameSoldBean().getPriceBean(), null);
        Sale sale = new Sale(gameToSend, saleBean.getEmailBean(), saleBean.getAddressBean(), Sale.CONFIRMED, saleBean.getPlatformBean());

        try{
            PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
            CatalogueDAO catalogueDAO = factory.createCatalogueDAO();
            SaleDAO saleDAO = factory.createSaleDAO();
            catalogueDAO.addVideogame(saleBean.getEmailBean(), gameToSend);
            saleDAO.updateSale(sale);
            shipmentCompany.confirmDelivery(sale.getAddress());

            String message = "Your order has been confirmed.";
            googleBoundary.sendMail("delivery CONFIRMED", message, sale.getEmail());

        } catch (PersistencyErrorException e) {
            throw new ConfirmDeliveryException("Couldn't confirm delivery. Try later");
        }
    }
}

