package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SingletonConnectionPoolTest {

    @Test
    void getInstanceTest(){
        SingletonConnectionPool singletonConnectionPool = SingletonConnectionPool.getInstance();
        SingletonConnectionPool singletonConnectionPool1 = SingletonConnectionPool.getInstance();
        assertSame(singletonConnectionPool, singletonConnectionPool1);
    }
    @Test
    void getConnectionTest(){
        try {
            SingletonConnectionPool singletonConnectionPool = SingletonConnectionPool.getInstance();
            Connection connection = singletonConnectionPool.getConnection();
            assertNotNull(connection);
        }catch (PersistencyErrorException e) {
            fail();
        }
    }

}