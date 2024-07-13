package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {
    @BeforeEach
    void setFilters(){
        List<String> consoles = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        consoles.add("consoleTest");
        categories.add("categoryTest");
        Filters.setConsoles(consoles);
        Filters.setCategories(categories);
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
