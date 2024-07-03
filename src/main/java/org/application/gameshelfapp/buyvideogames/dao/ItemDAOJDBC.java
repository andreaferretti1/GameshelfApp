package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.dao.queries.ItemDAOQueries;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ItemDAOJDBC implements ItemDAO {

    private List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException{
        List<Videogame> videogamesFound = new ArrayList<>();
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ResultSet rs = ItemDAOQueries.getVideogameOnSaleQuery(conn, filters);
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
    public void checkVideogameExistence(Filters filters) throws PersistencyErrorException, AlreadyExistingVideogameException {
        List<Videogame> gameList = this.getVideogamesForSale(filters);
        if (!gameList.isEmpty()) throw new AlreadyExistingVideogameException("Game Already in Catalogue");
    }

    @Override
    public List<Videogame> getVideogamesFiltered(Filters filters) throws PersistencyErrorException, NoGameInCatalogueException {
        List<Videogame> gameList = this.getVideogamesForSale(filters);
        if (gameList.isEmpty()) throw new NoGameInCatalogueException("Couldn't find Videogame");
        return gameList;
    }


    @Override
    public void addGameForSale(Videogame game) throws PersistencyErrorException{

            try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
                ItemDAOQueries.addGameForSaleQuery(conn, game);
            } catch(SQLException e){
                throw new PersistencyErrorException("Couldn't add videogame on sale");
            }
    }

    @Override
    public void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException{

        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ResultSet rs = ItemDAOQueries.getVideogameCopiesQuery(conn, game);
            int copies = 0;
            while(rs.next()){
                copies = rs.getInt("Copies");
            }
            rs.close();
            if(copies < game.getCopies()) throw new GameSoldOutException("Game is sold out");
           ItemDAOQueries.removeGameForSaleQuery(conn, game);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't remove game for sale");
        }
    }

    @Override
    public void updateGameForSale(Videogame game) throws PersistencyErrorException{
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ItemDAOQueries.updateGameForSaleQuery(conn, game);
        }catch (SQLException e){
            throw new PersistencyErrorException("Couldn't update chosen game");
        }
    }
}
