package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ItemDAOJDBC implements ItemDAO {

    @Override
    public List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException, NoGamesFoundException {
        String query = null;
        List<Videogame> videogamesFound = new ArrayList<Videogame>();
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            if (filters.getName().equals("*")) {
                query = "SELECT * FROM ObjectOnSale WHERE Type = " + filters.getCategory() + "AND Platform = " + filters.getPlatform() + " AND Copies > 0;";
            } else {
                query = "SELECT * FROM ObjectOnSale WHERE Type = " + filters.getCategory() + "AND Platform = " + filters.getPlatform() + "AND Name = " + filters.getName() + "AND Copies > 0;";
            }
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Videogame game = new Videogame(rs.getString("Name"), rs.getInt("Copies"), rs.getFloat("Price"), rs.getString("Description"));
                videogamesFound.add(game);
            }
            rs.close();
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't find videogames");
        }
        return videogamesFound;
    }

    @Override
    public void addGameForSale(Videogame game, Filters filters) throws PersistencyErrorException{

            try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
                Statement stmt = conn.createStatement()){

                String query = String.format("INSERT INTO ObjectOnSale (Name, Platform, Price, Description, Copies) VALUES (%s, %s, %f, %s, %d) ON DUPLICATE KEY UPDATE Copies = Copies + %d, Price = %f;", game.getName(), filters.getPlatform(), game.getPrice(), game.getDescription(), game.getCopies(),game.getCopies(), game.getPrice());
                stmt.execute(query);
                query =String.format( "INSERT IGNORE INTO Filtered (Name, Platform, Type) VALUES (%s, %s, %s);", game.getName(), filters.getPlatform(), filters.getCategory());
                stmt.execute(query);

            } catch(SQLException e){
                throw new PersistencyErrorException("Couldn't add videogame on sale");
            }
    }

    @Override
    public void removeGameForSale(Videogame game, Filters filters) throws PersistencyErrorException, GameSoldOutException{
        ResultSet rs = null;
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = "SELECT Copies FROM ObjectOnSale WHERE Name = " + game.getName() + "AND Platform = " + filters.getPlatform() + ";";
            stmt.execute(query);
            rs = stmt.getResultSet();
            int copies = 0;
            while(rs.next()){
                copies = rs.getInt("Copies");
            }
            rs.close();
            if(copies < game.getCopies()) throw new GameSoldOutException("Game is sold out");
            copies = copies - game.getCopies();
            query = "UPDATE ObjectOnSale SET Copies = " + copies + "WHERE Name = " + game.getName() + "AND Platform = " + filters.getPlatform() + ";";
            stmt.execute(query);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't remove game for sale");
        }
    }
}
