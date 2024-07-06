package org.application.gameshelfapp.updatevideogame;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.controller.SellVideogamesController;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateVideogameControllerTest {

    @Test
    void updateGameInCatalogueTest(){           //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UpdateVideogameController test = new UpdateVideogameController();
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(2);
            gameBeanTest.setPriceBean(15);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is a new description");
            test.updateGameInCatalogue(gameBeanTest);
            assertEquals(3, gameBeanTest.getCopiesBean());
            assertEquals(15, gameBeanTest.getPriceBean());
            assertEquals("This is a new description", gameBeanTest.getDescriptionBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void updateGameInCatalogueExceptionLaunchedTest(){          //Databse is empty
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
