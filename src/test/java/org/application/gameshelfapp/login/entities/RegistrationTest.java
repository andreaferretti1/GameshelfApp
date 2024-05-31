package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

//test case implemented by Andrea Ferretti

/* for the test were used the following parameters:
    username: "andrea"
    email: "fer.andrea35@gmail.com"
    password: "ciao"
    type of user: "customer"
 */
class RegistrationTest {

    @Test
    void registrationTestSuccess(){
        try{
            UserLogInBoundary userLogInBoundary = new UserLogInBoundary();
            userLogInBoundary.register("andrea", "fer.andrea35@gmail.com", "ciao");
            Scanner scanner = new Scanner(System.in);
            String code = scanner.nextLine();
            scanner.close();
            userLogInBoundary.checkCode(code);
            assertNotNull(userLogInBoundary.getUserBean());
        } catch(PersistencyErrorException | CheckFailedException | GmailException | SyntaxErrorException | NullPasswordException e) {
            fail("è stata lanciata una eccezione " + e.getMessage());
        }
    }
}
