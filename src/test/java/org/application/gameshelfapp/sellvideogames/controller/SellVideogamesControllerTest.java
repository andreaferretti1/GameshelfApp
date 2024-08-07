package org.application.gameshelfapp.sellvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
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
class SellVideogamesControllerTest {
//test written by Alessandro Zunica. Tests were executed using JDBC DAO. To run these tests, 'PERSISTENCE' key in resources/configuration/configuration.properties file was set to 'JDBC' value
    @BeforeEach
    void setFilters(){
        List<String> consoles = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        consoles.add("TestConsole2");
        consoles.add("TestConsole1");
        categories.add("TestCategory2");
        categories.add("TestCategory1");
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
    void showSellingGameCatalogueTest(){            //Videogame(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            Videogame videogame = new Videogame("Dark Souls", 1, 10, "This is another test", "TestConsole2", "TestCategory2");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            FiltersBean testFiltersBean = new FiltersBean();
            testFiltersBean.setNameBean("Dark Souls");
            testFiltersBean.setConsoleBean("TestConsole2");
            testFiltersBean.setCategoryBean("TestCategory2");
            SellingGamesCatalogueBean testCatalogueBean = test.showSellingGamesCatalogue(testFiltersBean);
            assertNotNull(testCatalogueBean);
            assertEquals(1, testCatalogueBean.getSellingGamesBean().size());
        } catch (PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void showSellingGameCatalogueNoGameInCatalogueExceptionLaunchedTest(){           //Database is Empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            FiltersBean testFiltersBean = new FiltersBean();
            testFiltersBean.setNameBean("Test1");
            testFiltersBean.setConsoleBean("TestConsole1");
            testFiltersBean.setCategoryBean("TestCategory1");
            assertThrows(NoGameInCatalogueException.class, () -> test.showSellingGamesCatalogue(testFiltersBean));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void obtainCategoriesTest(){            //List of Categories exist in database
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            assertNotNull(test.obtainCategories());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void obtainConsolesTest(){          //List of Categories exist in database
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            assertNotNull(test.obtainConsoles());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addGameToCatalogueTest(){          //Database is empty
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            SellingGamesCatalogueBean testCatalogueBean = test.addGameToCatalogue(gameBeanTest);
            assertNotNull(testCatalogueBean);
            assertEquals(1, testCatalogueBean.getSellingGamesBean().size());
        } catch (PersistencyErrorException | InvalidTitleException | CheckFailedException | NoGameInCatalogueException | GmailException | AlreadyExistingVideogameException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addGameToCatalogueAlreadyExistingVideogameExceptionLaunchedTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            Videogame videogame = new Videogame("Dark Souls", 1, 10, "This is another test", "TestConsole2", "TestCategory2");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(AlreadyExistingVideogameException.class, () -> test.addGameToCatalogue(gameBeanTest));
        }catch (WrongUserTypeException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void cancelGameFromCatalogueTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            Videogame videogame = new Videogame("Dark Souls", 1, 10, "This is another test", "TestConsole2", "TestCategory2");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            SellingGamesCatalogueBean testBean = test.cancelGameFromCatalogue(gameBeanTest);
            gameBeanTest = testBean.getSellingGamesBean().getFirst();
            assertEquals(0, gameBeanTest.getCopiesBean());
        } catch (PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException |
                 WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void cancelGameFromCatalogueGameSoldOutExceptionLaunchedTest(){     //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            Videogame videogame = new Videogame("Dark Souls", 1, 10, "This is another test", "TestConsole2", "TestCategory2");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(GameSoldOutException.class, () -> test.cancelGameFromCatalogue(gameBeanTest));
        } catch (WrongUserTypeException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void cancelGameFromCatalogueNoGameInCatalogueExceptionLaunchedTest(){           //Database is empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Test");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(NoGameInCatalogueException.class, () -> test.cancelGameFromCatalogue(gameBeanTest));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void modifyGameInCatalogueTest(){           //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            Videogame videogame = new Videogame("Dark Souls", 1, 10, "This is another test", "TestConsole2", "TestCategory2");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(15);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is a new description");
            SellingGamesCatalogueBean testBean = test.modifyGameInCatalogue(gameBeanTest);
            gameBeanTest = testBean.getSellingGamesBean().getFirst();
            assertEquals(3, gameBeanTest.getCopiesBean());
            assertEquals(15, gameBeanTest.getPriceBean());
            assertEquals("This is a new description", gameBeanTest.getDescriptionBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void modifyGameInCatalogueExceptionLaunchedTest(){          //Databse is empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellVideogamesController test = new SellVideogamesController(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Test");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(15);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is a new description");
            assertThrows(NoGameInCatalogueException.class, () -> test.modifyGameInCatalogue(gameBeanTest));
        }catch (WrongUserTypeException e){
            fail();
        }
    }
}
