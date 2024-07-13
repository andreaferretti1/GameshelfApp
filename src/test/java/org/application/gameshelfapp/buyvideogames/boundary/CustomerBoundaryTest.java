package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBoundaryTest {
//test written by Andrea Ferretti

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
            userBean.setTypeOfUser("Customer");
            CustomerBoundary boundary = new CustomerBoundary(userBean);
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("nameTest");
            videogameBean.setPlatformBean("consoleTest");
            videogameBean.setCategoryBean("categoryTest");
            boundary.setGameToBuy(videogameBean);
            assertThrows(NoGameInCatalogueException.class, () -> boundary.insertCredentialsAndPay("testName", "test", "test", "via cmabridge", "Roma", "Italia"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        try {
            VideogameBean videogameBean = new VideogameBean();
            videogameBean.setName("gameNameTest");
            videogameBean.setCopiesBean(2);
            videogameBean.setPriceBean(20);
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            customerBoundary.setGameToBuy(videogameBean);
            assertThrows(GameSoldOutException.class, () -> customerBoundary.insertCredentialsAndPay("Name", "card", "key", "Via Cambridge", "Roma", "Italy"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){
        try {
            VideogameBean videogameBean = new VideogameBean();
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
            customerBoundary.setGameToBuy(videogameBean);
            assertThrows(InvalidAddressException.class, () -> customerBoundary.insertCredentialsAndPay("Name", "card", "key", "Viasbagliata", "Roma", "Italy"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }
}