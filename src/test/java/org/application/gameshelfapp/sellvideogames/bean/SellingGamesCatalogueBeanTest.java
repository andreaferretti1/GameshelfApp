package org.application.gameshelfapp.sellvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.sellvideogames.entities.SellingGamesCatalogue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;


class SellingGamesCatalogueBeanTest {
    @Test
    void setAndGetSellingGamesBeanTest(){
        SellingGamesCatalogueBean test = new SellingGamesCatalogueBean();
        test.setSellingGamesBean(new ArrayList<>());
        assertNotNull(test.getSellingGamesBean());
    }

    @Test
    void setAndGetSubjectTest(){
        SellingGamesCatalogueBean test = new SellingGamesCatalogueBean();
        test.setSubject(new SellingGamesCatalogue());
        assertNotNull(test.getSubject());
    }

    @Test
    void updateTest(){
        SellingGamesCatalogueBean test = new SellingGamesCatalogueBean();
        SellingGamesCatalogue testCatalogue = new SellingGamesCatalogue();
        Videogame testGame = new Videogame("Test", 10, 19,"This is a test","TestPlatform", "TestCategory");
        List<Videogame> testList = new ArrayList<>();
        testList.add(testGame);
        testCatalogue.setSellingGames(testList);
        test.setSubject(testCatalogue);
        test.update();
        assertNotNull(test.getSellingGamesBean());
        assertEquals(1, test.getSellingGamesBean().size());
    }
}
