package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueDAOCSVTest {

    @Test
    void getCatalogueTest(){        //In the database there was tuple ('emailTest', 'nameTest', 'platformTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCatalogueGameNameTest(){        //In the database there was tuple ('emailTest', 'nameTest', 'platformTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            Videogame game = games.getFirst();
            assertEquals("nameTest", game.getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCataloguePlatformTest(){        //In the database there was tuple ('emailTest', 'nameTest', 'platformTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            Videogame videogame = games.getFirst();
            assertEquals("platformTest", videogame.getPlatform());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCatalogueCopiesTest(){        //In the database there was tuple ('emailTest', 'nameTest', 'platformTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            Videogame videogame = games.getFirst();
            assertEquals(3, videogame.getCopies());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCatalogueMultipleGamesTest(){        //In the database there were tuple ('emailTest', 'nameTest', 'platformTest', 3) and ('emailTest', 'nameTest1', 'platformTest1', 4)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertEquals(2, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCatalogueDifferentEmailsTest(){     //In the database there were tuples ('emailTest1', 'nameTest', 'platformTest', 2), ('emailTest2', 'nameTest2', 'platformTest1', 1)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest2");
            assertEquals(1, (long) games.size());
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
            Videogame videogame = new Videogame("nameTest", 3, 0, null, "platformTest", null);
            catalogueDAO.addVideogame("emailTest", videogame);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void addVideogameGameNameTest(){        //Database was empty
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogameToSave = new Videogame("nameTest", 3, 0, null, "platformTest", null);
            catalogueDAO.addVideogame("emailTest", videogameToSave);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            Videogame videogame = games.getFirst();
            assertEquals("nameTest", videogame.getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void addVideogamePlatformTest(){        //Database was empty
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogameToSave = new Videogame("nameTest", 3, 0, null, "platformTest", null);
            catalogueDAO.addVideogame("emailTest", videogameToSave);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            Videogame videogame = games.getFirst();
            assertEquals("platformTest", videogame.getPlatform());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void addVideogameCopiesTest(){        //Database was empty
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogameToSave = new Videogame("nameTest", 3, 0, null, "platformTest", null);
            catalogueDAO.addVideogame("emailTest", videogameToSave);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            Videogame videogame = games.getFirst();
            assertEquals(3, videogame.getCopies());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void removeVideogameTest(){      //In the database there was tuple ('emailTest', 'nameTest', 'platformTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogame = new Videogame("nameTest", 3, 0, null, "platformTest", null);
            catalogueDAO.removeVideogame("emailTest", videogame);
            List<Videogame> games = catalogueDAO.getCatalogue("emailTest");
            assertEquals(0, (long) games.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}