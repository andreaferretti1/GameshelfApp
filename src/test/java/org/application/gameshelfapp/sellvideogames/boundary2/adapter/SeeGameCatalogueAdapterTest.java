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

class SeeGameCatalogueAdapterTest {
    @Test
    void getSellingCatalogueTest(){             //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
            assertNotNull(test.getSellingGamesCatalogue("Dark Souls", "TestConsole2", "TestCategory2"));
        } catch (PersistencyErrorException | NoGameInCatalogueException  e){
            fail();
        }
    }

    @Test
    void getSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
        assertThrows(NoGameInCatalogueException.class, ()->test.getSellingGamesCatalogue("Dark Souls", "TestConsole2", "TestCategory2"));
    }

    @Test
    void addSellingGamesTest(){
        try{
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
            assertNotNull(test.addSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another description", 1, 10f));
        } catch(PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException |
                GmailException | AlreadyExistingVideogameException e){
            fail();
        }
    }

    @Test
    void addSellingGamesInvalidTitleExceptionLaunchedTest() {
        SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
        assertThrows(InvalidTitleException.class, () -> test.addSellingGames("Dark Souls 9", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
    }

        @Test
    void addSellingGamesAlreadyExistingVideogameExceptionLaunchedTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
            assertThrows(AlreadyExistingVideogameException.class, ()->test.addSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
    }

    @Test
    void removeSellingGamesCatalogueTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
            assertNotNull(test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
        } catch(PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException e){
            fail();
        }
    }

    @Test
    void removeSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
        assertThrows(NoGameInCatalogueException.class, ()->test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 1, 10f));
    }

    @Test
    void removeSellingGamesCatalogueGameSolOutExceptionLaunchedTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
        assertThrows(GameSoldOutException.class, ()->test.removeSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
    }

    @Test
    void updateSellingGameTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
            assertNotNull(test.updateSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void updateSellingGameNoGameInCatalogueExceptionLaunchedTest(){
        SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(null);
        assertThrows(NoGameInCatalogueException.class, ()->test.updateSellingGames("Dark Souls", "TestConsole2", "TestCategory2", "This is another test", 2, 10f));
    }

    @Test
    void getUserBeanTest(){
        SeeGameCatalogueAdapter test = new SeeGameCatalogueAdapter(new UserBean());
        assertNotNull(test.getUserBean());

    }
}
