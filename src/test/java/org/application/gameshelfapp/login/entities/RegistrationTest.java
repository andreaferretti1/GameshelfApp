package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

//test case implemented by Andrea Ferretti

/* for the test were used the following parameters:
    username: "andrea"
    email: "fer.andrea35@gmail.com"
    password: "ciao"
    type of user: "customer"
 */
public class RegistrationTest {

    @Test
    public void registrationTestSuccess(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.register("andrea", "fer.andrea35@gmail.com", "ciao", "customer");
            Scanner scanner = new Scanner(System.in);
            String code = scanner.nextLine();
            scanner.close();
            userLogInBoundary.checkCode(code);
            assertNotNull(userLogInBoundary.getUserBean());
        } catch(PersistencyErrorException | CheckFailedException | GmailException | SyntaxErrorException e) {
            fail("è stata lanciata una eccezione " + e.getMessage());
        }
    }
}
