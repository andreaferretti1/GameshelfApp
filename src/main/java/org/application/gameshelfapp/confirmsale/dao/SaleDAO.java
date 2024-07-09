package org.application.gameshelfapp.confirmsale.dao;

import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface SaleDAO {

    void saveSale(Sale sale) throws PersistencyErrorException;
    List<Sale> getConfirmedSales() throws PersistencyErrorException;
    List<Sale> getToConfirmSales() throws PersistencyErrorException;
    void updateSale(Sale sale) throws PersistencyErrorException;
    Sale getSaleToConfirmById(long id) throws WrongSaleException, PersistencyErrorException;
}