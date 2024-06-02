package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.junit.jupiter.api.Test;
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
