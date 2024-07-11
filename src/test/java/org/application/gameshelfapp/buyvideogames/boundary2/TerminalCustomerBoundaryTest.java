package org.application.gameshelfapp.buyvideogames.boundary2;

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

class TerminalCustomerBoundaryTest {
    @Test
    void getUserBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            assertNotNull(boundary.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandInsertFiltersNoGamesFoundTest(){       //Database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Csutomer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            String[] command = {"see catalogue", "nameTest", "consoleTest", "categoryTest"};
            assertThrows(NoGameInCatalogueException.class, () -> boundary.executeCommand(command));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandInsertFiltersByNameTest(){      //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20')
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);

            String[] command = {"see catalogue", "nameTest1", "consoleTest", "categoryTest"};
            String returnString = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "nameTest1", "consoleTest", "categoryTest", 2, 11f, "descriptionTest1") + "\nType <select gameToBuy, gameTitle, console, category, copies, price>\n\n";
            String testString =  boundary.executeCommand(command);
            assertEquals(returnString, testString);
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandInsertFiltersWithoutNameTest(){        //In the database there were tuples ('nameTest1', 'consoleTest', 'categoryTest', 'descriptionTest1', '2', '11'), ('nameTest2', 'consoleTest', 'categoryTest', 'descriptionTest', '3', '20'), ('nameTest3', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '5', '30')
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);

            String[] command = {"see catalogue", "null", "consoleTest", "categoryTest"};
            String expectedStringGame1 = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "nameTest1", "consoleTest", "categoryTest", 2, 11f, "descriptionTest1");
            String expectedStringGame2 = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "nameTest2", "consoleTest", "categoryTest", 3, 20f, "descriptionTest");
            String expectedString = expectedStringGame1 + expectedStringGame2 + "\nType <select gameToBuy, gameTitle, console, category, copies, price>\n\n";
            String returnString = boundary.executeCommand(command);
            assertEquals(expectedString, returnString);
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandInsertCredentialsAndPayTest(){

        try{
            UserBean userBean = new UserBean();
            userBean.setEmail("fer.andrea35@gmail.com");
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            String[] command = {"select game", "gameNameTest", "consoleTest", "categoryTest", String.valueOf(2), String.valueOf(40)};
            boundary.executeCommand(command);
            command = new String[]{"pay", "Name","card", "key", "Via Cambridge", "Roma", "Italy"};
            boundary.executeCommand(command);
            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            assertEquals(1, (long) sales.size());
        } catch (PersistencyErrorException | RefundException | GameSoldOutException |
                 SyntaxErrorException | InvalidAddressException | NoGameInCatalogueException | WrongUserTypeException e) {
            fail();
        }
    }

    @Test
    void executeCommandInsertCredentialsAndPayNoGameInCatalogueExceptionTest(){       //database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            String[] command = {"select game", null, null, null, String.valueOf(0), String.valueOf(0)};
            boundary.executeCommand(command);
            String[] finalCommand = {"pay", "testName", "test", "test", "via cmabridge", "Roma", "Italia"};
            assertThrows(NoGameInCatalogueException.class, () -> boundary.executeCommand(finalCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandInsertCredentialsAndPayGameSoldOutTest(){      //In the VideogameTable there was game ('gameNameTest', '1', '20', 'descriptionTest', 'consoleTest','categoryTest')
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            String[] command = {"select game", "gameNameTest", "consoleTest", "categoryTest", String.valueOf(2), String.valueOf(20)};
            boundary.executeCommand(command);
            String[] finalCommand = {"pay", "Name", "card", "key", "Via Cambridge", "Roma", "Italy"};
            assertThrows(GameSoldOutException.class, () -> boundary.executeCommand(finalCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandInsertCredentialsAndPayInvalidAddressTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Customer");
            TerminalCustomerBoundary boundary = new TerminalCustomerBoundary(userBean);
            String[] command = {"select game", null, null, null, String.valueOf(0), String.valueOf(0)};
            boundary.executeCommand(command);
            String[] finalCommand = {"pay", "Name", "card", "key", "Viasbagliata", "Roma", "Italy"};
            assertThrows(InvalidAddressException.class, () -> boundary.executeCommand(finalCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | RefundException | GameSoldOutException | SyntaxErrorException | InvalidAddressException | WrongUserTypeException e){
            fail();
        }
    }
}
