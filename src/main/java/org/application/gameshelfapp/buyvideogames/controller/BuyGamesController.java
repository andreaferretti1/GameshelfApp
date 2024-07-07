package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.Geocoder;
import org.application.gameshelfapp.buyvideogames.boundary.FedEx;
import org.application.gameshelfapp.buyvideogames.dao.CatalogueDAO;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.dao.SaleDAO;
import org.application.gameshelfapp.buyvideogames.entities.*;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.seevideogamecatalogue.SeeGameCatalogueController;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuyGamesController {
    public SellingGamesCatalogueBean searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {
        SeeGameCatalogueController controller = new SeeGameCatalogueController();
        return controller.displaySellingGamesCatalogue(filtersBean);
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

    public void confirmDelivery(long id) throws GmailException, ConfirmDeliveryException, PersistencyErrorException, WrongSaleException {
        FedEx shipmentCompany = new FedEx();
        GoogleBoundary googleBoundary = new GoogleBoundary();

        SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
        Sale sale = saleDAO.getSaleToConfirmById(id);
        try{
            CatalogueDAO catalogueDAO = PersistencyAbstractFactory.getFactory().createCatalogueDAO();
            catalogueDAO.addVideogame(sale.getEmail(), sale.getVideogameSold());
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

