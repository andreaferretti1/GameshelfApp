package org.application.gameshelfapp.buyvideogames.dao.queries;

import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAOQueries{
    private ItemDAOQueries(){}

    public static ResultSet getVideogameOnSaleQuery(Connection conn, Filters filters) throws SQLException{
        String query = "SELECT Name, Copies, Price, Description, Platform, Category FROM ObjectOnSale WHERE (? IS NULL OR Name = ?) AND (? IS NULL OR Platform = ?) AND (? IS NULL OR Category = ?);";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1,filters.getName());
        pstmt.setString(2, filters.getName());
        pstmt.setString(3, filters.getConsole());
        pstmt.setString(4, filters.getConsole());
        pstmt.setString(5,filters.getCategory());
        pstmt.setString(6, filters.getCategory());
        pstmt.execute();
        return pstmt.getResultSet();
    }

    public static void addGameForSaleQuery(Connection connection, Videogame videogame) throws SQLException{
        String query = "INSERT INTO ObjectOnSale (Name, Platform, Category, Price, Description, Copies) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, videogame.getName());
        pstmt.setString(2, videogame.getPlatform());
        pstmt.setString(3, videogame.getCategory());
        pstmt.setFloat(4, videogame.getPrice());
        pstmt.setString(5, videogame.getDescription());
        pstmt.setInt(6, videogame.getCopies());
        pstmt.execute();
    }

    public static ResultSet getVideogameCopiesQuery(Connection connection, Videogame videogame) throws SQLException{
        String query = "SELECT Copies FROM ObjectOnSale WHERE Name = ? AND Platform = ?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, videogame.getName());
        pstmt.setString(2, videogame.getPlatform());
        pstmt.execute();
        return pstmt.getResultSet();
    }

    public static void removeGameForSaleQuery(Connection connection, Videogame videogame) throws SQLException{
        String query = "UPDATE ObjectOnSale SET Copies = Copies - ? WHERE Name = ? AND Platform = ?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, videogame.getCopies());
        pstmt.setString(2, videogame.getName());
        pstmt.setString(3, videogame.getPlatform());
        pstmt.execute();
    }

    public static void updateGameForSaleQuery(Connection connection, Videogame videogame) throws SQLException{
        String query = "UPDATE ObjectOnSale SET Copies = Copies + ?, Price = ?, Description = ? WHERE Name = ? AND Platform = ?;";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, videogame.getCopies());
        pstmt.setFloat(2, videogame.getPrice());
        pstmt.setString(3, videogame.getDescription());
        pstmt.setString(4, videogame.getName());
        pstmt.setString(5,videogame.getPlatform());
        pstmt.execute();
    }
}
