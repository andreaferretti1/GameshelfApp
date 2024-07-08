package org.application.gameshelfapp.sellvideogames.boundary2;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TerminalSellerAddGamesBoundaryTest {
    @Test
    void executeCommandGetSellingCatalogueTest(){             //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"show", "Dark Souls", "TestConsole2", "TestCategory2"};
            String testReturn = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "Dark Souls", "TestConsole2", "TestCategory2", 1, 10f, "This is another test") + "\nType <add/remove/update, gameTitle, console, category, description, copies, price>\n\n";
            String testString = test.executeCommand(testCommand);
            assertEquals(testReturn, testString);
        } catch (PersistencyErrorException | NoGameInCatalogueException | CheckFailedException | GmailException |
                 InvalidTitleException | AlreadyExistingVideogameException | GameSoldOutException |
                 WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandGetSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"show", "Dark Souls", "TestConsole2", "TestCategory2"};
            assertThrows(NoGameInCatalogueException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandAddSellingGamesTest(){
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"add", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            String testReturn = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "Dark Souls", "TestConsole2", "TestCategory2", 1, 10f, "This is another test") + "\nType <add/remove/update, gameTitle, console, category, description, copies, price>\n\n";
            assertEquals(testReturn, test.executeCommand(testCommand));
        } catch(PersistencyErrorException | CheckFailedException | InvalidTitleException | NoGameInCatalogueException |
                GmailException | AlreadyExistingVideogameException | GameSoldOutException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandAddSellingGamesInvalidTitleExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"add", "Dark Souls 9", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            assertThrows(InvalidTitleException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandAddSellingGamesAlreadyExistingVideogameExceptionLaunchedTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"add", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            assertThrows(AlreadyExistingVideogameException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandRemoveSellingGamesCatalogueTest(){         //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"remove", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            String testReturn = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "Dark Souls", "TestConsole2", "TestCategory2", 0, 10f, "This is another test") + "\nType <add/remove/update, gameTitle, console, category, description, copies, price>\n\n";
            assertEquals(testReturn, test.executeCommand(testCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | GameSoldOutException | CheckFailedException |
                GmailException | InvalidTitleException | AlreadyExistingVideogameException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandRemoveSellingGamesCatalogueNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"remove", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            assertThrows(NoGameInCatalogueException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandRemoveSellingGamesCatalogueGameSolOutExceptionLaunchedTest(){          //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"remove", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "2", "10"};
            assertThrows(GameSoldOutException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandUpdateSellingGameTest(){            //In the database there exist tuple(Dark Souls,TestConsole2,TestCategory2,This is another test,1,10)
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"update", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            String testReturn = String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", "Dark Souls", "TestConsole2", "TestCategory2", 2, 10f, "This is another test") + "\nType <add/remove/update, gameTitle, console, category, description, copies, price>\n\n";
            assertEquals(testReturn, test.executeCommand(testCommand));
        } catch(PersistencyErrorException | NoGameInCatalogueException | CheckFailedException | GmailException |
                InvalidTitleException | AlreadyExistingVideogameException | GameSoldOutException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandUpdateSellingGameNoGameInCatalogueExceptionLaunchedTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            String[] testCommand = {"update", "Dark Souls", "TestConsole2", "TestCategory2", "This is another test", "1", "10"};
            assertThrows(NoGameInCatalogueException.class, () -> test.executeCommand(testCommand));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getUserBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerAddGamesBoundary test = new TerminalSellerAddGamesBoundary(userBean);
            assertNotNull(test.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}

