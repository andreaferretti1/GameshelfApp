package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.exception.GmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GoogleBoundaryTest {

    @Test
    void setAndGetMessageToSend(){
        try {
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.setMessageToSend("messageTest");
            assertEquals("messageTest", googleBoundary.getMessageToSend());
        } catch (GmailException e) {
            fail();
        }
    }
    @Test
    void sendMailTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            String message = "test passed";
            googleBoundary.setMessageToSend(message);
            googleBoundary.sendMail("test", "fer.andrea35@gmail.com");
        } catch(GmailException e){
            fail();
        }
    }

    @Test
    void invalidEmailAddressTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            String message = "test passed";
            googleBoundary.setMessageToSend(message);
            assertThrows(GmailException.class, () -> googleBoundary.sendMail("test", "invalidemail"));
        } catch(GmailException e){
            fail();
        }
    }
}
