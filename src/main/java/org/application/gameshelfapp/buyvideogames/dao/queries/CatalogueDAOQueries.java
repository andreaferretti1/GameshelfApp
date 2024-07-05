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
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, email);
            pstmt.execute();

            return pstmt.getResultSet();
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void addVideogameQuery(Connection connection, String email, Videogame videogame) throws SQLException{
        String query ="INSERT INTO Catalogue (Name, Email, Platform, Copies) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE Copies = Copies + Values(Copies);";

        try(PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, videogame.getName());
            pstmt.setString(2, email);
            pstmt.setString(3, videogame.getPlatform());
            pstmt.setInt(4, videogame.getCopies());

            pstmt.execute();
        } catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    public static void removeVideogameQuery(Connection conn, String email, Videogame videogame) throws SQLException{
        String updateQuery = "UPDATE Catalogue SET Copies = Copies - ? WHERE Name = ? AND Platform = ? AND Email = ?;";
        String deleteQuery = "DELETE FROM Catalogue WHERE Name = ? AND Platform = ? AND Email = ? AND Copies <= 0;";

        try(PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)){
            conn.setAutoCommit(false);

            updateStmt.setInt(1, videogame.getCopies());
            updateStmt.setString(2, videogame.getName());
            updateStmt.setString(3, videogame.getPlatform());
            updateStmt.setString(4, email);

            deleteStmt.setString(1, videogame.getName());
            deleteStmt.setString(2, videogame.getPlatform());
            deleteStmt.setString(2, email);

            updateStmt.execute();
            deleteStmt.execute();

            conn.commit();

        } catch(SQLException e){
            conn.rollback();
            throw new SQLException(e.getMessage());
        } finally{
            conn.setAutoCommit(true);
        }
    }
}
