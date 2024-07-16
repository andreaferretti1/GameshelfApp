package org.application.gameshelfapp.buyvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.CredentialsBean;
import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyGamesControllerTest{
//tests written by Andrea Ferretti. Tests were executed using JDBC DAO. To run these tests, 'PERSISTENCE' key in resources/configuration/configuration.properties file was set to 'JDBC' value

    @BeforeEach
    void setFilters(){
        List<String> consoles = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        consoles.add("consoleTest");
        consoles.add("consoleTest1");
        categories.add("categoryTest");
        categories.add("categoryTest1");
        categories.add("TestCategory1");
        Filters.setConsoles(consoles);
        Filters.setCategories(categories);
    }

    @AfterEach
    void cleanUp(){
        try {
            String query1 = "TRUNCATE TABLE Sale;";
            String query2 = "TRUNCATE TABLE ObjectOnSale;";
            Connection connection = SingletonConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);
            statement.execute(query2);
        } catch(PersistencyErrorException | SQLException e){
            fail();
        }
    }

    @Test
    void searchVideogameTest(){     //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '20')
        try{
            Videogame videogame = new Videogame("nameTest", 2, 20, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            BuyGamesController controller = new BuyGamesController(new UserBean());
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setNameBean("nameTest");
            filtersBean.setCategoryBean("categoryTest");
            filtersBean.setConsoleBean("consoleTest");
            SellingGamesCatalogueBean sellingGamesCatalogueBean = controller.searchVideogame(filtersBean);
            assertEquals(1, (long) sellingGamesCatalogueBean.getSellingGamesBean().size());
        } catch (PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void searchVideogameWithoutNameTest(){      /*In the videogame table there was tuple ('nameTest1', 'consoleTest1', 'categoryTest1', 'descriptionTest1', '2', '20')
                                                ('nameTest2', 'consoleTest2', 'categoryTest2', 'descriptionTest2', '3', '40')*/
        try{
            Videogame videogame1 = new Videogame("nameTest1", 2, 20, "descriptionTest1", "consoleTest1", "categoryTest1");
            Videogame videogame2 = new Videogame("nameTest2", 3, 40, "descriptionTest2", "consoleTest2", "categoryTest2");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame1);
            itemDAO.addGameForSale(videogame2);

            BuyGamesController controller = new BuyGamesController(new UserBean());
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setConsoleBean("consoleTest1");
            filtersBean.setCategoryBean("categoryTest1");
            SellingGamesCatalogueBean sellingGamesCatalogueBean = controller.searchVideogame(filtersBean);
            assertEquals(1, (long) sellingGamesCatalogueBean.getSellingGamesBean().size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void searchVideogameNoResultsTest(){        //Database was empty
        try {
            BuyGamesController controller = new BuyGamesController(new UserBean());
            FiltersBean filtersBean = new FiltersBean();
            filtersBean.setNameBean("nameTest");
            filtersBean.setConsoleBean("consoleTest");
            filtersBean.setCategoryBean("categoryTest");
            assertThrows(NoGameInCatalogueException.class, () -> controller.searchVideogame(filtersBean));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void sendMoneyTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30'), sale table was empty
        try{
            Videogame videogame = new Videogame("nameTest", 2, 30, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);

            UserBean userBean = new UserBean();
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            userBean.setTypeOfUser("Customer");
            BuyGamesController controller = new BuyGamesController(userBean);
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setEmailBean("fer.andrea35@gmail.com");
            credentialsBean.setAddressBean("Via Cambridge", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(1);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            controller.sendMoney(credentialsBean, videogameBean);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            assertEquals(1, (long) saleDAO.getToConfirmSales().size());
        } catch (PersistencyErrorException | SyntaxErrorException | RefundException | GameSoldOutException |
                 InvalidAddressException | NoGameInCatalogueException | WrongUserTypeException | GmailException |
                 CheckFailedException e){
            fail();
        }
    }

    @Test
    void sendMoneyNoGameInCatalogueExceptionTest(){     //database was empty
        try {
            BuyGamesController controller = new BuyGamesController(new UserBean());
            VideogameBean gameBean = new VideogameBean();
            gameBean.setName("nameTest");
            gameBean.setPlatformBean("consoleTest");
            gameBean.setCategoryBean("categoryTest");
            assertThrows(NoGameInCatalogueException.class, () -> controller.sendMoney(new CredentialsBean(), gameBean));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void sendMoneyWrongAddressTest(){       //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try {
            Videogame videogame = new Videogame("nameTest", 2, 30, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);

            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            BuyGamesController controller = new BuyGamesController(userBean);
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setEmailBean("fer.andrea35@gmail.com");
            credentialsBean.setAddressBean("testAddress", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(1);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            assertThrows(InvalidAddressException.class, () -> controller.sendMoney(credentialsBean, videogameBean));
        } catch(SyntaxErrorException | WrongUserTypeException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void sendMoneyGameSoldOutTest(){        //In the Videogame table there was tuple ('nameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2','30')
        try {
            Videogame videogame = new Videogame("nameTest", 2, 30, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);

            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            userBean.setUsername("name");
            userBean.setEmail("fer.andrea35@gmail.com");
            BuyGamesController controller = new BuyGamesController(userBean);
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setNameBean("Name");
            credentialsBean.setPaymentKeyBean("key");
            credentialsBean.setTypeOfPaymentBean("payment");
            credentialsBean.setEmailBean("fer.andrea35@gmail.com");
            credentialsBean.setAddressBean("Via Cambridge", "Rome", "Italy");
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPriceBean(30);
            videogameBean.setCopiesBean(3);
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            assertThrows(GameSoldOutException.class, () -> controller.sendMoney(credentialsBean, videogameBean));
        } catch(SyntaxErrorException | WrongUserTypeException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getSaleTest(){      //In the Sale table there was tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
        try{
            Videogame videogame = new Videogame("gameNameTest1", 3, 10, null, "consoleTest", null);
            Credentials credentials = new Credentials("nameTest1", "addressTest", "emailTest");
            Sale sale = new Sale(videogame, credentials, Sale.TO_CONFIRM);
            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            saleDAO.saveSale(sale);

            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            BuyGamesController controller = new BuyGamesController(new UserBean());
            List<SaleBean> sales = controller.getSales(userBean);
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException | WrongUserTypeException e){
            fail();
        }
    }

    //run this test alone, since Gmail allows system to send e-mails some seconds after the previous one was sent, because Gmail is used in the test phase.
    @Test
    void confirmDeliveryTest(){     //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'fer.andrea35@gmail.com')
        try{
            Videogame videogame = new Videogame("gameNameTest1", 3, 10, null, "consoleTest", null);
            Credentials credentials = new Credentials("nameTest1", "addressTest", "fer.andrea35@gmail.com");
            Sale sale = new Sale(videogame, credentials, Sale.TO_CONFIRM);
            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            saleDAO.saveSale(sale);

            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            BuyGamesController controller = new BuyGamesController(userBean);
            controller.confirmDelivery(1, userBean);

            assertEquals(1, (long) saleDAO.getConfirmedSales().size());
        } catch(PersistencyErrorException | ConfirmDeliveryException | WrongSaleException | GmailException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getConsolesTest(){     //look at BeforeEach method
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            BuyGamesController buyGamesController = new BuyGamesController(userBean);
            assertEquals(2, (long) buyGamesController.getConsoles().size());
        } catch (WrongUserTypeException e) {
            fail();
        }
    }
    @Test
    void getCategoriesTest(){     //look at BeforeEach method
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            BuyGamesController buyGamesController = new BuyGamesController(userBean);
            assertEquals(3, (long) buyGamesController.getCategories().size());
        } catch (WrongUserTypeException e) {
            fail();
        }
    }
}