package org.application.gameshelfapp.sellvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SellVideogamesAdapterTest {
    @Test
    void getSellingCatalogueTest(){             //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellVideogamesAdapter test = new SellVideogamesAdapter(null);
            assertNotNull(test.getSellingGamesCatalogue("Dark Souls", "TestConsole2", "TestCategory2"));
        } catch (PersistencyErrorException | NoGameInCatalogueException  e){
            fail();
        }
    }

    @Test
    void getSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        SellVideogamesAdapter test = new SellVideogamesAdapter(null);
        assertThrows(NoGameInCatalogueException.class, ()->test.getSellingGamesCatalogue("Dark Souls", "TestConsole2", "TestCategory2"));
    }

    @Test
    void addSellingGamesTest(){
        try{
            SellVideogamesAdapter test = new SellVideogamesAdapter(null);
            assertNotNull(test.addSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another description", 1, 10f));
        } catch(PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException |
                GmailException | AlreadyExistingVideogameException e){
            fail();
        }
    }

    @Test
    void addSellingGamesInvalidTitleExceptionLaunchedTest() {
        SellVideogamesAdapter test = new SellVideogamesAdapter(null);
        assertThrows(InvalidTitleException.class, () -> test.addSellingGames("Dark Souls 9", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
    }

        @Test
    void addSellingGamesAlreadyExistingVideogameExceptionLaunchedTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
            SellVideogamesAdapter test = new SellVideogamesAdapter(null);
            assertThrows(AlreadyExistingVideogameException.class, ()->test.addSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
    }

    @Test
    void removeSellingGamesCatalogueTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellVideogamesAdapter test = new SellVideogamesAdapter(null);
            assertNotNull(test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
        } catch(PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        SellVideogamesAdapter test = new SellVideogamesAdapter(null);
        assertThrows(NoGameInCatalogueException.class, ()->test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
    }

    @Test
    void removeSellingGamesCatalogueGameSolOutExceptionLaunchedTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        SellVideogamesAdapter test = new SellVideogamesAdapter(null);
        assertThrows(GameSoldOutException.class, ()->test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
    }

    @Test
    void updateSellingGameTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SellVideogamesAdapter test = new SellVideogamesAdapter(null);
            assertNotNull(test.updateSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void updateSellingGameNoGameInCatalogueExceptionLaunchedTest(){
        SellVideogamesAdapter test = new SellVideogamesAdapter(null);
        assertThrows(NoGameInCatalogueException.class, ()->test.updateSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
    }

    @Test
    void getUserBeanTest(){
        SellVideogamesAdapter test = new SellVideogamesAdapter(new UserBean());
        assertNotNull(test.getUserBean());

    }
}
