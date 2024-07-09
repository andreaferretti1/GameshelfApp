package org.application.gameshelfapp.sellvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SeeGameCatalogueAdapterTest {
    @Test
    void getSellingCatalogueTest(){             //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertNotNull(test.getSellingGamesCatalogue("Dark Souls", "TestConsole2", "TestCategory2"));
        } catch (PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertThrows(NoGameInCatalogueException.class, () -> test.getSellingGamesCatalogue("Dark Souls", "TestConsole2", "TestCategory2"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addSellingGamesTest(){
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertNotNull(test.addSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another description", 1, 10f));
        } catch(PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException |
                GmailException | AlreadyExistingVideogameException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void addSellingGamesInvalidTitleExceptionLaunchedTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertThrows(InvalidTitleException.class, () -> test.addSellingGames("Dark Souls 9", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

        @Test
    void addSellingGamesAlreadyExistingVideogameExceptionLaunchedTest() {            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertThrows(AlreadyExistingVideogameException.class, () -> test.addSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertNotNull(test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
        } catch(PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertThrows(NoGameInCatalogueException.class, () -> test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueGameSolOutExceptionLaunchedTest() {          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertThrows(GameSoldOutException.class, () -> test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void updateSellingGameTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertNotNull(test.updateSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void updateSellingGameNoGameInCatalogueExceptionLaunchedTest() {
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertThrows(NoGameInCatalogueException.class, () -> test.updateSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getUserBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            assertNotNull(test.getUserBean());
        }catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getFiltersCategoriesTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            Map<String, String[]> filters = test.getFilters();
            assertNotNull(filters.get("Category"));
        } catch (PersistencyErrorException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getFiltersConsolesTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(userBean);
            Map<String, String[]> filters = test.getFilters();
            assertNotNull(filters.get("Console"));
        } catch (PersistencyErrorException | WrongUserTypeException e){
            fail();
        }
    }
}
