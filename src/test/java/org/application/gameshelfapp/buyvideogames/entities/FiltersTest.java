package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {
    @BeforeEach
    void setFilters(){
        Filters.consoles = new ArrayList<>();
        Filters.categories = new ArrayList<>();
        Filters.consoles.add("consoleTest");
        Filters.categories.add("categoryTest");
    }

    @Test
    void getNameTest(){
        try {
            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            assertEquals("nameTest", filters.getName());
        } catch (CheckFailedException e){
            fail();
        }
    }

    @Test
    void getConsoleTest(){
       try {
           Filters filters = new Filters(null, "consoleTest", "categoryTest");
           assertEquals("consoleTest", filters.getConsole());
       } catch(CheckFailedException e){
           fail();
       }
    }

    @Test
    void getCategoryTest(){
        try {
            Filters filters = new Filters(null, "consoleTest", "categoryTest");
            assertEquals("categoryTest", filters.getCategory());
        } catch (CheckFailedException e){
            fail();
        }
    }

    @Test
    void setNameTest(){
        try{
            Filters filters = new Filters(null, "consoleTest", "categoryTest");
            filters.setName("name");
            assertEquals("name", filters.getName());
        } catch (CheckFailedException e){
            fail();
        }
    }
}
