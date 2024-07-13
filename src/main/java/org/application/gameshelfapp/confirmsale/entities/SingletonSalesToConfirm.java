package org.application.gameshelfapp.confirmsale.entities;

import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;

import java.util.ArrayList;
import java.util.List;

public class SingletonSalesToConfirm {

    private static SingletonSalesToConfirm salesToConfirm = null;
    private final List<Sale> sales;

    protected SingletonSalesToConfirm(){
        this.sales = new ArrayList<>();
    }
    public static SingletonSalesToConfirm getInstance(){
        if(SingletonSalesToConfirm.salesToConfirm == null){
            SingletonSalesToConfirm.salesToConfirm = new SingletonSalesToConfirm();
        }
        return salesToConfirm;
    }

    public void setSales(List<Sale> sales){
            for(Sale sale: sales) {
                this.addSaleToConfirm(sale);
            }
    }
    public List<Sale> getSales(){
        return this.sales;
    }
    public void addSaleToConfirm(Sale sale){
        if(sale.getState().equals(Sale.TO_CONFIRM) && !this.sales.contains(sale)) {
            this.sales.add(sale);
        }
    }
    public Sale confirmSale(long id) throws WrongSaleException{
        Sale saleConfirmed;
        for(Sale sale: sales){
            if(sale.getId() == id){
                saleConfirmed = sale;
                this.sales.remove(sale);
                return saleConfirmed;
            }
        }
        throw new WrongSaleException("Sale has been already confirmed");
    }
}
