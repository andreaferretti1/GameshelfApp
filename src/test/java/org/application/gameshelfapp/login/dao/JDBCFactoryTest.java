package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.buyvideogames.dao.ItemDAOJDBC;
import org.application.gameshelfapp.confirmsale.dao.SaleDAOJDBC;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class JDBCFactoryTest {

    @Test
    void createItemDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(ItemDAOJDBC.class, jdbcFactory.createItemDAO());
    }

    @Test
    void createAccessDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(AccessDAOJDBC.class, jdbcFactory.createAccessDAO());
    }

    @Test
    void createSaleDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(SaleDAOJDBC.class, jdbcFactory.createSaleDAO());
    }
}