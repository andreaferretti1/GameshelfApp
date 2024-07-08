package org.application.gameshelfapp.updatevideogame;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.controller.SellVideogamesController;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UpdateVideogameControllerTest {

    @Test
    void updateGameInCatalogueCopiesTest(){           //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
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
            ItemDAO testDao = PersistencyAbstractFactory.getFactory().createItemDAO();
            List<Videogame> testList = testDao.getVideogamesFiltered(new Filters("Dark Souls","TestConsole2","TestCategory2"));
            Videogame gameTest = testList.getFirst();
            assertEquals(3, gameTest.getCopies());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void updateGameInCataloguePriceTest(){           //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
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
            ItemDAO testDao = PersistencyAbstractFactory.getFactory().createItemDAO();
            List<Videogame> testList = testDao.getVideogamesFiltered(new Filters("Dark Souls","TestConsole2","TestCategory2"));
            Videogame gameTest = testList.getFirst();
            assertEquals(15, gameTest.getPrice());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

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
            ItemDAO testDao = PersistencyAbstractFactory.getFactory().createItemDAO();
            List<Videogame> testList = testDao.getVideogamesFiltered(new Filters("Dark Souls","TestConsole2","TestCategory2"));
            Videogame gameTest = testList.getFirst();
            assertEquals("This is a new description", gameTest.getDescription());
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
