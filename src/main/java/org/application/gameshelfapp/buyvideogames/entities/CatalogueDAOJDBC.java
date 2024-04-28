package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.*;

public class CatalogueDAOJDBC implements CatalogueDAO {

    @Override
    public void addVideogame(String email, Videogame game, int numberOfCopies) throws PersistencyErrorException{
        Connection conn = null;
        Statement stmt = null;
        SingletonConnectionPool singletonConnectionPool = SingletonConnectionPool.getInstance();

        try{
            conn = singletonConnectionPool.getConnection();
            stmt = conn.createStatement();
            String query = String.format("INSERT INTO Catalogue (Name, Email, Copies) VALUES (%s, %s, %d) ON DUPLICATE KEY UPDATE Copies = Copies + %d;", game.getName(), email, numberOfCopies, numberOfCopies);
            stmt.execute(query);
            conn.close();
        } catch (SQLException e){
            throw new PersistencyErrorException("Couldn't add videogame to your catalogue.");
        }

    }

    @Override
    public void removeVideogame(String email, Videogame item, int quantity) throws PersistencyErrorException{
        Connection conn = null;
        Statement stmt = null;
        String query = null;
        SingletonConnectionPool singletonConnectionPool = SingletonConnectionPool.getInstance();
        try{
            conn = singletonConnectionPool.getConnection();
            stmt = conn.createStatement();
            if(quantity == 0){
                query = "DELETE FROM Catalogue WHERE Name = " + item.getName() + "AND Email = " + email + ";";
            } else{
                query = "UPDATE Catalogue SET Copies = " + quantity + "WHERE Name = " + item.getName() + "AND Email = " + email + ";";
            }
            stmt.execute(query);
            conn.close();
        }catch(SQLException e){
            throw new PersistencyErrorException("Couldn't remove videogame from your catalogue.");
        }

    }
}
