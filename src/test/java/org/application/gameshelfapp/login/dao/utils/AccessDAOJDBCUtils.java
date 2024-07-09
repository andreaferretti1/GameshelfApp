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
            for (String[] myRecord : records) {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, myRecord[0]);
                pstmt.setString(2, myRecord[1]);
                pstmt.setString(3, myRecord[2]);
                pstmt.setString(4, myRecord[3]);
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
