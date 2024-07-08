package org.application.gameshelfapp.buyvideogames.dao.utils;

import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

public class ItemDAOJDBCUtils {
    public static void truncateTable(){try(Connection connection = SingletonConnectionPool.getInstance().getConnection()){
        String truncateQuery = "TRUNCATE TABLE ObjectOnSale";
        connection.createStatement().execute(truncateQuery);
    } catch(PersistencyErrorException | SQLException e){
        fail();
    }
    }
    public static void insertRecord(String[][] record){
        try(Connection connection = SingletonConnectionPool.getInstance().getConnection()){
            String query = "INSERT INTO ObjectOnSale(Name, Platform, Price, Type, Description, Copies) VALUES (?, ?, ?, ?, ?, ?)";
            for(String[] game: record) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, game[0]);
                preparedStatement.setString(2, game[1]);
                preparedStatement.setFloat(3, Float.parseFloat(game[2]));
                preparedStatement.setString(4, game[3]);
                preparedStatement.setInt(5, Integer.parseInt(game[4]));
                preparedStatement.setString(6, game[5]);
                preparedStatement.execute();
            }
        } catch(PersistencyErrorException | SQLException e){
            fail();
        }
    }
}
