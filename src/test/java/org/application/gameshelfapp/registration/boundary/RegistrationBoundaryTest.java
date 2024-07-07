package org.application.gameshelfapp.registration.boundary;

import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class RegistrationBoundaryTest {

    @Test
    void registerSuccessfulTest(){
        try{
            RegistrationBoundary boundary = new RegistrationBoundary();
            boundary.register("andrea", "fer.andrea35@gmail.com", "test");
        } catch(PersistencyErrorException | SyntaxErrorException | CheckFailedException | GmailException |
                NullPasswordException e){
            fail();
        }
    }

    @Test
    void registerUsernameSyntaxErrorExceptionTest(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(SyntaxErrorException.class,() ->boundary.register(".-<", "fer.andrea35@gmail.com", "test"));
    }

    @Test
    void registerEmailSyntaxErrorException(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(SyntaxErrorException.class, () -> boundary.register("testName", "testEmail", "test"));
    }

    @Test  //credentials already existing in persistency are username: test, email: testEmail@example.com, password: test, typeOfUser: customer.
    void registerUsernameCheckFailedException(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(CheckFailedException.class, () -> boundary.register("test", "testEmail2@example.com", "test"));
    }

    @Test
    void registerEmailCheckFailedException(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(CheckFailedException.class, () -> boundary.register("test1", "testEmail@example.com", "test"));
    }
    @Test
    void checkCodeNumberFormatExceptionTest(){
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(NumberFormatException.class, () -> boundary.checkCode("test"));
    }
}
