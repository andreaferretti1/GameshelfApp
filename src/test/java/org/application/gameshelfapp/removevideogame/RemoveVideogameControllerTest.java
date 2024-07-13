package org.application.gameshelfapp.removevideogame;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class RemoveVideogameControllerTest {
    @BeforeEach
    void setFilters(){
        Filters.consoles = new ArrayList<>();
        Filters.categories = new ArrayList<>();
        Filters.consoles.add("TestConsole2");
        Filters.categories.add("TestCategory2");
    }
    @Test
    void removeGameFromCatalogueTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            RemoveVideogameController test = new RemoveVideogameController();
            VideogameBean gameBeanTest = new VideogameBean();
            gameBeanTest.setName("Dark Souls");
            gameBeanTest.setCopiesBean(1);
            gameBeanTest.setPriceBean(10);
            gameBeanTest.setPlatformBean("TestConsole2");
            gameBeanTest.setCategoryBean("TestCategory2");
            gameBeanTest.setDescriptionBean("This is another test");
            test.removeGameFromCatalogue(gameBeanTest);
            ItemDAO testDao = PersistencyAbstractFactory.getFactory().createItemDAO();
            List<Videogame> testList = testDao.getVideogamesFiltered(new Filters("Dark Souls","TestConsole2","TestCategory2"));
            assertEquals(0, (long)testList.size());
        } catch (PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void removeGameFromCatalogueGameSoldOutExceptionLaunchedTest(){     //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        RemoveVideogameController test = new RemoveVideogameController();
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Dark Souls");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(GameSoldOutException.class, ()-> test.removeGameFromCatalogue(gameBeanTest));
    }

    @Test
    void removeGameFromCatalogueNoGameInCatalogueExceptionLaunchedTest(){           //Database is empty
        RemoveVideogameController test = new RemoveVideogameController();
        VideogameBean gameBeanTest = new VideogameBean();
        gameBeanTest.setName("Test");
        gameBeanTest.setCopiesBean(2);
        gameBeanTest.setPriceBean(10);
        gameBeanTest.setPlatformBean("TestConsole2");
        gameBeanTest.setCategoryBean("TestCategory2");
        gameBeanTest.setDescriptionBean("This is another test");
        assertThrows(NoGameInCatalogueException.class, ()-> test.removeGameFromCatalogue(gameBeanTest));
    }
}
