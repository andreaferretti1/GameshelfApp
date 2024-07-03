package org.application.gameshelfapp.sellvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.controller.SellGamesController;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellerAddGamesBoundaryTest {

    @Test
    void getSellingCatalogueTest(){             //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellGamesController testController = new SellGamesController();
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
            FiltersBean testBean = new FiltersBean();
            testBean.setNameBean("Dark Souls");
            testBean.setConsoleBean("TestConsole2");
            testBean.setCategoryBean("TestCategory2");
            test.getSellingCatalogue(testBean);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch (PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void getSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        FiltersBean testBean = new FiltersBean();
        testBean.setNameBean("Dark Souls");
        testBean.setConsoleBean("TestConsole2");
        testBean.setCategoryBean("TestCategory2");
        assertThrows(NoGameInCatalogueException.class, ()->test.getSellingCatalogue(testBean));
    }

    @Test
    void addSellingGamesTest(){
        try{
            SellGamesController testController = new SellGamesController();
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.addSellingGames(gameBeanTest);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch(PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException | GmailException | AlreadyExistingVideogameException e){
            fail();
        }
    }

    @Test
    void addSellingGamesInvalidTitleExceptionLaunchedTest(){
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls 9");
        gameBeanTest.setCopiesBean(1);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(InvalidTitleException.class, ()-> test.addSellingGames(gameBeanTest));
    }

    @Test
    void addSellingGamesAlreadyExistingVideogameExceptionLaunchedTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(1);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(AlreadyExistingVideogameException.class, ()->test.addSellingGames(gameBeanTest));
    }

    @Test
    void removeSellingGamesCatalogueTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellGamesController testController = new SellGamesController();
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.removeSellingGames(gameBeanTest);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(1);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(NoGameInCatalogueException.class, ()->test.removeSellingGames(gameBeanTest));
    }

    @Test
    void removeSellingGamesCatalogueGameSolOutExceptionLaunchedTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(GameSoldOutException.class, ()->test.removeSellingGames(gameBeanTest));
    }

    @Test
    void updateSellingGameTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellGamesController testController = new SellGamesController();
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.updateSellingGame(gameBeanTest);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void updateSellingGameNoGameInCatalogueExceptionLaunchedTest(){
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(NoGameInCatalogueException.class, ()->test.updateSellingGame(gameBeanTest));
    }

    @Test
    void setAndGetUserBeanTest(){
        SellGamesController testController = new SellGamesController();
        SellerAddGamesBoundary test = new SellerAddGamesBoundary(testController, null);
        UserBean testUser = new UserBean();
        test.setUserBean(testUser);
        assertEquals(testUser, test.getUserBean());
    }
}
