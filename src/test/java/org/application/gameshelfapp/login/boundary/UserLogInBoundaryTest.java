package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserLogInBoundaryTest {
    @Test
    void logSuccessfulTest(){       //Username: test, email: testEmail@example.com, password: test, typeOfUser: customer;
        try {
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.log("testEmail@example.com", "test");
            assertNotNull(userLogInBoundary.getUserBean());
            assertEquals("test", userLogInBoundary.getUserBean().getUsername());
            assertEquals("testEmail@example.com", userLogInBoundary.getUserBean().getEmail());
            assertEquals("customer", userLogInBoundary.getUserBean().getTypeOfUser());
        }catch (PersistencyErrorException | SyntaxErrorException | CheckFailedException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void logSyntaxErrorExceptionTest(){
        try {
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(SyntaxErrorException.class, () -> userLogInBoundary.log("test", "testEmail"));
        }catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void logEmailCheckFailedExceptionTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("testEMAIL@example.com", "test"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void logPasswordCheckFailedExceptionTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("testEmail@example.com", "TEST"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void logPasswordNullExceptionTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(NullPasswordException.class, () -> userLogInBoundary.log("testEmail@example.com", null));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void registerSuccessfulTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.register("andrea", "fer.andrea35@gmail.com", "test");
        } catch(PersistencyErrorException | SyntaxErrorException | CheckFailedException | GmailException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void registerUsernameSyntaxErrorExceptionTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(SyntaxErrorException.class,() ->userLogInBoundary.register(".-<", "fer.andrea35@gmail.com", "test"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void registerEmailSyntaxErrorException(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(SyntaxErrorException.class, () -> userLogInBoundary.register("testName", "testEmail", "test"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test  //credentials already existing in persistency are username: test, email: testEmail@example.com, password: test, typeOfUser: customer.
    void registerUsernameCheckFailedException(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(CheckFailedException.class, () -> userLogInBoundary.register("test", "testEmail2@example.com", "test"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void registerEmailCheckFailedException(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(CheckFailedException.class, () -> userLogInBoundary.register("test1", "testEmail@example.com", "test"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void registerNullPasswordException(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(NullPasswordException.class, () -> userLogInBoundary.register("test", "testEmail@example.com", null));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void checkCodeSuccessfulTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.register("andrea", "fer.andrea35@gmail.com", "test");
            AccessThroughRegistration accessThroughRegistration = userLogInBoundary.getController().getRegAccess();
            userLogInBoundary.checkCode(String.valueOf(accessThroughRegistration.getCodeGenerated()));
        } catch(PersistencyErrorException | SyntaxErrorException | CheckFailedException | GmailException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void checkCodeFailedTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.register("andrea", "fer.andrea35@gmail.com", "test");
            AccessThroughRegistration accessThroughRegistration = userLogInBoundary.getController().getRegAccess();
            assertThrows(CheckFailedException.class, () -> userLogInBoundary.checkCode(String.valueOf(accessThroughRegistration.getCodeGenerated() + 1)));
        } catch(PersistencyErrorException | SyntaxErrorException | CheckFailedException | GmailException | NullPasswordException e){
            fail();
        }
    }
    @Test
    void checkCodeNumberFormatExceptionTest(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            assertThrows(NumberFormatException.class, () -> userLogInBoundary.checkCode("test"));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

}
