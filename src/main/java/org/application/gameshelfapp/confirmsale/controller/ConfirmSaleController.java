package org.application.gameshelfapp.confirmsale.controller;

import org.application.gameshelfapp.buyvideogames.dao.CatalogueDAO;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.boundary.FedEx;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;
import java.util.List;

public class ConfirmSaleController{
    public List<SaleBean> getSales() throws PersistencyErrorException {
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
