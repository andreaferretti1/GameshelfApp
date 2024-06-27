package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface SaleDAO {

    void saveSale(Sale sale) throws PersistencyErrorException;
    List<Sale> getSales() throws PersistencyErrorException;
    void updateSale(Sale sale) throws PersistencyErrorException;
}
