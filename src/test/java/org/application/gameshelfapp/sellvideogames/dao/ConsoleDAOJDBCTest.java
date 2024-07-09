package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleDAOJDBCTest {

    @Test
    void getConsoles(){             //List of Consoles exist in database
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            ConsoleDAO consoleDAO = jdbcFactory.createConsoleDAO();
            assertNotNull(consoleDAO.getConsoles());
        } catch (PersistencyErrorException e){
            fail();
        }
    }
}
