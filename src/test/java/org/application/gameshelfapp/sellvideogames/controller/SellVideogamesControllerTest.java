package org.application.gameshelfapp.sellvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SellVideogamesControllerTest {

    @Test
    void showSellingGameCatalogueTest(){            //Videogame(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellVideogamesController test = new SellVideogamesController();
            FiltersBean testFiltersBean = new FiltersBean();
            testFiltersBean.setNameBean("Dark Souls");
            testFiltersBean.setConsoleBean("TestConsole2");
            testFiltersBean.setCategoryBean("TestCategory2");
            SellingGamesCatalogueBean testCatalogueBean = test.showSellingGamesCatalogue(testFiltersBean);
            assertNotNull(testCatalogueBean);
            assertEquals(1, testCatalogueBean.getSellingGamesBean().size());
        } catch (PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void showSellingGameCatalogueNoGameInCatalogueExceptionLaunchedTest(){           //Database is Empty
        SellVideogamesController test = new SellVideogamesController();
        FiltersBean testFiltersBean = new FiltersBean();
        testFiltersBean.setNameBean("Test1");
        testFiltersBean.setConsoleBean("TestConsole1");
        testFiltersBean.setCategoryBean("TestCategory1");
        assertThrows(NoGameInCatalogueException.class, ()->test.showSellingGamesCatalogue(testFiltersBean));
    }

    @Test
    void addGameToCatalogueTest(){          //Database is empty
        try{
           SellVideogamesController test = new SellVideogamesController();
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
        } catch (PersistencyErrorException | InvalidTitleException | CheckFailedException | NoGameInCatalogueException | GmailException | AlreadyExistingVideogameException e){
            fail();
        }
    }

    @Test
    void addGameToCatalogueAlreadyExistingVideogameExceptionLaunchedTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        SellVideogamesController test = new SellVideogamesController();
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(1);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(AlreadyExistingVideogameException.class, ()-> test.addGameToCatalogue(gameBeanTest));
    }

    @Test
    void cancelGameFromCatalogueTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellVideogamesController test = new SellVideogamesController();
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
        } catch (PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException e){
            fail();
        }
    }

    @Test
    void cancelGameFromCatalogueGameSoldOutExceptionLaunchedTest(){     //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        SellVideogamesController test = new SellVideogamesController();
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(GameSoldOutException.class, ()-> test.cancelGameFromCatalogue(gameBeanTest));
    }

    @Test
    void cancelGameFromCatalogueNoGameInCatalogueExceptionLaunchedTest(){           //Database is empty
        SellVideogamesController test = new SellVideogamesController();
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Test");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(NoGameInCatalogueException.class, ()-> test.cancelGameFromCatalogue(gameBeanTest));
    }

    @Test
    void modifyGameInCatalogueTest(){           //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            SellVideogamesController test = new SellVideogamesController();
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
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void modifyGameInCatalogueExceptionLaunchedTest(){          //Databse is empty
        SellVideogamesController test = new SellVideogamesController();
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Test");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(15);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is a new description");
        assertThrows(NoGameInCatalogueException.class, ()->test.modifyGameInCatalogue(gameBeanTest));
    }
}