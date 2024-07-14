package org.application.gameshelfapp.sellvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerAddGamesBoundaryTest {
//test written by Alessandro Zunica
    @BeforeEach
    void setFilters(){
        List<String> consoles = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        consoles.add("TestConsole2");
        categories.add("TestCategory2");
        Filters.setConsoles(consoles);
        Filters.setCategories(categories);
    }
    @Test
    void getSellingCatalogueTest(){             //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            FiltersBean testBean = new FiltersBean();
            testBean.setNameBean("Dark Souls");
            testBean.setConsoleBean("TestConsole2");
            testBean.setCategoryBean("TestCategory2");
            test.getSellingCatalogue(testBean);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch (PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void getSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            FiltersBean testBean = new FiltersBean();
            testBean.setNameBean("Dark Souls");
            testBean.setConsoleBean("TestConsole2");
            testBean.setCategoryBean("TestCategory2");
            assertThrows(NoGameInCatalogueException.class, () -> test.getSellingCatalogue(testBean));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addSellingGamesTest(){
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.addSellingGames(gameBeanTest);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch(PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException | GmailException | AlreadyExistingVideogameException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addSellingGamesInvalidTitleExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls 9");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(InvalidTitleException.class, () -> test.addSellingGames(gameBeanTest));
        }catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addSellingGamesAlreadyExistingVideogameExceptionLaunchedTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(AlreadyExistingVideogameException.class, () -> test.addSellingGames(gameBeanTest));
        }catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.removeSellingGames(gameBeanTest);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException | WrongUserTypeException |
                CheckFailedException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(NoGameInCatalogueException.class, () -> test.removeSellingGames(gameBeanTest));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueGameSolOutExceptionLaunchedTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(GameSoldOutException.class, () -> test.removeSellingGames(gameBeanTest));
        }catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void updateSellingGameTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.updateSellingGame(gameBeanTest);
            assertNotNull(test.getSellingGamesCatalogueBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void updateSellingGameNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            assertThrows(NoGameInCatalogueException.class, () -> test.updateSellingGame(gameBeanTest));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void setAndGetUserBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            UserBean testUser = new UserBean();
            test.setUserBean(testUser);
            assertEquals(testUser, test.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getCategoriesTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            assertNotNull(test.getCategories());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getConsolesTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAddGamesBoundary test = new SellerAddGamesBoundary(userBean);
            assertNotNull(test.getConsoles());
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}
