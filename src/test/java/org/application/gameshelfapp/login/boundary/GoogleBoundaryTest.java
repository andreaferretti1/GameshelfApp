package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.exception.GmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GoogleBoundaryTest {

    @Test
    void sendMailTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.sendMail("test", "test passato", "fer.andrea35@gmail.com");
        } catch(GmailException e){
            fail();
        }
    }

    @Test
    void invalidEmailAddressTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            assertThrows(GmailException.class, () -> googleBoundary.sendMail("test", "test passed", "invalidemail"));
        } catch(GmailException e){
            fail();
        }
    }
}
