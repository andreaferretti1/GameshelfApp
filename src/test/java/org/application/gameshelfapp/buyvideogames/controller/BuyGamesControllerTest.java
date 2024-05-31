package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;

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
            assertEquals(1, (long) videogamesFoundBean.getVideoamesFoundBean().size());
        } catch (PersistencyErrorException e){
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
            assertEquals(1, (long) videogamesFoundBean.getVideoamesFoundBean().size());
        } catch(PersistencyErrorException e){
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
        try{
            VideogamesFoundBean videogamesFoundBean = controller.searchVideogame(filtersBean);
            videogamesFoundBean.getInformationFromModel();
            assertEquals(0, (long) videogamesFoundBean.getVideoamesFoundBean().size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void sendMoneyTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try{
            BuyGamesController controller = new BuyGamesController();
            UserBean userBean = new UserBean();
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("Via Cambridge", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(1);
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setConsoleBean("consoleTest");
            filtersBean.setCategoryBean("categoryTest");
            controller.sendMoney(credentialsBean, videogameBean, userBean, filtersBean);
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
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("testAddress", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(1);
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setConsoleBean("consoleTest");
            filtersBean.setCategoryBean("categoryTest");
            assertThrows(InvalidAddressException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean, filtersBean));
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
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("testAddress", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(3);
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setConsoleBean("consoleTest");
            filtersBean.setCategoryBean("categoryTest");
            assertThrows(GameSoldOutException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean, filtersBean));
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
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setAddressBean("Via Cambridge", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(3);
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setConsoleBean("consoleTest");
            filtersBean.setCategoryBean("categoryTest");
            assertThrows(RefundException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean, filtersBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    
}