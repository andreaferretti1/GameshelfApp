package org.application.gameshelfapp.confirmsale.entities;

import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SaleTest {
    @Test
    void getIdTest(){
        Sale sale = new Sale(1, null, null, null);
        assertEquals(1, sale.getId());
    }

    @Test
    void setIdTest(){
        Sale sale = new Sale(1, null, null, null);
        sale.setId(1);
        assertEquals(1, sale.getId());
    }
    @Test
    void getVideogameSoldTest(){
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        Sale sale = new Sale(0, videogame, null, null);
        assertEquals(videogame, sale.getVideogameSold());
    }
    @Test
    void setVideogameSoldTest(){
        Sale sale = new Sale(0, null, null, null);
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        sale.setVideogameSold(videogame);
        assertEquals(videogame, sale.getVideogameSold());
    }
    @Test
    void setGetCredentialsTest(){
        Credentials credentials = new Credentials(null, null, null);
        Sale sale = new Sale(0, null, credentials, null);
        assertNotNull(sale.getCredentials());
    }

}