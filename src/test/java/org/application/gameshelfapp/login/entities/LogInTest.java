package org.application.gameshelfapp.login.entities;


import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


//implemented by Andrea Ferretti

/* for all the tests, the correct credentials are:
    username: "andrea"
    email: "fer.andrea35@gmail.com"
    password: "ciao"
 */
public class LogInTest {


    @Test
    public void logInWithCorrectCredentials(){

        try {
            UserLogInBoundary boundary = new UserLogInBoundary();
            boundary.log("fer.andrea35@gmail.com", "ciao");
            assertNotNull(boundary.getUserBean());
        } catch (PersistencyErrorException | CheckFailedException | SyntaxErrorException e) {
            fail("é stata lanciata una eccezione con il seguente messaggio: " + e.getMessage());
        }
    }


    @Test
    public void logInWithIncorrectPassword(){


        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("fer.andrea35@gmail.com", "abab"));

    }

    @Test
    public void logInWithIncorrectEmail(){
        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(CheckFailedException.class, () -> userLogInBoundary.log("aaaa.fdfdf@gmail.com", "ciao"));
    }

    @Test
    public void logInWrongEmailSyntax(){

        UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
        assertThrows(SyntaxErrorException.class, () -> userLogInBoundary.log("email@examplecom", null));
    }
}
