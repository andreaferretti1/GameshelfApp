package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ItemDAOJDBC implements ItemDAO {

    @Override
    public List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException{
        String query = "SELECT Name, Copies, Price, Description, Platform, Type " +
                       "FROM ObjectOnSale WHERE" +
                       "(? IS NULL OR Name = ?) AND" +
                       "(? IS NULL OR )";
        List<Videogame> videogamesFound = new ArrayList<Videogame>();
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Videogame game = new Videogame(rs.getString("Name"), rs.getInt("Copies"), rs.getFloat("Price"), rs.getString("Description"), filters.getConsole(), filters.getCategory());
                videogamesFound.add(game);
            }
            rs.close();
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't find videogames");
        }
        return videogamesFound;
    }

    @Override
    public void addGameForSale(Videogame game) throws PersistencyErrorException{

            try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(qu)){

                String query = String.format("INSERT INTO ObjectOnSale (Name, Platform, Price, Description, Copies) VALUES (%s, %s, %f, %s, %d) ON DUPLICATE KEY UPDATE Copies = Copies + %d, Price = %f;", game.getName(), game.getPlatform(), game.getPrice(), game.getDescription(), game.getCopies(), game.getCopies(), game.getPrice());
                stmt.execute(query);
                query =String.format( "INSERT IGNORE INTO Filtered (Name, Platform, Type) VALUES (%s, %s, %s);", game.getName(), game.getPlatform(), game.getCategory());
                stmt.execute(query);

            } catch(SQLException e){
                throw new PersistencyErrorException("Couldn't add videogame on sale");
            }
    }

    @Override
    public void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException{
        ResultSet rs;
        String query = "SELECT Copies FROM ObjectOnSale WHERE Name = ? AND Platform = ?;";
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            stmt.execute(query);
            rs = stmt.getResultSet();
            int copies = 0;
            while(rs.next()){
                copies = rs.getInt("Copies");
            }
            rs.close();
            if(copies < game.getCopies()) throw new GameSoldOutException("Game is sold out");
            copies = copies - game.getCopies();
            query = "UPDATE ObjectOnSale SET Copies = " + copies + "WHERE Name = " + game.getName() + "AND Platform = " + game.getPlatform() + ";";
            stmt.execute(query);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't remove game for sale");
        }
    }
}
