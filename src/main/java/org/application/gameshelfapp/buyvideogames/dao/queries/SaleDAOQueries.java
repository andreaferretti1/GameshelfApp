package org.application.gameshelfapp.buyvideogames.dao.queries;

import org.application.gameshelfapp.buyvideogames.entities.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleDAOQueries {

    private SaleDAOQueries(){}
    public static void saveSaleQuery(Connection conn, Sale sale) throws SQLException{
        String query = "INSERT INTO Sale (Copies, Price, State, GameName, Platform, Username, UserEmail, UserAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, sale.getVideogameSold().getCopies());
        pstmt.setFloat(2, sale.getVideogameSold().getPrice());
        pstmt.setString(3, sale.getState());
        pstmt.setString(4, sale.getVideogameSold().getName());
        pstmt.setString(5, sale.getVideogameSold().getPlatform());
        pstmt.setString(6, sale.getName());
        pstmt.setString(7, sale.getEmail());
        pstmt.setString(8, sale.getAddress());

        pstmt.execute();
    }

    public static ResultSet getSalesByStateQuery(Connection conn, String state) throws SQLException{
        String query = "SELECT Id, Name, Copies, Price, GameName, Platform, UserEmail, UserAddress FROM ObjectOnSale WHERE State = ?;";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, state);
        pstmt.execute();
        return pstmt.getResultSet();
    }

    public static void updateSaleQuery(Connection conn, Sale sale) throws SQLException{
        String query = "UPDATE Sale SET State = Confirmed WHERE Id = ?;";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, sale.getId());
        pstmt.execute();
    }
}
