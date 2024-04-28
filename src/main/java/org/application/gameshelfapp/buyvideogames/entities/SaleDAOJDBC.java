package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class SaleDAOJDBC implements SaleDAO{
    @Override
    public void saveSale(Sale sale) throws PersistencyErrorException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = SingletonConnectionPool.getInstance().getConnection();
            stmt = conn.createStatement();
            String query = String.format("INSERT INTO Sale (Copies, Price, GameName, Platform, UserEmail, UserAddress) VALUES ('%d', '%f', '%s', '%s', '%s', '%s');", sale.getCopies(), sale.getPrice(), sale.getObjectName(), sale.getPlatform(), sale.getEmail(), sale.getAddress());
            stmt.execute(query);
            conn.close();
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public List<Sale> getSales() throws PersistencyErrorException {
         Connection conn = null;
         Statement stmt = null;
         ResultSet rs = null;
         List<Sale> sales = new ArrayList<Sale>();
         try{
             conn = SingletonConnectionPool.getInstance().getConnection();
             stmt = conn.createStatement();
             String query = "SELECT * FROM ObjectOnSale;";
             stmt.execute(query);
             rs = stmt.getResultSet();
             while(rs.next()){
                 Sale sale = new Sale(rs.getInt("Id"), rs.getInt("Copies"), rs.getFloat("Price"), rs.getString("GameName"), rs.getString("UserEmail"), rs.getString("UserAddress"), rs.getString("State"), rs.getString("Platform"));
                 sales.add(sale);
             }
             conn.close();
         } catch(SQLException e){
             throw new PersistencyErrorException("Couldn't get sales.");
         }
         return sales;
    }

    @Override
    public void updateSale(Sale sale) throws PersistencyErrorException {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = SingletonConnectionPool.getInstance().getConnection();
            stmt = conn.createStatement();
            String query = "UPDATE Sale SET State = Confirmed WHERE Id = " + sale.getId() + ";";
            stmt.execute(query);
            conn.close();
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't update sale.");
        }
    }
}
