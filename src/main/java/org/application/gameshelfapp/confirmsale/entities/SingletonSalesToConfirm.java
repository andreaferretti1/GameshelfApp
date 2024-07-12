package org.application.gameshelfapp.confirmsale.entities;

import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;

import java.util.ArrayList;
import java.util.List;

public class SingletonSalesToConfirm {

    private static SingletonSalesToConfirm salesToConfirm = null;
    private List<Sale> sales;
    private boolean initialized;

    protected SingletonSalesToConfirm(){
        sales = new ArrayList<>();
        initialized = false;
    }
    public static SingletonSalesToConfirm getInstance(){
        if(salesToConfirm == null){
            salesToConfirm = new SingletonSalesToConfirm();
        }
        return salesToConfirm;
    }

    public void setSales(List<Sale> sales){
        if(!initialized){
            this.sales = sales;
            this.initialized = true;
        }
    }
    public List<Sale> getSales(){
        return this.sales;
    }
    public void addSaleToConfirm(Sale sale){
        this.sales.add(sale);
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
