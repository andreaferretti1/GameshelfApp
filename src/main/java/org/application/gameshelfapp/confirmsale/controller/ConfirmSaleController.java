package org.application.gameshelfapp.confirmsale.controller;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.boundary.FedEx;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.entities.SingletonSalesToConfirm;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;

import java.util.ArrayList;
import java.util.List;

public class ConfirmSaleController{
    public ConfirmSaleController(UserBean userBean) throws WrongUserTypeException{
        if(userBean == null || userBean.getTypeOfUser().equals("Customer")) throw new WrongUserTypeException("You are not allowed to access.");
    }
    public List<SaleBean> getSalesToSend() throws PersistencyErrorException {
        SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
        List<Sale> sales = saleDAO.getToConfirmSales();
        SingletonSalesToConfirm singletonSalesToConfirm = SingletonSalesToConfirm.getInstance();
        singletonSalesToConfirm.setSales(sales);
        sales = singletonSalesToConfirm.getSales();
        List<SaleBean> salesBean = new ArrayList<>();
        for(Sale sale: sales){
            SaleBean saleBean = new SaleBean();
            saleBean.setSale(sale);
            salesBean.add(saleBean);
        }
        return salesBean;
    }

    public void confirmSale(long id) throws GmailException, ConfirmDeliveryException, PersistencyErrorException, WrongSaleException {
        FedEx shipmentCompany = new FedEx();
        GoogleBoundary googleBoundary = new GoogleBoundary();

        SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
        SingletonSalesToConfirm salesToConfirm = SingletonSalesToConfirm.getInstance();
        salesToConfirm.setSales(saleDAO.getToConfirmSales());
        Sale saleConfirmed = salesToConfirm.confirmSale(id);
        try{
            saleDAO.saveSale(saleConfirmed);
            shipmentCompany.confirmDelivery(saleConfirmed.getCredentials().getAddress());

            googleBoundary.sendSaleConfirmationMessage(saleConfirmed.getCredentials().getEmail(), saleConfirmed.getVideogameSold().getName());
        } catch (PersistencyErrorException e) {
            throw new ConfirmDeliveryException("Couldn't confirm delivery. Try later");
        }
    }
}
