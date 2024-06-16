package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
public class SaleDAOJDBC implements SaleDAO{
    @Override
    public void saveSale(Sale sale) throws PersistencyErrorException {
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = String.format("INSERT INTO Sale (Copies, Price, GameName, Platform, UserEmail, UserAddress) VALUES ('%d', '%f', '%s', '%s', '%s', '%s');", sale.getVideogameSold().getCopies(), sale.getVideogameSold().getPrice(), sale.getVideogameSold().getName(), sale.getName(), sale.getEmail(), sale.getAddress());
            stmt.execute(query);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public List<Sale> getSales() throws PersistencyErrorException {
        ResultSet rs = null;
        List<Sale> sales = new ArrayList<Sale>();
         try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
             String query = "SELECT Id, Name, Copies, State, Price, GameName, Platform, UserEmail, UserAddress FROM ObjectOnSale;";
             stmt.execute(query);
             rs = stmt.getResultSet();
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
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = "UPDATE Sale SET State = Confirmed WHERE Id = " + sale.getId() + ";";
            stmt.execute(query);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't update sale.");
        }
    }
}
