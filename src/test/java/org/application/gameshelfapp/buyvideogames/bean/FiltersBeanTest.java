package org.application.gameshelfapp.buyvideogames.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FiltersBeanTest {

    @Test
    void getAndSetNameTest(){
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setNameBean("nameTest");
        assertEquals("nameTest", filtersBean.getNameBean());
    }

    @Test
    void setAndGetPlatformBean(){
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setConsoleBean("platformTest");
        assertEquals("platformTest", filtersBean.getConsoleBean());
    }

    @Test
    void setAndGetCategoryTest(){
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean("categoryTest");
        assertEquals("categoryTest", filtersBean.getCategoryBean());
    }
}