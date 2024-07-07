package org.application.gameshelfapp.buyvideogames.dao.queries;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogueDAOQueries {

    private CatalogueDAOQueries(){}

    public static ResultSet getCatalogueQuery(Connection conn, String email) throws SQLException{
        String query = "SELECT Name, Copies, Platform FROM Catalogue WHERE Email = ?;";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, email);
        pstmt.execute();

        return pstmt.getResultSet();
    }

    public static void addVideogameQuery(Connection connection, String email, Videogame videogame) throws SQLException{
        String query ="INSERT INTO Catalogue (Name, Email, Platform, Copies) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE Copies = Copies + Values(Copies);";

        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, videogame.getName());
        pstmt.setString(2, email);
        pstmt.setString(3, videogame.getPlatform());
        pstmt.setInt(4, videogame.getCopies());

        pstmt.execute();
    }
}
