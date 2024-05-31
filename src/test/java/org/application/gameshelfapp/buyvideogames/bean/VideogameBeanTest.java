package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VideogameBeanTest {

    @Test
    void getAndSetNameTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("nameTest");
        assertEquals("nameTest", videogameBean.getName());
    }
    @Test
    void getAndSetCopiesBean(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setCopiesBean(5);
        assertEquals(5, videogameBean.getCopiesBean());
    }
    @Test
    void getAndSetPriceTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setPriceBean(13.1f);
        assertEquals(13.1f, videogameBean.getPriceBean());
    }
    @Test
    void getAndSetDescriptionTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setDescriptionBean("description test");
        assertEquals("descriptionTest", videogameBean.getDescriptionBean());
    }
    @Test
    void getAndSetVideogameTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame(null, 0, 0, null);
        videogameBean.setVideogame(videogame);
        assertEquals(videogame, videogameBean.getVideogame());
    }
    @Test
    void getVideogameFromModelNameTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame("gameName", 0, 0, null);
        videogameBean.setVideogame(videogame);
        assertEquals("gameName", videogameBean.getName());
    }
    @Test
    void getVideogameFromModelCopiesTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame(null, 2, 0, null);
        videogameBean.setVideogame(videogame);
        assertEquals(2, videogameBean.getCopiesBean());
    }
    @Test
    void getVideogameFromModelPriceTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame(null, 0, 0, "descriptionTest");
        videogameBean.setVideogame(videogame);
        videogameBean.getVideogameFromModel();
        assertEquals("descriptionTest", videogameBean.getDescriptionBean());
    }
}
