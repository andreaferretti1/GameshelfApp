package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.entities.VideogamesFound;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VideogamesFoundBeanTest {

    @Test
    void getAndSetVideogamesFoundBeanTest(){
        VideogamesFoundBean videogamesFoundBean  = new VideogamesFoundBean();
        List<VideogameBean> gamesBean = new ArrayList<VideogameBean>();
        videogamesFoundBean.setGamesFoundBean(gamesBean);
        assertEquals(gamesBean, videogamesFoundBean.getVideoamesFoundBean());
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
        Videogame videogame1 = new Videogame(null, 0, 0, null);
        Videogame videogame2 = new Videogame(null, 0, 0, null);
        List<Videogame> games = new ArrayList<Videogame>();
        games.add(videogame1);
        games.add(videogame2);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals(2, videogamesFoundBean.getVideoamesFoundBean().stream().count());
    }
    @Test
    void getInformationFromModelNameTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame("nameTest", 0, 0, null);
        List<Videogame> games = new ArrayList<Videogame>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals("nameTest", videogamesFoundBean.getVideoamesFoundBean().get(1).getName());
    }
    @Test
    void getInformationFromModelCopiesTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 2, 0, null);
        List<Videogame> games = new ArrayList<Videogame>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals(2, videogamesFoundBean.getVideoamesFoundBean().get(1).getCopiesBean());
    }
    @Test
    void getInformationFromModelPriceTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 0, 13.1f, null);
        List<Videogame> games = new ArrayList<Videogame>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals(13.1f, videogamesFoundBean.getVideoamesFoundBean().get(1).getPriceBean());
    }
    @Test
    void getInformationFromModelDescriptionTest(){
        VideogamesFoundBean videogamesFoundBean = new VideogamesFoundBean();
        Videogame videogame = new Videogame(null, 0, 0, "descriptionTest");
        List<Videogame> games = new ArrayList<Videogame>();
        games.add(videogame);
        VideogamesFound videogamesFound = new VideogamesFound();
        videogamesFound.setGamesFound(games);
        videogamesFoundBean.setVideogamesFound(videogamesFound);
        videogamesFoundBean.getInformationFromModel();
        assertEquals("descriptionTest", videogamesFoundBean.getVideoamesFoundBean().get(1).getDescriptionBean());
    }
}