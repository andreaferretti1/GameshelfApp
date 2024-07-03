package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.*;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyGamesControllerTest {

    @Test
    void searchVideogameTest(){     //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '20')
        BuyGamesController controller = new BuyGamesController();
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setNameBean("nameTest");
        filtersBean.setCategoryBean("categoryTest");
        filtersBean.setConsoleBean("consoleTest");
        try{
            VideogamesFoundBean videogamesFoundBean = controller.searchVideogame(filtersBean);
            videogamesFoundBean.getInformationFromModel();
            assertEquals(1, (long) videogamesFoundBean.getVideogamesFoundBean().size());
        } catch (PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void searchVideogameWithoutNameTest(){      /*In the videogame table there was tuple ('nameTest1', 'consoleTest1', 'categoryTest1', 'descriptionTest1', '2', '20')
                                                ('nameTest2', 'consoleTest2', 'categoryTest2', 'descriptionTest2', '3', '40')*/
        BuyGamesController controller = new BuyGamesController();
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setConsoleBean("consoleTest1");
        filtersBean.setConsoleBean("categoryTest1");
        try{
            VideogamesFoundBean videogamesFoundBean = controller.searchVideogame(filtersBean);
            videogamesFoundBean.getInformationFromModel();
            assertEquals(1, (long) videogamesFoundBean.getVideogamesFoundBean().size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void searchVideogameNoResultsTest(){        //Database was empty

        BuyGamesController controller = new BuyGamesController();
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setNameBean("nameTest");
        filtersBean.setConsoleBean("consoleTest");
        filtersBean.setCategoryBean("categoryTest");
        assertThrows(NoGameInCatalogueException.class, ()->controller.searchVideogame(filtersBean));
    }

    @Test
    void sendMoneyTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try{
            BuyGamesController controller = new BuyGamesController();
            UserBean userBean = new UserBean();
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("Via Cambridge", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(1);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            controller.sendMoney(credentialsBean, videogameBean, userBean);
        } catch (PersistencyErrorException | SyntaxErrorException | RefundException | GameSoldOutException | InvalidAddressException e){
            fail();
        }
    }

    @Test
    void sendMoneyWrongAddressTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try {
            BuyGamesController controller = new BuyGamesController();
            UserBean userBean = new UserBean();
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("testAddress", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(1);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            assertThrows(InvalidAddressException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void sendMoneyGameSoldOutTest(){        //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try {
            BuyGamesController controller = new BuyGamesController();
            UserBean userBean = new UserBean();
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("testAddress", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(3);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            assertThrows(GameSoldOutException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void sendMoneyGmailRefundExceptionTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try {
            BuyGamesController controller = new BuyGamesController();
            UserBean userBean = new UserBean();
            userBean.setUsername("name");
            userBean.setEmail("emailTest");
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("Via Cambridge", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(3);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            assertThrows(RefundException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void getSalesTest(){        //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
        BuyGamesController controller = new BuyGamesController();
        try {
            List<SaleBean> sales = controller.getSales();
            assertEquals(1, (long) sales.size());
        }  catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getSalesConfirmedSalesTest(){      //In the Sale table there are tuples ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest'), ('2', 'nameTest2', 'gameNameTest2', '1', '15', 'consoleTest2', 'Confirmed', 'addressTest', 'emailTest')
        BuyGamesController controller = new BuyGamesController();
        try{
            List<SaleBean> sales = controller.getSales();
            assertEquals(2, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryTest(){     //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'fer.andrea35@gmail.com')
        BuyGamesController controller = new BuyGamesController();
        try{
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("gameNameTest1");
            videogameBean.setCopiesBean(3);
            videogameBean.setPriceBean(10);
            videogameBean.setPlatformBean("consoleTest");
            SaleBean saleBean = new SaleBean();
            saleBean.setIdBean(1);
            saleBean.setNameBean("nameTest1");
            saleBean.setGameSoldBean(videogameBean);
            saleBean.setStateBean("To confirm");
            saleBean.setAddressBean("addressTest");
            saleBean.setEmailBean("fer.andrea35@gmail.com");
            controller.confirmDelivery(saleBean);
        } catch(Exception e){
            fail();
        }
    }

    @Test
    void confirmDeliveryGmailEcxeptionTest(){       //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
            BuyGamesController controller = new BuyGamesController();
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("gameNameTest1");
        videogameBean.setCopiesBean(3);
        videogameBean.setPriceBean(10);
        videogameBean.setPlatformBean("consoleTest");
        SaleBean saleBean = new SaleBean();
        saleBean.setIdBean(1);
        saleBean.setNameBean("nameTest1");
        saleBean.setGameSoldBean(videogameBean);
        saleBean.setStateBean("To confirm");
        saleBean.setAddressBean("addressTest");
        saleBean.setEmailBean("emailTest");
        assertThrows(GmailException.class, () -> controller.confirmDelivery(saleBean));
    }
}