package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
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

class CustomerBoundaryTest {
//tests written by Andrea Ferretti. Tests were executed using JDBC DAO. To run these tests, 'PERSISTENCE' key in resources/configuration/configuration.properties file was set to 'JDBC' value

    @BeforeEach
    void setFilters(){
        List<String> consoles = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        consoles.add("consoleTest");
        categories.add("categoryTest");
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
    void getAndSetSellingGamesCatalogueBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary boundary = new CustomerBoundary(userBean);
            boundary.setSellingGamesCatalogueBean(new SellingGamesCatalogueBean());
            assertNotNull(boundary.getSellingGamesCatalogueBean());
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getUserBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            assertNotNull(customerBoundary.getUserBean());
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertFiltersNoGamesFoundTest(){       //Database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            assertThrows(NoGameInCatalogueException.class, () -> customerBoundary.insertFilters("nameTest", "consoleTest", "categoryTest"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void inertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        try{
            Videogame videogame1 = new Videogame("nameTest1", 2, 11, "descriptionTest1", "consoleTest", "categoryTest");
            Videogame videogame2 = new Videogame("nameTest2", 3, 20, "descriptinTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame1);
            itemDAO.addGameForSale(videogame2);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);

            customerBoundary.insertFilters("nameTest1", "consoleTest", "categoryTest");
            List<VideogameBean> gamesBean = customerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean();
            assertEquals(1, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        try{
            Videogame videogame1 = new Videogame("nameTest1", 2, 11, "descriptionTest1", "consoleTest", "categoryTest");
            Videogame videogame2 = new Videogame("nameTest2", 3, 20, "descriptinTest", "consoleTest", "categoryTest");
            Videogame videogame3 = new Videogame("nameTest3", 5, 30, "descriptionTest2", "consoleTest1", "categoryTest1");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame1);
            itemDAO.addGameForSale(videogame2);
            itemDAO.addGameForSale(videogame3);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);

            customerBoundary.insertFilters(null, "consoleTest", "categoryTest");
            List<VideogameBean> gamesBean = customerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean();
            assertEquals(2, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayNoGameInCatalogueExceptionTest(){       //database was empty
        try{
            UserBean userBean = new UserBean();
            userBean.setEmail("fer.andrea35@gmail.com");
            userBean.setTypeOfUser("Customer");
            CustomerBoundary boundary = new CustomerBoundary(userBean);
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            boundary.setGameToBuy(videogameBean);
            assertThrows(NoGameInCatalogueException.class, () -> boundary.insertCredentialsAndPay("testName", "test", "test", "via cambridge", "Roma", "Italia"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        try {
            Videogame videogame = new Videogame("gameNameTest", 1, 20, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("gameNameTest");
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            videogameBean.setCopiesBean(2);
            videogameBean.setPriceBean(20);
            UserBean userBean = new UserBean();
            userBean.setEmail("fer.andrea35@gmail.com");
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            customerBoundary.setGameToBuy(videogameBean);
            assertThrows(GameSoldOutException.class, () -> customerBoundary.insertCredentialsAndPay("Name", "card", "key", "Via Cambridge", "Roma", "Italy"));
        } catch(WrongUserTypeException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){       //In the VideogameTable there was game ('gameNameTest', '3', '20', 'descriptionTest', 'consoleTest','categoryTest')
        try {
            Videogame videogame = new Videogame("gameNameTest", 3, 20, "descriptionTest", "consoleTest", "categoryTest");
            ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
            itemDAO.addGameForSale(videogame);
            UserBean userBean = new UserBean();
            userBean.setEmail("fer.andrea35@gmail.com");
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("gameNameTest");
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            videogameBean.setCopiesBean(2);
            videogameBean.setPriceBean(20);
            customerBoundary.setGameToBuy(videogameBean);
            assertThrows(InvalidAddressException.class, () -> customerBoundary.insertCredentialsAndPay("Name", "card", "key", "Viasbagliata", "Roma", "Italy"));
        } catch(WrongUserTypeException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getCategoriestest(){       //look at BeforeEach method
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            assertEquals(1, (long) customerBoundary.getCategoriesFilters().size());
        } catch(WrongUserTypeException e){
            fail();
        }
    }
    @Test
    void getConsolestest(){       //look at BeforeEach method
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            assertEquals(1, (long) customerBoundary.getConsoleFilters().size());
        } catch(WrongUserTypeException e){
            fail();
        }
    }
}