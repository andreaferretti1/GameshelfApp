package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideogameTest {

    @Test
    void getNameTest(){
        Videogame videogame = new Videogame("nameTest", 0, 0, null);
        assertEquals("nameTest", videogame.getName());
    }
    @Test
    void setNameTest(){
        Videogame videogame = new Videogame(null, 0, 0, null);
        videogame.setName("nameTest");
        assertEquals("nameTest", videogame.getName());
    }
    @Test
    void getCopiesTest(){
        Videogame videogame = new Videogame(null, 2, 0, null);
        assertEquals(2, videogame.getCopies());
    }
    @Test
    void setCopiesTest(){
        Videogame videogame = new Videogame(null, 0, 0, null);
        videogame.setCopies(2);
        assertEquals(2, videogame.getCopies());
    }
    @Test
    void getPriceTest(){
        Videogame videogame = new Videogame(null, 0, 13.1f, null);
        assertEquals(13.1f, videogame.getPrice());
    }
    @Test
    void setPriceTest(){
        Videogame videogame = new Videogame(null, 0, 0, null);
        videogame.setPrice(13.1f);
        assertEquals(13.1f, videogame.getPrice());
    }
    @Test
    void getDescriptionTest(){
        Videogame videogame = new Videogame(null, 0, 0, "descriptionTest");
        assertEquals("descriptionTest", videogame.getDescription());
    }
    @Test
    void setDescriptionTest(){
        Videogame videogame = new Videogame(null, 0, 0, null);
        videogame.setDescription("descriptionTest");
        assertEquals("descriptionTest", videogame.getDescription());
    }
}
