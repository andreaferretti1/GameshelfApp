package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.utils.ItemDAOCSVUtils;
import org.application.gameshelfapp.buyvideogames.dao.utils.ItemDAOJDBCUtils;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOCSVUtils;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOJDBCUtils;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyGamesControllerTest{

    @AfterEach
    void truncateFile(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            ItemDAOCSVUtils.truncateFile();
            SaleDAOCSVUtils.truncateFile();
        } else{
            ItemDAOJDBCUtils.truncateTable();
            SaleDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void searchVideogameTest(){     //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '20')
        String[][] records = {{"nameTest", "consoleTest", "categoryTest", "descriptionTest", "2", "20"}};
        if (GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        BuyGamesController controller = new BuyGamesController();
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setNameBean("nameTest");
        filtersBean.setCategoryBean("categoryTest");
        filtersBean.setConsoleBean("consoleTest");
        try{
            SellingGamesCatalogueBean sellingGamesCatalogueBean = controller.searchVideogame(filtersBean);
            assertEquals(1, (long) sellingGamesCatalogueBean.getSellingGamesBean().size());
        } catch (PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void searchVideogameWithoutNameTest(){      /*In the videogame table there was tuple ('nameTest1', 'consoleTest1', 'categoryTest1', 'descriptionTest1', '2', '20')
                                                ('nameTest2', 'consoleTest2', 'categoryTest2', 'descriptionTest2', '3', '40')*/
        String[][] records = {{"nameTest1", "consoleTest1", "categoryTest1", "descriptionTest1", "2", "20"}, {"nameTest2", "consoleTest2", "categoryTest2", "descriptionTest2", "3", "40"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        BuyGamesController controller = new BuyGamesController();
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setConsoleBean("consoleTest1");
        filtersBean.setCategoryBean("categoryTest1");
        try{
            SellingGamesCatalogueBean sellingGamesCatalogueBean = controller.searchVideogame(filtersBean);
            assertEquals(1, (long) sellingGamesCatalogueBean.getSellingGamesBean().size());
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
    void sendMoneyTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30'), sale table was empty
        String[][] records = {{"nameTest", "consoleTest", "categoryTest", "descriptionTest", "2", "30"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
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

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            assertEquals(1, (long) saleDAO.getToConfirmSales().size());
        } catch (PersistencyErrorException | SyntaxErrorException | RefundException | GameSoldOutException | InvalidAddressException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void sendMoneyNoGameInCatalogueExceptionTest(){     //database was empty
        BuyGamesController controller = new BuyGamesController();
        VideogameBean gameBean = new VideogameBean();
        gameBean.setName("nameTest");
        gameBean.setPlatformBean("platformTest");
        gameBean.setCategoryBean("categoryTest");
        assertThrows(NoGameInCatalogueException.class, () -> controller.sendMoney(new CredentialsBean(), gameBean, new UserBean()));
    }

    @Test
    void sendMoneyWrongAddressTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        String[][] records = {{"nameTest", "consoleTest", "categoryTest", "descriptionTest", "2", "30"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
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
        String[][] records = {{"nameTest", "consoleTest", "categoryTest", "descriptionTest", "2", "30"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        try {
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
            videogameBean.setCopiesBean(3);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            assertThrows(GameSoldOutException.class, () -> controller.sendMoney(credentialsBean, videogameBean, userBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void getSalesDifferentSalesTest(){      //In the Sale table there are tuples ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest'), ('2', 'nameTest2', 'gameNameTest2', '1', '15', 'consoleTest2', 'Confirmed', 'addressTest', 'emailTest')
        String[][] records = {{"1", "nameTest1", "gameNameTest1", "3", "10", "consoleTest", "To confirm", "addressTest", "emailTest"}, {"2", "nameTest2", "gameNameTest2", "1", "15", "consoleTest2", "Confirmed", "addressTest", "emailTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        BuyGamesController controller = new BuyGamesController();
        try{
            List<SaleBean> sales = controller.getSales();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryTest(){     //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'fer.andrea35@gmail.com')
        String[][] records = {{"1", "nameTest1", "gameNameTest1", "3", "10", "consoleTest", "To confirm", "addressTest", "fer.andrea35@gmail.com"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        BuyGamesController controller = new BuyGamesController();
        try{
            controller.confirmDelivery(1);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            assertEquals(1, (long) saleDAO.getConfirmedSales().size());
        } catch(PersistencyErrorException | ConfirmDeliveryException | WrongSaleException | GmailException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryWrongSaleExceptionTest(){       //database was empty
        BuyGamesController controller = new BuyGamesController();
        assertThrows(WrongSaleException.class, () -> controller.confirmDelivery(2));
    }

    @Test
    void confirmedDeliveryWrongStateExceptionTest(){        //In the database there was tuple ('1', 'testName', 'gameName', '2', '22', 'platformTest', 'Comfirmed', 'addressTest', 'emailTest')
        String[][] records = {{"1", "testName", "gameName", "2", "22", "platformTest", "Confirmed", "addressTest", "emailTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        BuyGamesController controller = new BuyGamesController();
        assertThrows(WrongSaleException.class, () -> controller.confirmDelivery(1));
    }
}