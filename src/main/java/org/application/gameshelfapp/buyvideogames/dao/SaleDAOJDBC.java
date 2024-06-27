package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.dao.queries.SaleDAOQueries;
import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class SaleDAOJDBC implements SaleDAO {
    @Override
    public void saveSale(Sale sale) throws PersistencyErrorException {
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            SaleDAOQueries.saveSaleQuery(conn, sale);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public List<Sale> getSales() throws PersistencyErrorException {
        ResultSet rs = null;
        List<Sale> sales = new ArrayList<Sale>();
         try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
             rs = SaleDAOQueries.getSalesQuery(conn);
             while(rs.next()){
                 Videogame videogame = new Videogame(rs.getString("GameName"), rs.getInt("Copies"), rs.getFloat("Price"), null, rs.getString("Platform"), null);
                 Sale sale = new Sale(videogame, rs.getString("UserEmail"), rs.getString("UserAddress"), rs.getString("State"), rs.getString("Name"));
                 sale.setId(rs.getInt("Id"));
                 sales.add(sale);
             }
             rs.close();
         } catch(SQLException e){
             throw new PersistencyErrorException("Couldn't get sales.");
         }
         return sales;
    }

    @Override
    public void updateSale(Sale sale) throws PersistencyErrorException {
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            SaleDAOQueries.updateSaleQuery(conn, sale);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't update sale.");
        }
    }

    private List<Sale> getSalesByState(String state){
        List<Sale> sales = new ArrayList<>();

        return sales;
    }
    private List<Sale> getConfirmedSales(){

    }
}
