package org.application.gameshelfapp.confirmsale.dao;

import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.confirmsale.dao.queries.SaleDAOQueries;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SaleDAOJDBC implements SaleDAO {
    @Override
    public void saveSale(Sale sale) throws PersistencyErrorException {
        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            SaleDAOQueries.saveSaleQuery(conn, sale);
            SingletonConnectionPool.getInstance().releaseConnection(conn);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public List<Sale> getConfirmedSales() throws PersistencyErrorException{
        return this.getSalesByState(Sale.CONFIRMED);
    }
    @Override
    public List<Sale> getToConfirmSales() throws PersistencyErrorException{
        return this.getSalesByState(Sale.TO_CONFIRM);
    }

    private List<Sale> getSalesByState(String state) throws PersistencyErrorException{
        List<Sale> sales = new ArrayList<>();
        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            ResultSet rs = SaleDAOQueries.getSalesByStateQuery(conn, state);
            while(rs.next()){
                Videogame videogame = new Videogame(rs.getString("GameName"), rs.getInt("Copies"), rs.getFloat("Price"), null, rs.getString("Platform"), null);
                Credentials credentials = new Credentials(rs.getString("Name"), rs.getString("UserAddress"), rs.getString("UserEmail"));
                Sale sale = new Sale(rs.getInt("id"), videogame, credentials, state);
                sales.add(sale);
            }
            rs.close();
            SingletonConnectionPool.getInstance().releaseConnection(conn);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't get sales.");
        }
        return sales;
    }
}
