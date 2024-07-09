package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOCSVTest {

    @Test
    void getCategoryTest(){             //List of Categories present in database
        try{
            CSVFactory csvFactory = new CSVFactory();
            CategoryDAO testDAO = csvFactory.createCategoryDAO();
            assertNotNull(testDAO.getCategories());
        } catch (PersistencyErrorException e){
            fail();
        }
    }

}
