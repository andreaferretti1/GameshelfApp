package org.application.gameshelfapp.login.dao.utils;

import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.fail;

public class AccessDAOJDBCUtils {

    public static void insertRecords(String[][] records){
        try(Connection connection = SingletonConnectionPool.getInstance().getConnection()) {
            String query = "INSERT INTO User(Username, Email, Password, Type) VALUES(?, ?, ?, ?);";
            for (String[] record : records) {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, record[0]);
                pstmt.setString(2, record[1]);
                pstmt.setString(3, record[2]);
                pstmt.setString(4, record[3]);
            }
        } catch(SQLException | PersistencyErrorException e){
            fail();
        }
    }

    public static void truncateTable(){
        try(Connection connection = SingletonConnectionPool.getInstance().getConnection()){
            String query = "TRUNCATE TABLE User;";
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch(SQLException | PersistencyErrorException e){
            fail();
        }
    }
}
