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
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        Sale sale = new Sale(videogame, null, null, null, null);
        assertEquals(videogame, sale.getVideogameSold());
    }
    @Test
    void setVideogameSoldTest(){
        Sale sale = new Sale(null, null, null, null, null);
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        sale.setVideogameSold(videogame);
        assertEquals(videogame, sale.getVideogameSold());
    }

    @Test
    void getAddressTest() {
        Sale sale = new Sale(null, null, "address", null, null);
        assertEquals("address", sale.getAddress());
    }

    @Test
    void setAddressTest() {
        Sale sale = new Sale(null, null, null, null, null);
        sale.setAddress("address");
        assertEquals("address", sale.getAddress());
    }

    @Test
    void getStateTest() {
        Sale sale = new Sale(null, null, null, "To confirm", null);
        assertEquals("To confirm", sale.getState());
    }

    @Test
    void setStateTest() {
        Sale sale = new Sale(null, null, null, null, null);
        sale.setState("Confirmed");
        assertEquals("Confirmed", sale.getState());
    }
    @Test
    void getNameTest() {
        Sale sale = new Sale(null, null, null, null, "name");
        assertEquals("name", sale.getName());
    }
    @Test
    void setNameTest() {
        Sale sale = new Sale(null, null, null, null, null);
        sale.setName("name");
        assertEquals("name", sale.getName());
    }

    @Test
    void getEmailTest() {
        Sale sale = new Sale(null, "email", null, null, null);
        assertEquals("email", sale.getEmail());
    }
    @Test
    void setEmailTest() {
        Sale sale = new Sale(null, null, null, null, null);
        sale.setEmail("email");
        assertEquals("email", sale.getEmail());
    }
}