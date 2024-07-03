package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.entities.VideogamesFound;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideogamesFoundBeanTest {

    @Test
    void getAndSetVideogamesFoundBeanTest(){
        VideogamesFoundBean videogamesFoundBean  = new VideogamesFoundBean();
        List<VideogameBean> gamesBean = new ArrayList<>();
        videogamesFoundBean.setGamesFoundBean(gamesBean);
        assertEquals(gamesBean, videogamesFoundBean.getVideogamesFoundBean());
    }
    @Test
    void getAndSetVideogamesFoundTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        assertEquals(videogamesFound, videogamesFoundBean.getVideogamesFound());
    }
    @Test
    void getInformationFromModelTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame1 = new Videogame(null, 0, 0, null, null, null);
        Videogame videogame2 = new Videogame(null, 0, 0, null, null, null);
        List<Videogame> games = new ArrayList<>();
        games.add(videogame1);
        games.add(videogame2);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals(2, (long) videogamesFoundBean.getVideogamesFoundBean().size());
    }
    @Test
    void getInformationFromModelNameTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame("nameTest", 0, 0, null, null, null);
        List<Videogame> games = new ArrayList<>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals("nameTest", videogamesFoundBean.getVideogamesFoundBean().get(1).getName());
    }
    @Test
    void getInformationFromModelCopiesTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 2, 0, null, null, null);
        List<Videogame> games = new ArrayList<>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals(2, videogamesFoundBean.getVideogamesFoundBean().get(1).getCopiesBean());
    }
    @Test
    void getInformationFromModelPriceTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 0, 13.1f, null, null, null);
        List<Videogame> games = new ArrayList<>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals(13.1f, videogamesFoundBean.getVideogamesFoundBean().get(1).getPriceBean());
    }
    @Test
    void getInformationFromModelDescriptionTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 0, 0, "descriptionTest", null, null);
        List<Videogame> games = new ArrayList<>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals("descriptionTest", videogamesFoundBean.getVideogamesFoundBean().get(1).getDescriptionBean());
    }

    @Test
    void getInformationFromModelPlatformTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 0, 0, null, "platformTest", null);
        List<Videogame> games = new ArrayList<>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals("platformTest", videogamesFoundBean.getVideogamesFoundBean().getFirst().getPlatformBean());
    }

    @Test
    void getInformationFromModelCategoryBeanTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 0, 0, null, null, "categoryTest");
        List<Videogame> games = new ArrayList<>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals("categoryTest", videogamesFoundBean.getVideogamesFoundBean().getFirst().getCategoryBean());
    }
}