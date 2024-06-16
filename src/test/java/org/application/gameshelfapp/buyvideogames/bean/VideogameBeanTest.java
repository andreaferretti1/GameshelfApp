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
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        videogameBean.setVideogame(videogame);
        assertEquals(videogame, videogameBean.getVideogame());
    }

    @Test
    void getAndSetPlatformTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setPlatformBean("platformTest");
        assertEquals("platformTest", videogameBean.getCategoryBean());
    }

    @Test
    void getAndSetCategoryTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setCategoryBean("categoryTest");
        assertEquals("categoryTest", videogameBean.getCategoryBean());
    }
    @Test
    void getVideogameFromModelNameTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame("gameName", 0, 0, null, null, null);
        videogameBean.setVideogame(videogame);
        assertEquals("gameName", videogameBean.getName());
    }
    @Test
    void getVideogameFromModelCopiesTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame(null, 2, 0, null, null, null);
        videogameBean.setVideogame(videogame);
        assertEquals(2, videogameBean.getCopiesBean());
    }

    @Test
    void getVideogameFromModelPriceTest(){
        Videogame videogame = new Videogame(null, 0, 1, null, null, null);
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setVideogame(videogame);
        videogameBean.getVideogameFromModel();
        assertEquals(1, videogameBean.getPriceBean());
    }
    @Test
    void getVideogameFromModelDescriptionTest(){
        VideogameBean videogameBean = new VideogameBean();
        Videogame videogame = new Videogame(null, 0, 0, "descriptionTest", null, null);
        videogameBean.setVideogame(videogame);
        videogameBean.getVideogameFromModel();
        assertEquals("descriptionTest", videogameBean.getDescriptionBean());
    }

    @Test
    void getVideogameFromModelPlatformTest(){
        Videogame videogame = new Videogame(null, 0, 0, null, "platformTest", null);
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setVideogame(videogame);
        videogameBean.getVideogameFromModel();
        assertEquals("categoryTest", videogameBean.getPlatformBean());
    }

    @Test
    void getVideogameFromModelCategoryTest(){
        Videogame videogame = new Videogame(null, 0, 0, null, null, "categoryTest");
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setVideogame(videogame);
        videogameBean.getVideogameFromModel();
        assertEquals("categoryTest", videogameBean.getCategoryBean());
    }
}
