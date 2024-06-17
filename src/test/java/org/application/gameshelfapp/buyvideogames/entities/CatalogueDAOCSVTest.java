package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueDAOCSVTest {

    @Test
    void getCatalogueTest(){        //In the database there was tuple ('emailTest', 'nameTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertNotNull(games);
            Videogame videogame = games.getFirst();
            assertEquals("nameTest", videogame.getName());
            assertEquals(3, videogame.getCopies());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCatalogueDifferentEmailsTest(){     //In the database there were tuples ('emailTest1', 'nameTest', 2), ('emailTest2', 'nameTest2', 1)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest2");
            assertNotNull(games);
            Videogame videogame = games.getFirst();
            assertEquals("nameTest2", videogame.getName());
            assertEquals(1, videogame.getCopies());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCatalogueNoGamesTest(){     //Database was empty
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertNotNull(games);
            assertEquals(0, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void addVideogameTest(){        //Database was empty
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogame = new Videogame("nameTest", 3, 0, null, null, null);
            catalogueDAO.addVideogame("emailTest", videogame);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertNotNull(games);
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void removeVideogameTest(){      //In the database there was tuple ('emailTest', 'nameTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogame = new Videogame("nameTest", 3, 0, null, null, null);
            catalogueDAO.removeVideogame("emailTest", videogame);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertEquals(0, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}