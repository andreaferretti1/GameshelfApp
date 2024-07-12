package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CredentialsTest {

    @Test
    void getNameTest(){
        Credentials credentials = new Credentials("nameTest", null, null, null, null);
        assertEquals("nameTest", credentials.getName());
    }
    @Test
    void setNameTest(){
        Credentials credentials = new Credentials(null, null, null, null, null);
        credentials.setName("nameTest");
        assertEquals("nameTest", credentials.getName());
    }
    @Test
    void getTypeOfPaymentTest(){
        Credentials credentials = new Credentials(null, "paymentTest", null, null, null);
        assertEquals("paymentTest", credentials.getTypeOfPayment());
    }

    @Test
    void setTypeOfPaymentTest(){
        Credentials credentials = new Credentials(null, null, null, null, null);
        credentials.setTypeOfPayment("paymentTest");
        assertEquals("paymentTest", credentials.getTypeOfPayment());
    }

    @Test
    void getPaymentKeyTest(){
        Credentials credentials = new Credentials(null, null, "keyTest", null, null);
        assertEquals("keyTest", credentials.getPaymentKey());
    }

    @Test
    void setPaymentKeyTest(){
        Credentials credentials = new Credentials(null, null, null, null, null);
        credentials.setPaymentKey("keyTest");
        assertEquals("keyTest", credentials.getPaymentKey());
    }

    @Test
    void getAddressTest(){
        Credentials credentials = new Credentials(null, null, null, "addressTest", null);
        assertEquals("addressTest", credentials.getAddress());
    }

    @Test
    void setAddressTest(){
        Credentials credentials = new Credentials(null, null, null, null, null);
        credentials.setAddress("addressTest");
        assertEquals("addressTest", credentials.getAddress());
    }
    @Test
    void setEmailTest(){
        Credentials credentials = new Credentials(null, null, null, null, null);
        credentials.setEmail("emailTest");
        assertEquals("emailTest", credentials.getEmail());
    }
    @Test
    void getEmailTest(){
        Credentials credentials = new Credentials(null, null, null, null, "emailTest");
        assertEquals("emailTest", credentials.getEmail());
    }
}
