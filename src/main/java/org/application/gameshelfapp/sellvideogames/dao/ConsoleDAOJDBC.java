package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.dao.queries.ConsoleDAOQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleDAOJDBC implements ConsoleDAO{
    public List<String> getConsoles() throws PersistencyErrorException{
        List<String> consoles = new ArrayList<>();
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ResultSet rs = ConsoleDAOQueries.getConsolesQuery(conn);
            while (rs.next()){
                consoles.add(rs.getString("Console"));
            }
        } catch (SQLException e){
            throw new PersistencyErrorException("Couldn't access database");
        }
        return consoles;
    }
}
