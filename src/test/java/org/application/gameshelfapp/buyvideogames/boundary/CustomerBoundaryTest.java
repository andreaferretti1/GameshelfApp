package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.utils.ItemDAOCSVUtils;
import org.application.gameshelfapp.buyvideogames.dao.utils.ItemDAOJDBCUtils;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOCSVUtils;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOJDBCUtils;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBoundaryTest {

    @AfterEach
    void truncateTable(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            ItemDAOCSVUtils.truncateFile();
            SaleDAOCSVUtils.truncateFile();
        } else{
            ItemDAOJDBCUtils.truncateTable();
            SaleDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void getandSetSellingGamesCatalogueBeanTest(){
        CustomerBoundary boundary = new CustomerBoundary(null);
        boundary.setSellingGamesCatalogueBean(new SellingGamesCatalogueBean());
        assertNotNull(boundary.getSellingGamesCatalogueBean());
    }

    @Test
    void getUserBeanTest(){
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        assertNotNull(customerBoundary.getUserBean());
    }

    @Test
    void insertFiltersNoGamesFoundTest(){       //Database was empty
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        assertThrows(NoGameInCatalogueException.class, ()->customerBoundary.insertFilters("nameTest", "consoleTest", "categoryTest"));

    }

    @Test
    void inertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        String[][] records = {{"nameTest1", "consoleTest", "categoryTest", "descriptionTest1", "2", "11"}, {"nameTest2", "consoleTest", "categoryTest", "dscriptionTest", "3", "20"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters("nameTest1", "consoleTest", "categoryTest");
            List<VideogameBean> gamesBean = customerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean();
            assertEquals(1, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        String[][] records = {{"nameTest1", "consoleTest", "categoryTest", "descriptionTest1", "2", "11"}, {"nameTest2", "consoleTest", "categoryTest", "descriptionTest", "3", "20"}, {"nameTest3", "consoleTest1", "categoryTest1", "descriptionTest2", "5", "30"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters(null, "consoleTest", "categoryTest");
            List<VideogameBean> gamesBean = customerBoundary.getSellingGamesCatalogueBean().getSellingGamesBean();
            assertEquals(2, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("gameNameTest");
        videogameBean.setCopiesBean(2);
        videogameBean.setPriceBean(40);
        videogameBean.setPlatformBean("consoleTest");
        UserBean userBean = new UserBean();
        userBean.setEmail("fer.andrea35@gmail.com");
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        customerBoundary.setGameToBuy(videogameBean);
        try {
            customerBoundary.insertCredentialsAndPay("Name","card", "key", "Via Cambridge", "Roma", "Italy");
            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            assertEquals(1, (long) sales.size());
        } catch (PersistencyErrorException | RefundException | GameSoldOutException |
                 SyntaxErrorException | InvalidAddressException | NoGameInCatalogueException e) {
           fail();
        }
    }

    @Test
    void insertCredentialsAndPayNoGameInCatalogueExceptionTest(){       //database was empty
        CustomerBoundary boundary = new CustomerBoundary(new UserBean());
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("nameTest");
        videogameBean.setPlatformBean("platformTest");
        videogameBean.setCategoryBean("categoryTest");
        boundary.setGameToBuy(videogameBean);
        assertThrows(NoGameInCatalogueException.class, () -> boundary.insertCredentialsAndPay("testName", "test", "test", "via cmabridge", "Roma", "Italia"));
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        String[][] records = {{"gameNameTest1", "consoleTest", "categoryTest", "descriptionTest", "1", "20"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("gameNameTest");
        videogameBean.setCopiesBean(2);
        videogameBean.setPriceBean(20);
        UserBean userBean = new UserBean();
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        customerBoundary.setGameToBuy(videogameBean);
        assertThrows(GameSoldOutException.class, () -> customerBoundary.insertCredentialsAndPay("Name", "card", "key", "Via Cambridge", "Roma", "Italy"));
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){
        VideogameBean videogameBean = new VideogameBean();
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        customerBoundary.setGameToBuy(videogameBean);
        assertThrows(InvalidAddressException.class, () -> customerBoundary.insertCredentialsAndPay("Name","card", "key", "Viasbagliata", "Roma", "Italy"));
    }
}