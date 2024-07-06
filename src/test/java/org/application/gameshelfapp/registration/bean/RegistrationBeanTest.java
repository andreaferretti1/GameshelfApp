package org.application.gameshelfapp.registration.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationBeanTest {

    @Test
    void acceptableUsernameBean(){
        try {
            RegistrationBean regBean = new RegistrationBean();
            regBean.setUsernameBean("andrea21._");
            assertEquals("andrea21._", regBean.getUsernameBean());
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void nonAcceptableUsernameBean(){
        RegistrationBean regBean = new RegistrationBean();
        assertThrows(SyntaxErrorException.class, () -> regBean.setUsernameBean("andrea21._*"));
    }

    @Test
    void emptyUsernameBean(){
        RegistrationBean regBean = new RegistrationBean();
        assertThrows(SyntaxErrorException.class, () -> regBean.setUsernameBean(""));
    }

    @Test
    void checkCodeBeanTest(){
        RegistrationBean regBean = new RegistrationBean();
        regBean.setCheckCode(90);
        assertEquals(90, regBean.getCheckCode());
    }
}
