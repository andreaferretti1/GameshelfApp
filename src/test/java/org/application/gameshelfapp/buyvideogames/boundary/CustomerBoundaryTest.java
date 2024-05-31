package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.buyvideogames.entities.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.entities.SaleDAO;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.FiltersException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
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
    void setAndGetFiltersBeanTest(){
        CustomerBoundary customerBoundary = new CustomerBoundary(null);
        customerBoundary.setFiltersBean(new FiltersBean());
        assertNotNull(customerBoundary.getFiltersBean());
    }

    @Test
    void setAndGetVideogameFoundBeanTest(){
        CustomerBoundary customerBoundary = new CustomerBoundary(null);
        customerBoundary.setVideogamesFoundBean(new VideogamesFoundBean());
        assertNotNull(customerBoundary.getVideogamesFoundBean());
    }

    @Test
    void getUserBeanTest(){
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        assertNotNull(customerBoundary.getUserBean());
    }

    @Test
    void insertFiltersNoGamesFoundTest(){       //Database was empty
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters("nameTest", "consoleTest", "categoryTest");
            assertNotNull(customerBoundary.getVideogamesFoundBean());
        } catch(PersistencyErrorException | FiltersException e){
            fail();
        }
    }

    @Test
    void inertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters("nameTest1", "consoleTest", "categoryTest");
            customerBoundary.getVideogamesFoundBean().getInformationFromModel();
            List<VideogameBean> gamesBean = customerBoundary.getVideogamesFoundBean().getVideoamesFoundBean();
            assertNotNull(gamesBean);
            VideogameBean videogameBean = gamesBean.getFirst();
            assertEquals("nameTest1", videogameBean.getName());
            assertEquals(2, videogameBean.getCopiesBean());
            assertEquals(11, videogameBean.getPriceBean());
            assertEquals("descriptionTest1", videogameBean.getDescriptionBean());
        } catch(PersistencyErrorException | FiltersException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        try{
            customerBoundary.insertFilters(null, "consoleTest", "categoryTest");
            customerBoundary.getVideogamesFoundBean().getInformationFromModel();
            List<VideogameBean> gamesBean = customerBoundary.getVideogamesFoundBean().getVideoamesFoundBean();
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
        } catch(PersistencyErrorException | FiltersException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayTest(){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("nameTest");
        videogameBean.setCopiesBean(2);
        videogameBean.setPriceBean(40);
        videogameBean.setDescriptionBean("descriptionTest");
        UserBean userBean = new UserBean();
        userBean.setEmail("fer.andrea35@gmail.com");
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        try {
            customerBoundary.insertCredentialsAndPay("card", "key", "Via Cambridge", "Roma", "Italy", videogameBean);
            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> sales = saleDAO.getSales();
            assertNotNull(sales);

            Sale sale = sales.getFirst();
            Videogame videogame = sale.getVideogameSold();
            assertEquals("nameTest", videogame.getName());
            assertEquals(2, videogame.getCopies());
            assertEquals(40, videogame.getPrice());
            assertEquals("fer.andrea35@gmail.com", sale.getEmail());
            assertEquals("Via Cambridge, Roma, Italy", sale.getAddress());
            assertEquals(Sale.TO_CONFIRM, sale.getState());
        } catch (PersistencyErrorException | RefundException | GameSoldOutException |
                 SyntaxErrorException | InvalidAddressException e) {
           fail();
        }
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the database there was game ('nameTest', '1', '20', 'descriptionTest')
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("nameTest");
        videogameBean.setCopiesBean(1);
        videogameBean.setPriceBean(20);
        UserBean userBean = new UserBean();
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        assertThrows(GameSoldOutException.class, () -> customerBoundary.insertCredentialsAndPay("card", "key", "Via Cambridge", "Roma", "Italy", videogameBean));
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){
        VideogameBean videogameBean = new VideogameBean();
        CustomerBoundary customerBoundary = new CustomerBoundary(new UserBean());
        assertThrows(InvalidAddressException.class, () -> customerBoundary.insertCredentialsAndPay("card", "key", "Viasbagliata", "Roma", "Italy", videogameBean));
    }

    @Test
    void insertCredentialsAndPayGmailRefundException(){     //In the sale table there was tuple ('nameTest', '2', '10', 'descriptionTest')
        UserBean userBean = new UserBean();
        userBean.setEmail("test");
        CustomerBoundary customerBoundary = new CustomerBoundary(userBean);
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName("nameTest");
        videogameBean.setCopiesBean(1);
        videogameBean.setPriceBean(10);
        videogameBean.setDescriptionBean("descriptionTest");
        assertThrows(RefundException.class, () -> customerBoundary.insertCredentialsAndPay("card", "key", "Via Cambridge", "Rome", "Italy", videogameBean));
    }
}