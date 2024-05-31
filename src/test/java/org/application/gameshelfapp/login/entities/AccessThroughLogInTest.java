package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccessThroughLogInTest {

    @Test
    void checkAccountPassedTest(){
        try {
            AccessThroughLogIn access1 = new AccessThroughLogIn(null, "test", null);
            access1.encodePassword();
            AccessThroughLogIn access2 = new AccessThroughLogIn(null, "test", null);
            access2.encodePassword();
            access2.checkAccount(access1);
        } catch(NullPasswordException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void checkAccountWrongPasswordTest(){
        try{
            AccessThroughLogIn access1 = new AccessThroughLogIn(null, "testFailed", null);
            access1.encodePassword();
            AccessThroughLogIn access2 = new AccessThroughLogIn(null, "test", null);
            access2.encodePassword();
            assertThrows(CheckFailedException.class, () -> access2.checkAccount(access1));
        } catch(NullPasswordException e){
            fail();
        }
    }

    @Test
    void checkAccountNullAccessTest(){
        try{
            AccessThroughLogIn access1 = new AccessThroughLogIn(null, "test", null);
            access1.encodePassword();
            assertThrows(CheckFailedException.class, () -> access1.checkAccount(null));
        } catch(NullPasswordException e){
            fail();
        }
    }
}
