package org.application.gameshelfapp.buyvideogames.boundary2;

import org.application.gameshelfapp.buyvideogames.dao.SaleDAO;
import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerminalCustomerBoundaryTest {
    @Test
    void getUserBeanTest(){
        TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
        assertNotNull(boundary.getUserBean());
    }

    @Test
    void executeCommandInsertFiltersNoGamesFoundTest(){       //Database was empty
        TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
        String[] command = {"see catalogue", "nameTest", "consoleTest", "categoryTest"};
        assertThrows(NoGameInCatalogueException.class, ()->boundary.executeCommand(command));

    }

    @Test
    void executeCommandInsertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
        try{
            String[] command = {"see catalogue", "nameTest1", "consoleTest", "categoryTest"};
            String returnString = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "nameTest1", "consoleTest", "categoryTest", 2, 11f, "descriptionTest1") + "\nType <select gameToBuy, gameTitle, console, category, copies, price>\n\n";
            String testString =  boundary.executeCommand(command);
            assertEquals(returnString, testString);
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException e){
            fail();
        }
    }

    @Test
    void insertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
        try{
            String[] command = {"see catalogue", null, "consoleTest", "categoryTest"};
            String expectedStringGame1 = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "nameTest1", "consoleTest", "categoryTest", 2, 11f, "descriptionTest1");
            String expectedStringGame2 = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "nameTest2", "consoleTest", "categoryTest", 3, 20f, "descriptionTest");
            String expectedString = expectedStringGame1 + expectedStringGame2 + "\nType <select gameToBuy, gameTitle, console, category, copies, price>\n\n";
            String returnString = boundary.executeCommand(command);
            assertEquals(expectedString, returnString);
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayTest(){

        try{
            UserBean userBean = new UserBean();
            userBean.setEmail("fer.andrea35@gmail.com");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            String[] command = {"select game", "gameNameTest", "consoleTest", "categoryTest", String.valueOf(2), String.valueOf(40)};
            boundary.executeCommand(command);
            command = new String[]{"pay", "Name","card", "key", "Via Cambridge", "Roma", "Italy"};
            boundary.executeCommand(command);
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
        try {
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
            String[] command = {"select game", null, null, null, String.valueOf(0), String.valueOf(0)};
            boundary.executeCommand(command);
            String[] finalCommand = {"pay", "testName", "test", "test", "via cmabridge", "Roma", "Italia"};
            assertThrows(NoGameInCatalogueException.class, () -> boundary.executeCommand(finalCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        try {
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
            String[] command = {"select game", "gameNameTest", "consoleTest", "categoryTest", String.valueOf(2), String.valueOf(20)};
            boundary.executeCommand(command);
            String[] finalCommand = {"pay", "Name", "card", "key", "Via Cambridge", "Roma", "Italy"};
            assertThrows(GameSoldOutException.class, () -> boundary.executeCommand(finalCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException e){
            fail();
        }
    }

    @Test
    void insertCredentialsAndPayInvalidAddressTest(){
        try {
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(new UserBean());
            String[] command = {"select game", null, null, null, String.valueOf(0), String.valueOf(0)};
            boundary.executeCommand(command);
            String[] finalCommand = {"pay", "Name", "card", "key", "Viasbagliata", "Roma", "Italy"};
            assertThrows(InvalidAddressException.class, () -> boundary.executeCommand(finalCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException e){
            fail();
        }
    }
}
