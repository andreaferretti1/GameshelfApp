package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOJDBCTest {

    @Test
    void getVideogamesForSaleByNameTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("gameTest", gameForSale.getName());
            assertEquals(2, gameForSale.getCopies());
            assertEquals(10, gameForSale.getPrice());
            assertEquals("descriptionTest", gameForSale.getDescription());
            assertEquals("consoleTest", gameForSale.getPlatform());
            assertEquals("categoryTest", gameForSale.getCategory());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void getVideogameForSaleByCategoryTest(){       //to run this test were added games ('gameTest1', 'consoleTest1', 'categoryTest1', 'descriptionTest1', '2', '10'), ('gameTest2', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '3', '10'), ('gameTest3', 'consoleTest2', 'categoryTest2', 'descriptionTest3', '4', '9')
        try{
            Filters filters = new Filters(null, "consoleTest1", "categoryTest1");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            assertEquals(2, (long) games.size());
            Videogame game1 = games.getFirst();
            Videogame game2 = games.getLast();
            assertEquals("gameTest1", game1.getName());
            assertEquals("gameTest2", game2.getName());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleZeroCopiesTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '0', '10')
        try{
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            assertEquals(0, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void addGameForSaleNotExisting(){
        try{
            Videogame game = new Videogame("nameTest", 2, 10, "descriptionTest", "platformTest", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            itemDAO.addGameForSale(game);
            Filters filters = new Filters("nameTest", "platformTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            assertEquals(1, (long) games.size());
            Videogame gameForSale = games.getFirst();
            assertEquals("nameTest", gameForSale.getName());
            assertEquals(2, gameForSale.getCopies());
            assertEquals(10, gameForSale.getPrice());
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void addGameForSaleExistingTest(){      //in the database there was already the tuple('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try{
            Videogame game = new Videogame("nameTest", 3, 12, "descriptionTest", "consoleTest", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            itemDAO.addGameForSale(game);
            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            assertEquals(1, (long) games.size());
            Videogame gameForSale = games.getFirst();
            assertEquals(5, gameForSale.getCopies());
            assertEquals(12, gameForSale.getPrice());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }
    @Test
    void addGameForSaleExistingDiffConsoleTest(){       //in the database there was the tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try{
            Videogame game = new Videogame("nameTest", 3, 11, "descriptionTest", "consoleTest2", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            itemDAO.addGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest2", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            assertEquals(1, (long) games.size());
            Videogame gameForSale = games.getFirst();
            assertEquals("nameTest", gameForSale.getName());
            assertEquals(3, gameForSale.getCopies());
            assertEquals(10, gameForSale.getPrice());
            assertEquals("descriptionTest", gameForSale.getDescription());

            filters.setCategory("categoryTest");
            games = itemDAO.getVideogamesForSale(filters);
            assertEquals(1, (long) games.size());
            gameForSale = games.getFirst();
            assertEquals("nameTest", gameForSale.getName());
            assertEquals(2, gameForSale.getCopies());
            assertEquals(10, gameForSale.getPrice());
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }
    @Test
    void removeGameForSaleTest(){       //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try{
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("nameTest", gameForSale.getName());
            assertEquals(1, gameForSale.getCopies());
            assertEquals(10, gameForSale.getPrice());
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch(PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException e){
            fail();
        }
    }
    @Test
    void removeGameForSaleSoldOutTest(){        //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        Videogame game = new Videogame("nameTest", 4, 10, "descriptionTest", "consoleTest", "categoryTest");
        JDBCFactory jdbcFactory = new JDBCFactory();
        ItemDAO itemDAO = jdbcFactory.createItemDAO();
        assertThrows(GameSoldOutException.class, () -> itemDAO.removeGameForSale(game));
    }
    @Test
    void removeGameForSaleDiffConsoleTest(){        //In the database there were tuples ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest') and ('nameTest', 'consoleTest1', 'categoryTest', '3', '12', 'descriptionTest')
        try{
            Videogame game = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesForSale(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("nameTest", gameForSale.getName());
            assertEquals(0, gameForSale.getCopies());

            filters.setConsole("consoleTest1");
            games = itemDAO.getVideogamesForSale(filters);
            gameForSale = games.getFirst();
            assertEquals("nameTest", gameForSale.getName());
            assertEquals(3, gameForSale.getCopies());
        } catch(PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException e){
            fail();
        }
    }
}