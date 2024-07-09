package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

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
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerAdapterTest {

    @AfterEach
    void truncateTable(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) {
            ItemDAOCSVUtils.truncateFile();
            SaleDAOCSVUtils.truncateFile();
        }
        else {
            ItemDAOJDBCUtils.truncateTable();
            SaleDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void getUserBeanTest(){
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        assertNotNull(adapter.getUserBean());
    }

    @Test
    void insertFiltersNoGamesFoundTest(){       //Database was empty
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        assertThrows(NoGameInCatalogueException.class, ()->adapter.searchVideogame("nameTest", "consoleTest", "categoryTest"));

    }

    @Test
    void inertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        String[][] records = {{"nameTest1", "consoleTest", "categoryTest", "descriptionTest1", "2", "11"}, {"nameTest2", "consoleTest", "categoryTest", "descriptionTest", "3", "20"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        try{
            List<VideogameBean> gamesBean =  adapter.searchVideogame("nameTest1", "consoleTest", "categoryTest").getSellingGamesBean();
            assertEquals(1, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        String[][] records = {{"nameTest1", "consoleTest", "categoryTest", "descriptionTest1", "2", "11"}, {"nameTest2", "consoleTest", "categoryTest", "descriptionTest", "3", "20"}, {"nameTest3", "consoleTest1", "categoryTest1", "descriptionTest2", "5", "10"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        try{
            List<VideogameBean> gamesBean = adapter.searchVideogame("null", "consoleTest", "categoryTest").getSellingGamesBean();
            assertEquals(2, (long) gamesBean.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayTest(){

        UserBean userBean = new UserBean();
        userBean.setEmail("fer.andrea35@gmail.com");
        CustomerAdapter adapter = new CustomerAdapter(userBean);
        adapter.chooseGameToBuy("gameNameTest", "consoleTest", "categoryTest", 2, 40);
        try {
            adapter.pay("Name","card", "key", "Via Cambridge", "Roma", "Italy");
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
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        adapter.chooseGameToBuy(null, null, null, 0, 0);
        assertThrows(NoGameInCatalogueException.class, () -> adapter.pay("testName", "test", "test", "via cmabridge", "Roma", "Italia"));
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        String[][] records = {{"gameNameTest", "consoleTest", "categoryTest", "descriptionTest", "1", "20"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) ItemDAOCSVUtils.insertRecord(records);
        else ItemDAOJDBCUtils.insertRecord(records);
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        adapter.chooseGameToBuy("gameNameTest", "consoleTest", "categoryTest", 2, 20);
        assertThrows(GameSoldOutException.class, () -> adapter.pay("Name", "card", "key", "Via Cambridge", "Roma", "Italy"));
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){
        CustomerAdapter adapter = new CustomerAdapter(new UserBean());
        adapter.chooseGameToBuy(null, null, null, 0, 0);
        assertThrows(InvalidAddressException.class, () -> adapter.pay("Name","card", "key", "Viasbagliata", "Roma", "Italy"));
    }
}
