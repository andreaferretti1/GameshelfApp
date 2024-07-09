package org.application.gameshelfapp.registration.boundary;

import org.application.gameshelfapp.login.dao.utils.AccessDAOCSVUtils;
import org.application.gameshelfapp.login.dao.utils.AccessDAOJDBCUtils;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class RegistrationBoundaryTest {

    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.truncateFile();
        else AccessDAOJDBCUtils.truncateTable();
    }

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
        String[][] records = {{"test", "testEmail@example.com", "test", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(CheckFailedException.class, () -> boundary.register("test", "testEmail2@example.com", "test"));
    }

    @Test
    void registerEmailCheckFailedException(){
        String[][] records = {{"test", "testEmail@example.com", "test", "Customer"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) AccessDAOCSVUtils.insertRecord(records);
        else AccessDAOJDBCUtils.insertRecords(records);
        RegistrationBoundary boundary = new RegistrationBoundary();
        assertThrows(CheckFailedException.class, () -> boundary.register("test1", "testEmail@example.com", "test"));
    }
}
