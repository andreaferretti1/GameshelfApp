package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.dao.queries.CategoryDAOQueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOJDBC implements CategoryDAO{
    @Override
    public List<String> getCategories() throws PersistencyErrorException{
        List<String> categories = new ArrayList<>();
        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            ResultSet rs = CategoryDAOQueries.getCategoryQuery(conn);
            while (rs.next()){
                categories.add(rs.getString("Type"));
            }
            SingletonConnectionPool.getInstance().releaseConnection(conn);
        } catch (SQLException e){
            throw new PersistencyErrorException("Couldn't access database");
        }
        return categories;
    }
}
