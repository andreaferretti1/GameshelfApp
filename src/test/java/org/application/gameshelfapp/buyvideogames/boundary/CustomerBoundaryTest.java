package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.dao.SaleDAO;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBoundaryTest {
    @Test
    void constructorTest(){
        UserBean userBean = new UserBean();
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        assertSame(userBean, customerBoundary.getUserBean());
        assertNotNull(customerBoundary.getBuyGamesController());
    }

    @Test
    void setAndGetVideogameFoundBeanTest(){
        CustomerBoundary customerBoundary = new CustomerBoundary(null);
        customerBoundary.setVideogamesFoundBean(new VideogamesFoundBean());
        assertNotNull(customerBoundary.getVideogamesFoundBean());
    }

    @Test
    void setAndGetGameToBuyTest(){
        CustomerBoundary customerBoundary = new CustomerBoundary(null);
        VideogameBean videogameBean = new VideogameBean();
        customerBoundary.setGameToBuy(videogameBean);
        assertEquals(videogameBean, customerBoundary.getGameToBuy());
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
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters("nameTest1", "consoleTest", "categoryTest");
            customerBoundary.getVideogamesFoundBean().getInformationFromModel();
            List<VideogameBean> gamesBean = customerBoundary.getVideogamesFoundBean().getVideogamesFoundBean();
            assertNotNull(gamesBean);
            VideogameBean videogameBean = gamesBean.getFirst();
            assertEquals("nameTest1", videogameBean.getName());
            assertEquals(2, videogameBean.getCopiesBean());
            assertEquals(11, videogameBean.getPriceBean());
            assertEquals("descriptionTest1", videogameBean.getDescriptionBean());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters(null, "consoleTest", "categoryTest");
            customerBoundary.getVideogamesFoundBean().getInformationFromModel();
            List<VideogameBean> gamesBean = customerBoundary.getVideogamesFoundBean().getVideogamesFoundBean();
            assertEquals(2, (long) gamesBean.size());

            VideogameBean videogameBean1 = gamesBean.getFirst();
            assertEquals("nameTest1", videogameBean1.getName());
            assertEquals(2, videogameBean1.getCopiesBean());
            assertEquals(11, videogameBean1.getPriceBean());
            assertEquals("descriptionTest1", videogameBean1.getDescriptionBean());

            VideogameBean videogameBean2 = gamesBean.getLast();
            assertEquals("nameTest2", videogameBean2.getName());
            assertEquals(3, videogameBean2.getCopiesBean());
            assertEquals(20, videogameBean2.getPriceBean());
            assertEquals("descriptionTest2", videogameBean2.getDescriptionBean());
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
            assertNotNull(sales);

            Sale sale = sales.getFirst();
            Videogame videogame = sale.getVideogameSold();
            assertEquals("Name", sale.getName());
            assertEquals("gameNameTest", videogame.getName());
            assertEquals(2, videogame.getCopies());
            assertEquals(40, videogame.getPrice());
            assertEquals("consoleTest", videogame.getPlatform());
            assertEquals("fer.andrea35@gmail.com", sale.getEmail());
            assertEquals("Via Cambridge, Roma, Italy", sale.getAddress());
            assertEquals(Sale.TO_CONFIRM, sale.getState());
        } catch (PersistencyErrorException | RefundException | GameSoldOutException |
                 SyntaxErrorException | InvalidAddressException e) {
           fail();
        }
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
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

    @Test
    void insertCredentialsAndPayGmailRefundException(){     //In the game table there was tuple ('gameNameTest', '2', '10', 'descriptionTest', 'consoleTest', 'categoryTest')
        UserBean userBean = new UserBean();
        userBean.setEmail("test");
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("gameNameTest");
        videogameBean.setCopiesBean(1);
        videogameBean.setPriceBean(10);
        videogameBean.setDescriptionBean("descriptionTest");
        customerBoundary.setGameToBuy(videogameBean);
        assertThrows(RefundException.class, () -> customerBoundary.insertCredentialsAndPay("Name", "card", "key", "Via Cambridge", "Rome", "Italy"));
    }
}