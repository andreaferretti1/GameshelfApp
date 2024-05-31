package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CredentialsTest {
    @Test
    void getTypeOfPaymentTest(){
        Credentials credentials = new Credentials("paymentTest", null, null);
        assertEquals("paymentTest", credentials.getTypeOfPayment());
    }

    @Test
    void getPaymentKey(){
        Credentials credentials = new Credentials(null, "keyTest", null);
        assertEquals("keyTest", credentials.getPaymentKey());
    }

    @Test
    void getAddressTest(){
        Credentials credentials = new Credentials(null, null, "addressTest");
        assertEquals("addressTest", credentials.getAddress());
    }
}
