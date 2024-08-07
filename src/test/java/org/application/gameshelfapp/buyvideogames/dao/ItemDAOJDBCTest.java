
package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOJDBCTest {
//test written by Alessandro Zunica. Tests were executed using JDBC DAO. To run these tests, 'PERSISTENCE' key in resources/configuration/configuration.properties file was set to 'JDBC' value

    @BeforeEach
    void setFilters(){
        List<String> consoles = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        consoles.add("consoleTest");
        consoles.add("consoleTest1");
        consoles.add("consoleTest2");
        consoles.add("platformTest");
        consoles.add("TestConsole");
        consoles.add("Test Console");
        categories.add("categoryTest");
        categories.add("TestCategory");
        categories.add("Test Category");
        Filters.setConsoles(consoles);
        Filters.setCategories(categories);
    }

    @AfterEach
    void cleanUp(){
        try {
            String query2 = "TRUNCATE TABLE ObjectOnSale;";
            Connection connection = SingletonConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query2);
        } catch(PersistencyErrorException | SQLException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleGameNameTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("gameTest", gameForSale.getName());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleCopiesTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(2, gameForSale.getCopies());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSalePriceTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(10, gameForSale.getPrice());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleDescriptionTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSalePlatformTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("consoleTest", gameForSale.getPlatform());
            assertEquals("categoryTest", gameForSale.getCategory());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleCategoryTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("categoryTest", gameForSale.getCategory());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogameForSaleWithoutNameTest(){       //to run this test were added games ('gameTest1', 'consoleTest1', 'categoryTest1', 'descriptionTest1', '2', '10'), ('gameTest2', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '3', '10'), ('gameTest3', 'consoleTest2', 'categoryTest2', 'descriptionTest3', '4', '9')
        try{
            Videogame videogame1 = new Videogame("gameTest1", 2, 10, "descriptionTest1", "consoleTest1", "categoryTest");
            Videogame videogame2 = new Videogame("gameTest2", 3, 10, "descriptionTest2", "consoleTest1", "categoryTest");
            Videogame videogame3 = new Videogame("gameTest3", 4, 9, "descriptionTest3", "consoleTest2", "TestCategory");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame1);
            itemDAO.addGameForSale(videogame2);
            itemDAO.addGameForSale(videogame3);
            Filters filters = new Filters(null, "consoleTest1", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(2, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleZeroCopiesTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '0', '10')
        try{
            Videogame videogame = new Videogame("gameTest", 0, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
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
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
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
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());

            filters.setCategory("categoryTest");
            games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void removeGameForSaleTest(){       //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try{
            Videogame videogame = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(1, gameForSale.getCopies());
        } catch(PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void removGameForSaleSamePriceTest() {        //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try {
            Videogame videogame = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(10, gameForSale.getPrice());
        } catch (PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | CheckFailedException e) {
            fail();
        }
    }

    @Test
    void removGameForSaleSameDescriptionTest() {        //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try {
            Videogame videogame = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch (PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | CheckFailedException e) {
            fail();
        }
    }

    @Test
    void removeGameForSaleSoldOutTest(){    //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try {
            Videogame videogame = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Videogame game = new Videogame("nameTest", 4, 10, "descriptionTest", "consoleTest", "categoryTest");
            assertThrows(GameSoldOutException.class, () -> itemDAO.removeGameForSale(game));
        } catch (PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void removeGameForSaleDiffConsoleTest(){        //In the database there were tuples ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest') and ('nameTest', 'consoleTest1', 'categoryTest', '3', '12', 'descriptionTest')
        try{
            Videogame videogame1 = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            Videogame videogame2 = new Videogame("nameTest", 3, 12, "descriptionTest", "consoleTest1", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame1);
            itemDAO.addGameForSale(videogame2);
            Videogame game = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(0, gameForSale.getCopies());
        } catch(PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void checkVideogameExistenceTest(){
        try{
            Filters testFilters = new Filters("Test1", "TestConsole", "TestCategory");
            JDBCFactory jdbcFactory = new JDBCFactory();
            ItemDAO itemDAO = jdbcFactory.createItemDAO();
            itemDAO.checkVideogameExistence(testFilters);
        } catch (AlreadyExistingVideogameException | PersistencyErrorException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void checkVideogameExistenceExceptionLaunchedTest(){                //In the database there exist tuple('Test1', 1, 1, 'TestConsole', 'TestCategory', 'Description Test')
        try{
            Videogame videogame = new Videogame("Test1", 1, 1, "Description Test", "TestConsole", "TestCategory");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters testFilters = new Filters("Test1", "TestConsole", "TestCategory");
            assertThrows(AlreadyExistingVideogameException.class, () -> itemDAO.checkVideogameExistence(testFilters));
        } catch (CheckFailedException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void updateGameForSaleTest(){           //In the database there exist tuple('Test', 1, 1, 'This is a description', 'TestConsole', 'TestCategory')
        try{
            Videogame videogame = new Videogame("Test", 1, 1, "This is a description", "TestConsole", "TestCategory");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Videogame testGame = new Videogame("Test", 2, 5,"Description Test", "TestConsole", "TestCategory");
            itemDAO.updateGameForSale(testGame);
            Filters testFilters = new Filters(testGame.getName(), testGame.getPlatform(), testGame.getCategory());
            List<Videogame> gameListTest = itemDAO.getVideogamesFiltered(testFilters);
            Videogame testUpdate = gameListTest.getFirst();
            assertEquals(3, testUpdate.getCopies());
            assertEquals(5, testUpdate.getPrice());
            assertEquals("Description Test", testUpdate.getDescription());
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getVideogameTest(){        //In the database there was tuple('nameTest', 'consoleTest', 'categoryTest', '1', '10', 'description')
        try{
            Videogame videogame = new Videogame("nameTest", 1, 10, "description", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            assertNotNull(itemDAO.getVideogame(filters));
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }
}
