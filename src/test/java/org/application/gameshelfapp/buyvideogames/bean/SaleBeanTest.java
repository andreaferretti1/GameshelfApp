package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleBeanTest {

    @Test
    void getAndSetIdBeanTest() {
        SaleBean saleBean = new SaleBean();
        saleBean.setIdBean(50);
        assertEquals(50, saleBean.getIdBean());
    }
    @Test
    void getAndSetGameSoldBeanTest(){
        SaleBean saleBean = new SaleBean();
        VideogameBean videogameBean = new VideogameBean();
        saleBean.setGameSoldBean(videogameBean);
        assertEquals(videogameBean, saleBean.getGameSoldBean());
    }
    @Test
    void getAndSetEmailBeanTest(){
        SaleBean saleBean = new SaleBean();
        saleBean.setEmailBean("emailTest");
        assertEquals("enailTest", saleBean.getEmailBean());
    }
    @Test
    void getAndSetAddressBeanTest(){
        SaleBean saleBean = new SaleBean();
        saleBean.setAddressBean("addressTest");
        assertEquals("addressTest", saleBean.getAddressBean());
    }
    @Test
    void getAndSetNameTest(){
        SaleBean saleBean = new SaleBean();
        saleBean.setNameBean("name");
        assertEquals("name", saleBean.getNameBean());
    }
    @Test
    void getAndSetStateTest(){
        SaleBean saleBean = new SaleBean();
        saleBean.setStateBean(Sale.CONFIRMED);
        assertEquals(Sale.CONFIRMED, saleBean.getStateBean());
    }
    @Test
    void getAndSetSaleTest(){
        SaleBean saleBean = new SaleBean();
        Sale sale = new Sale(null, null, null, null, null);
        saleBean.setSale(sale);
        assertEquals(sale,saleBean.getSale());
    }
    @Test
    void getInformationFromModelTestVideogameTest(){
        SaleBean saleBean = new SaleBean();
        Videogame videogame = new Videogame("gameName", 1, 10, "description", null, null);
        Sale sale = new Sale(videogame, null, null, null, null);
        saleBean.setSale(sale);
        saleBean.getInformationFromModel();
        assertEquals("gameName", saleBean.getGameSoldBean().getName());
        assertEquals(1, saleBean.getGameSoldBean().getCopiesBean());
        assertEquals(10, saleBean.getGameSoldBean().getPriceBean());
        assertEquals("description", saleBean.getGameSoldBean().getDescriptionBean());
    }
    @Test
    void getInformationModelTestEmailTest(){
        SaleBean saleBean = new SaleBean();
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        Sale sale = new Sale(videogame, "emailTest", null, null, null);
        saleBean.setSale(sale);
        saleBean.getInformationFromModel();
        assertEquals("emailTest", saleBean.getEmailBean());
    }
    @Test
    void getInformationFromModelAddressTest(){
        SaleBean saleBean = new SaleBean();
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        Sale sale = new Sale(videogame, null, "addressTest", null, null);
        saleBean.setSale(sale);
        saleBean.getInformationFromModel();
        assertEquals("addressTest", saleBean.getAddressBean());
    }
    @Test
    void getInformationFromModelStateTest(){
        SaleBean saleBean = new SaleBean();
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        Sale sale = new Sale(videogame, null, null, Sale.CONFIRMED, null);
        saleBean.setSale(sale);
        saleBean.getInformationFromModel();
        assertEquals(Sale.CONFIRMED, saleBean.getStateBean());
    }
    @Test
    void getInformationFromModelPlatformTest(){
        SaleBean saleBean = new SaleBean();
        Videogame videogame = new Videogame(null, 0, 0, null, null, null);
        Sale sale = new Sale(videogame, null, null, null, "platformTest");
        saleBean.setSale(sale);
        saleBean.getInformationFromModel();
        assertEquals("platformTest", saleBean.getNameBean());
    }
}