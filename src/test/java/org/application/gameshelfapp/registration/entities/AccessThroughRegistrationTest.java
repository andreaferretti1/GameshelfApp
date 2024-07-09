package org.application.gameshelfapp.registration.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class AccessThroughRegistrationTest {

    @Test
    void checkCodePassedTest(){
        try {
           AccessThroughRegistration regAccess = new AccessThroughRegistration(null, null, null, null);
           regAccess.generateCode();
           regAccess.checkCode(regAccess.getCodeGenerated());
       } catch(CheckFailedException e){
            fail();
        }
    }

    @Test
    void checkCodeFailedTest(){
        AccessThroughRegistration regAccess = new AccessThroughRegistration(null, null, null, null);
        regAccess.generateCode();
        assertThrows(CheckFailedException.class, () -> regAccess.checkCode(regAccess.getCodeGenerated() + 1));
    }

}
