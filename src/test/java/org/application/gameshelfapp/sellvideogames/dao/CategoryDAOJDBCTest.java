package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOJDBCTest {

    @Test
    void getCategoriesTest(){           //List of Categories exist in database
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            CategoryDAO categoryDAO = jdbcFactory.createCategoryDAO();
            assertNotNull(categoryDAO.getCategories());
        } catch (PersistencyErrorException e){
            fail();
        }
    }
}
