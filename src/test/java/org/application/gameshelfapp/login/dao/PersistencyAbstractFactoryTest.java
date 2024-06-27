package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersistencyAbstractFactoryTest {

    //run these tests setting the right property
    @Test
    void getJDBCFactoryTest(){
        PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
        assertInstanceOf(JDBCFactory.class, factory);
    }
    @Test
    void getCSVFactoryTest(){
        PersistencyAbstractFactory factory = PersistencyAbstractFactory.getFactory();
        assertInstanceOf(CSVFactory.class, factory);
    }
}