package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FiltersTest {

    @Test
    void getNameTest(){
        Filters filters = new Filters("nameTest", null, null);
        assertEquals("nameTest", filters.getName());
    }

    @Test
    void getConsoleTest(){
        Filters filters = new Filters(null, "consoleTest", null);
        assertEquals("consoleTest", filters.getConsole());
    }

    @Test
    void getCategoryTest(){
        Filters filters = new Filters(null, null, "categoryTest");
        assertEquals("categoryTest", filters.getCategory());
    }

    @Test
    void setNameTest(){
        Filters filters = new Filters(null, null, null);
        filters.setName("name");
        assertEquals("name", filters.getName());
    }

    @Test
    void setConsoleTest(){
        Filters filters = new Filters(null, null, null);
        filters.setConsole("console");
        assertEquals("console", filters.getConsole());
    }

    @Test
    void setCategoryTest(){
        Filters filters = new Filters(null, null, null);
        filters.setCategory("category");
        assertEquals("category", filters.getCategory());
    }
}
