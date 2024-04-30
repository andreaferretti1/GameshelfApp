package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.boundary.Braintree;
import org.application.gameshelfapp.buyvideogames.boundary.ShipmentCompany;
import org.application.gameshelfapp.buyvideogames.entities.*;
import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class BuyGamesController {
    public BuyGamesController(){
    }

    public VideogamesFoundBean searchVideogame(FiltersBean filtersBean) throws PersistencyErrorException{

        Filters filters = new Filters(filtersBean.getNameBean(), filtersBean.getConsoleBean(), filtersBean.getCategoryBean());

        ItemDAO itemDao = PersistencyAbstractFactory.getFactory().createItemDAO();
        VideogamesFound gamesFound = new VideogamesFound();
        VideogamesFoundBean gamesFoundBean = new VideogamesFoundBean();

        gamesFound.setGamesFound(itemDao.getVideogamesForSale(filters));
        gamesFoundBean.setVideogamesFound(gamesFound);
        return gamesFoundBean;
    }

    public void sendMoney(CredentialsBean credentialsBean, VideogameBean videogameBean, UserBean userBean, FiltersBean filtersBean) throws RefundException, GameSoldOutException, GmailException, PersistencyErrorException{
        Braintree braintree = new Braintree();
        GoogleBoundary googleBoundary = new GoogleBoundary();

        PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
        ItemDAO itemDAO = factory.createItemDAO();
        SaleDAO saleDAO = factory.createSaleDAO();
        CatalogueDAO catalogueDAO = factory.createCatalogueDAO();

        Videogame game = new Videogame(videogameBean.getName(), videogameBean.getCopiesBean(), videogameBean.getPriceBean(), videogameBean.getDescriptionBean());
        Filters filters = new Filters(null, filtersBean.getConsoleBean(), null);
        Sale sale = new Sale();
            try{
                int quantity = game.getCopies();
                float amountToPay = game.getPrice() * quantity;
                braintree.pay(amountToPay, credentialsBean.getPaymentKeyBean(), credentialsBean.getTypeOfPaymentBean());
                itemDAO.removeGameForSale(game, filters);
                catalogueDAO.addVideogame(userBean.getUsername(), game, game.getCopies());
                String messageToSend = userBean.getUsername() + "bought" + quantity + "of" + game.getName() + "for" + amountToPay;
                googleBoundary.sendMail("Videogame bought", messageToSend, /*TODO metti email del negozio*/);
                saleDAO.saveSale(sale);
            }catch(GmailException e){
                itemDAO.addGameForSale(game, filters);
                braintree.refund();
            } catch (PersistencyErrorException e){
                braintree.refund();
                throw new RefundException("Transaction has been refunded due to problems");
            } catch(GameSoldOutException e){
                braintree.refund();
                throw  new GameSoldOutException(e.getMessage());
            }
    }

    public VideogamesFoundBean getSales() throws PersistencyErrorException{

        SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
        saleDAO.getSales();

        return videogameBeans;
    }

    public void confirmDelivery(SaleBean saleBean) throws GmailException, ConfirmDeliveryException{
        ShipmentCompany shipmentCompany = new ShipmentCompany();
        GoogleBoundary googleBoundary = new GoogleBoundary();
        Videogame gameToSend = null;
        Sale sale;

        try{
            PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
            CatalogueDAO catalogueDAO = factory.createCatalogueDAO();
            SaleDAO saleDAO = factory.createSaleDAO();
            catalogueDAO.addVideogame();
            saleDAO.updateSale(sale);
            shipmentCompany.confirmDelivery(sale.getAddress());

            String message = "Your order has been confirmed.";
            googleBoundary.sendMail("delivery CONFIRMED", message, sale.getEmail());

        } catch (PersistencyErrorException e) {
            throw new ConfirmDeliveryException("Couldn't confirm delivery. Try later");
        }
    }
 }

