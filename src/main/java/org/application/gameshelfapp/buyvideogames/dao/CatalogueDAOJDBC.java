package org.application.gameshelfapp.buyvideogames.dao;


import org.application.gameshelfapp.buyvideogames.dao.queries.CatalogueDAOQueries;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogueDAOJDBC implements CatalogueDAO {

    @Override
    public List<Videogame> getCatalogue(String email) throws PersistencyErrorException {
        List<Videogame> catalogue = new ArrayList<>();

        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ResultSet rs = CatalogueDAOQueries.getCatalogueQuery(conn, email);
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
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            CatalogueDAOQueries.addVideogameQuery(conn, email, game);
        } catch (SQLException e){
            throw new PersistencyErrorException("Couldn't add videogame to your catalogue.");
        }
    }

    @Override
    public void removeVideogame(String email, Videogame game) throws PersistencyErrorException{

        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            CatalogueDAOQueries.removeVideogameQuery(conn, email, game);
        }catch(SQLException e){
            throw new PersistencyErrorException("Couldn't remove videogame from your catalogue.");
        }

    }
}
