package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {
    @Test
    void setAndGetIdTest(){
        Sale sale = new Sale(null, null, null, null, null);
        sale.setId(1);
        assertEquals(1, sale.getId());
    }
    @Test
    void getVideogameSoldTest(){
        Videogame videogame = new Videogame(null, 0, 0, null);
        Sale sale = new Sale(videogame, null, null, null, null);
        assertEquals(videogame, sale.getVideogameSold());
    }
    @Test
    void setVideogameSoldTest(){
        Sale sale = new Sale(null, null, null, null, null);
        Videogame videogame = new Videogame(null, 0, 0, null);
        sale.setVideogameSold(videogame);
        assertEquals(videogame, sale.getVideogameSold());
    }
    @Test

}