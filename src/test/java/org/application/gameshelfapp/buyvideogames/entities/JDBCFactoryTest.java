package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JDBCFactoryTest {

    @Test
    void createItemDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(ItemDAOJDBC.class, jdbcFactory.createItemDAO());
    }

    @Test
    void createCatalogueDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(CatalogueDAOJDBC.class, jdbcFactory.createCatalogueDAO());
    }

    @Test
    void createAccessDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(JDBCFactory.class, jdbcFactory.createAccessDAO());
    }

    @Test
    void createSaleDAOTest() {
        JDBCFactory jdbcFactory = new JDBCFactory();
        assertInstanceOf(SaleDAOJDBC.class, jdbcFactory.createSaleDAO());
    }
}