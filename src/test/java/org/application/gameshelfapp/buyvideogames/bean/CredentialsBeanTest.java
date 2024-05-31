package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CredentialsBeanTest {

    @Test
    void checkTypeOfPaymentSyntaxTest(){
        try {
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setTypeOfPaymentBean("PayPal");
            assertEquals("PayPal", credentialsBean.getTypeOfPaymentBean());
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void typeOfPaymentSyntaxErrorException(){
        CredentialsBean credentialsBean = new CredentialsBean();
        assertThrows(SyntaxErrorException.class, () -> credentialsBean.setTypeOfPaymentBean(""));
    }

    @Test
    void nullTypeOfPaymentTest(){
        CredentialsBean credentialsBean = new CredentialsBean();
        assertThrows(SyntaxErrorException.class, () -> credentialsBean.setTypeOfPaymentBean(null));
    }

    @Test
    void checkPaymentKeySyntaxTest(){
        try {
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setPaymentKeyBean("paymentKey");
            assertEquals("paymentKey", credentialsBean.getPaymentKeyBean());
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void paymentKeySyntaxErrorException(){
        CredentialsBean credentialsBean = new CredentialsBean();
        assertThrows(SyntaxErrorException.class, () -> credentialsBean.setPaymentKeyBean(""));
    }

    @Test
    void nullPaymentKeyTest(){
        CredentialsBean credentialsBean = new CredentialsBean();
        assertThrows(SyntaxErrorException.class, () -> credentialsBean.setPaymentKeyBean(null));
    }

    @Test
    void checkAddressSyntax(){
        try {
            CredentialsBean credentialsBean = new CredentialsBean();
            credentialsBean.setAddressBean("testAddress", "testCity", "testCountry");
            assertEquals("testAddress, testCity, testCountry", credentialsBean.getAddressBean());
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void nullAddressTest(){
        CredentialsBean credentialsBean = new CredentialsBean();
        assertThrows(SyntaxErrorException.class, () -> credentialsBean.setAddressBean(null, null, null));
    }
}
