package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccessThroughRegistrationTest {

    @Test
    void getMessageToSendTest(){
        AccessThroughRegistration regAccess = new AccessThroughRegistration(null, null, null, null);
        String message = regAccess.getMessageToSend();
        int codeGenerated = regAccess.getCodeGenerated();
        assertEquals("Your code is " + codeGenerated, message);
    }

    @Test
    void checkCodePassedTest(){
        try {
           AccessThroughRegistration regAccess = new AccessThroughRegistration(null, null, null, null);
           regAccess.getMessageToSend();
           regAccess.checkCode(regAccess.getCodeGenerated());
       } catch(CheckFailedException e){
            fail();
        }
    }

    @Test
    void checkCodeFailedTest(){
        AccessThroughRegistration regAccess = new AccessThroughRegistration(null, null, null, null);
        regAccess.getMessageToSend();
        assertThrows(CheckFailedException.class, () -> regAccess.checkCode(regAccess.getCodeGenerated() + 1));
    }

    @Test
    void checkAccountPassedtest(){
        try{
            AccessThroughRegistration regAccess1 = new AccessThroughRegistration("testNameDiff", "testEmailDiff", null, null);
            AccessThroughRegistration regAccess2 = new AccessThroughRegistration("testName", "testEmail", null, null);
            regAccess1.checkAccount(regAccess2);
        } catch(CheckFailedException e){
            fail();
        }
    }

    @Test
    void checkAccountUsernameFailedTest(){
        AccessThroughRegistration regAccess1 = new AccessThroughRegistration("testName", "testEmailDiff", null, null);
        AccessThroughRegistration regAccess2 = new AccessThroughRegistration("testName", "testEmail", null, null);
        assertThrows(CheckFailedException.class, () -> regAccess1.checkAccount(regAccess2));
    }

    @Test
    void checkAccountEmailFailedTest(){
        AccessThroughRegistration regAccess1 = new AccessThroughRegistration("testNameDiff", "testEmail", null, null);
        AccessThroughRegistration regAccess2 = new AccessThroughRegistration("testName", "testEmail", null, null);
        assertThrows(CheckFailedException.class, () -> regAccess1.checkAccount(regAccess2));
    }

    @Test
    void checkAccountNullAccessTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration(null, null, null, null);
            regAccess.checkAccount(null);
        } catch(CheckFailedException e){
            fail();
        }
    }
}
