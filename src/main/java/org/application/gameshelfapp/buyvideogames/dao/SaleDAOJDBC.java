package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.dao.queries.SaleDAOQueries;
import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.exception.WrongSaleException;
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
    public void updateSale(Sale sale) throws PersistencyErrorException {
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            SaleDAOQueries.updateSaleQuery(conn, sale);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't update sale.");
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

    @Override
    public Sale getSaleToConfirmById(long id) throws PersistencyErrorException, WrongSaleException{
        List<Sale> sales = this.getToConfirmSales();
        Sale saleToConfirm = null;
        for(Sale sale: sales){
            if(sale.getId() == id && sale.getState().equals(Sale.TO_CONFIRM)) saleToConfirm = sale;
        }
        if(saleToConfirm == null) throw new WrongSaleException("This sale is already confirmed or doesn't exist");
        return saleToConfirm;
    }
    private List<Sale> getSalesByState(String state) throws PersistencyErrorException{
        List<Sale> sales = new ArrayList<>();
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ResultSet rs = SaleDAOQueries.getSalesByStateQuery(conn, state);
            while(rs.next()){
                Videogame videogame = new Videogame(rs.getString("GameName"), rs.getInt("Copies"), rs.getFloat("Price"), null, rs.getString("Platform"), null);
                Sale sale = new Sale(rs.getInt("id"), videogame, rs.getString("UserEmail"), rs.getString("UserAddress"), state, rs.getString("Name"));
                sales.add(sale);
            }
            rs.close();
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't get sales.");
        }
        return sales;
    }
}
