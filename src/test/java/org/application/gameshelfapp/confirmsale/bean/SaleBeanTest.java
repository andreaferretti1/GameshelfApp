package org.application.gameshelfapp.confirmsale.bean;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void getAndSetStateTest(){
        SaleBean saleBean = new SaleBean();
        saleBean.setStateBean(Sale.CONFIRMED);
        assertEquals(Sale.CONFIRMED, saleBean.getStateBean());
    }
    @Test
    void getCredentialsBeanTest(){
        SaleBean saleBean = new SaleBean();
        saleBean.setCredentialsBean(new CredentialsBean());
        assertNotNull(saleBean.getCredentialsBean());
    }
}