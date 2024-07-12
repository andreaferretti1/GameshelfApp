package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.exception.GmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GoogleBoundaryTest {

    @Test
    void sendReceiptMessage(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.sendReceiptMessage("nameTest", 2, 30, "fer.andrea35@gmail.com");
        } catch (GmailException e){
            fail();
        }
    }
    @Test
    void sendNewSaleMailTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.sendNewSaleMessage("testMail", "nameTest", 3, 30);
        } catch(GmailException e){
            fail();
        }
    }

    @Test
    void sendSaleConfirmationMessageTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.sendSaleConfirmationMessage("fer.andrea35@gmail.com", "gameNameTest");
        } catch(GmailException e){
            fail();
        }
    }

    @Test
    void sendCheckCodeMessageTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.sendCheckCodeMessage("fer.andrea35@gmail.com", 300);
        } catch(GmailException e){
            fail();
        }
    }
    @Test
    void sendMailToRandomCustomerTest(){
        try{
            GoogleBoundary googleBoundary = new GoogleBoundary();
            googleBoundary.sendMailToRandomCustomer("fer.andrea35@gmail.com", "gameNameTest", "platformTest");
        } catch(GmailException e){
            fail();
        }
    }
}
