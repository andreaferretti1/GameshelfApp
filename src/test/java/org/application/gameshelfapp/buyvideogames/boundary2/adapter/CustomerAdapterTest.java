package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerAdapterTest {

    @Test
    void getUserBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);
            assertNotNull(adapter.getUserBean());
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertFiltersNoGamesFoundTest(){       //Database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);
            assertThrows(NoGameInCatalogueException.class, () -> adapter.searchVideogame("nameTest", "consoleTest", "categoryTest"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void inertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);

            List<VideogameBean> gamesBean =  adapter.searchVideogame("nameTest1", "consoleTest", "categoryTest").getSellingGamesBean();
            assertEquals(1, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);

            List<VideogameBean> gamesBean = adapter.searchVideogame("null", "consoleTest", "categoryTest").getSellingGamesBean();
            assertEquals(2, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayTest(){
        try{
            UserBean userBean = new UserBean();
            userBean.setEmail("fer.andrea35@gmail.com");
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);
            adapter.chooseGameToBuy("gameNameTest", "consoleTest", "categoryTest", 2, 40);

            adapter.pay("Name","card", "key", "Via Cambridge", "Roma", "Italy");
            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            assertEquals(1, (long) sales.size());
        } catch (PersistencyErrorException | RefundException | GameSoldOutException |
                 SyntaxErrorException | InvalidAddressException | NoGameInCatalogueException | WrongUserTypeException e) {
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayNoGameInCatalogueExceptionTest(){       //database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);
            adapter.chooseGameToBuy(null, null, null, 0, 0);
            assertThrows(NoGameInCatalogueException.class, () -> adapter.pay("testName", "test", "test", "via cmabridge", "Roma", "Italia"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);
            adapter.chooseGameToBuy("gameNameTest", "consoleTest", "categoryTest", 2, 20);
            assertThrows(GameSoldOutException.class, () -> adapter.pay("Name", "card", "key", "Via Cambridge", "Roma", "Italy"));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            CustomerAdapter adapter = new CustomerAdapter(userBean);
            adapter.chooseGameToBuy(null, null, null, 0, 0);
            assertThrows(InvalidAddressException.class, () -> adapter.pay("Name","card", "key", "Viasbagliata", "Roma", "Italy"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}
