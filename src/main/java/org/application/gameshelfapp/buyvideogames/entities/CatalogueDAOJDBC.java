package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogueDAOJDBC implements CatalogueDAO {

    @Override
    public List<Videogame> getCatalogue(String email) throws PersistencyErrorException {
        List<Videogame> catalogue = new ArrayList<Videogame>();

        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = "SELECT Name, Copies FROM Catalogue WHERE Email = " + email + ";";
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
                Videogame videogame = new Videogame(rs.getString("Name"), rs.getInt("Copies"), 0, null, null, null);
                catalogue.add(videogame);
            }
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't get catalogue");
        }

        return catalogue;
    }

    @Override
    public void addVideogame(String email, Videogame game) throws PersistencyErrorException{
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = String.format("INSERT INTO Catalogue (Name, Email, Copies) VALUES (%s, %s, %d) ON DUPLICATE KEY UPDATE Copies = Copies + %d;", game.getName(), email, game.getCopies(), game.getCopies());
            stmt.execute(query);
        } catch (SQLException e){
            throw new PersistencyErrorException("Couldn't add videogame to your catalogue.");
        }
    }

    @Override
    public void removeVideogame(String email, Videogame game) throws PersistencyErrorException{
        String query = null;
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement();){
            int quantity;
            query = "SELECT Copies FROM Catalogue WHERE Name = " + game.getName() + "AND Email = " + email + ";";
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();
             //TODO definisci eccezione per i videogiochi che voglio rimuovere ma che non sono presenti nel catalogo
            quantity = rs.getInt("Copies");
            rs.close();
            if(quantity == 0){
                query = "DELETE FROM Catalogue WHERE Name = " + game.getName() + "AND Email = " + email + ";";
            } else{
                query = "UPDATE Catalogue SET Copies = " + quantity + "WHERE Name = " + game.getName() + "AND Email = " + email + ";";
            }
            stmt.execute(query);
        }catch(SQLException e){
            throw new PersistencyErrorException("Couldn't remove videogame from your catalogue.");
        }

    }
}
