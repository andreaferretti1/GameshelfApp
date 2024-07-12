package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SingletonConnectionPoolTest {

    @Test
    void getInstanceTest(){
        try {
            SingletonConnectionPool singletonConnectionPool = SingletonConnectionPool.getInstance();
            SingletonConnectionPool singletonConnectionPool1 = SingletonConnectionPool.getInstance();
            assertSame(singletonConnectionPool, singletonConnectionPool1);
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getConnectionTest(){
        try {
            SingletonConnectionPool singletonConnectionPool = SingletonConnectionPool.getInstance();
            Connection connection = singletonConnectionPool.getConnection();
            assertNotNull(connection);
        }catch (PersistencyErrorException | SQLException e) {
            fail();
        }
    }

}