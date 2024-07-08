package org.application.gameshelfapp.confirmsale.dao.utils;

import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

public class SaleDAOJDBCUtils {


    public static void insertRecord(String[][] sales){
        try(Connection connection = SingletonConnectionPool.getInstance().getConnection()){
            String query = "INSERT INTO Sale (Copies, Price, State, GameName, Platform, Name, UserEmail, UserAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

            for(String[] sale: sales){
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(sale[0]));
                pstmt.setFloat(2, Float.parseFloat(sale[1]));
                pstmt.setString(3, sale[2]);
                pstmt.setString(4, sale[3]);
                pstmt.setString(5, sale[4]);
                pstmt.setString(6, sale[5]);
                pstmt.setString(7, sale[6]);
                pstmt.setString(8, sale[7]);

                pstmt.execute();
            }
        } catch(SQLException | PersistencyErrorException e){
            fail();
        }
    }

    public static void truncateTable(){
        try(Connection connection = SingletonConnectionPool.getInstance().getConnection()){
             String query = "TRUNCATE TABLE Sale;";
             connection.createStatement().execute(query);
        } catch(SQLException | PersistencyErrorException e){
            fail();
        }
    }
}
