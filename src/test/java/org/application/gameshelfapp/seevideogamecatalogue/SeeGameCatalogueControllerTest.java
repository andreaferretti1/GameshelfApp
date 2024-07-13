package org.application.gameshelfapp.seevideogamecatalogue;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SeeGameCatalogueControllerTest {
    @BeforeEach
    void setFilters(){
        Filters.consoles = new ArrayList<>();
        Filters.categories = new ArrayList<>();
        Filters.consoles.add("TestConsole2");
        Filters.categories.add("TestCategory2");
    }
    @Test
    void displaySellingGamesCatalogueTest(){        //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            SeeGameCatalogueController testCon = new SeeGameCatalogueController();
            VideogameBean test = new VideogameBean();
            FiltersBean testFilters = new FiltersBean();
            testFilters.setNameBean("Dark Souls");
            testFilters.setConsoleBean("TestConsole2");
            testFilters.setCategoryBean("TestCategory2");
            test.setName("Dark Souls");
            test.setPlatformBean("TestConsole2");
            test.setCategoryBean("TestCategory2");
            test.setDescriptionBean("This is another test");
            test.setCopiesBean(1);
            test.setPriceBean(10);
            VideogameBean testGame = testCon.displaySellingGamesCatalogue(testFilters).getSellingGamesBean().getFirst();
            assertEquals(test, testGame);
        } catch (PersistencyErrorException | NoGameInCatalogueException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void displaySellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){      //Database is empty
        SeeGameCatalogueController testCon = new SeeGameCatalogueController();
        FiltersBean testFilters = new FiltersBean();
        testFilters.setNameBean("Dark Souls");
        testFilters.setConsoleBean("TestConsole2");
        testFilters.setCategoryBean("TestCategory2");
        assertThrows(NoGameInCatalogueException.class, ()-> testCon.displaySellingGamesCatalogue(testFilters));
    }

    @Test
    void getCategoriesValueTest(){
        try {
            SeeGameCatalogueController test = new SeeGameCatalogueController();
            assertNotNull(test.getCategoriesValue());
        } catch (PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getConsolesValueTest(){
        try {
            SeeGameCatalogueController test = new SeeGameCatalogueController();
            assertNotNull(test.getConsolesValue());
        } catch (PersistencyErrorException e){
            fail();
        }
    }
}
