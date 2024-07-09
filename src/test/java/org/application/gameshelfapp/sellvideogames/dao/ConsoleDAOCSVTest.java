package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleDAOCSVTest {

    @Test
    void getConsolesTest(){         //List of Consoles present in database
        try{
            CSVFactory csvFactory = new CSVFactory();
            ConsoleDAO testDAO = csvFactory.createConsoleDAO();
            assertNotNull(testDAO.getConsoles());
        } catch (PersistencyErrorException e){
            fail();
        }
    }
}
