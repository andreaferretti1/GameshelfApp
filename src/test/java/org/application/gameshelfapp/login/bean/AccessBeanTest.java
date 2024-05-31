package org.application.gameshelfapp.login.bean;


import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccessBeanTest {

    @Test
    void acceptableEmailBeanTest(){
        try {
            AccessBean accessBean = new AccessBean();
            accessBean.setEmailBean("fer.andrea35@gmail.com");
            assertEquals("fer.andrea35@gmail.com", accessBean.getEmailBean());
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void nonAcceptableEmailBeanTest(){
        AccessBean accessBean = new AccessBean();
        assertThrows(SyntaxErrorException.class, () -> accessBean.setEmailBean("progetto.ispw.com"));
    }

    @Test
    void emptyEmailBeanTest(){
        AccessBean accessBean = new AccessBean();
        assertThrows(SyntaxErrorException.class, () -> accessBean.setEmailBean(""));
    }

    @Test
    void acceptablePasswordTest(){
        try {
            AccessBean accessBean = new AccessBean();
            accessBean.setPasswordBean("ciao");
            assertEquals("ciao", accessBean.getPasswordBean());
        } catch (SyntaxErrorException | NullPasswordException e) {
            fail();
        }
    }

    @Test
    void emptyPasswordBeanTest(){
        AccessBean accessBean = new AccessBean();
        assertThrows(SyntaxErrorException.class, () -> accessBean.setPasswordBean(""));
    }

    @Test
    void nullPasswordBeanTest(){
        AccessBean accessBean = new AccessBean();
        assertThrows(NullPasswordException.class, () -> accessBean.setPasswordBean(null));
    }

}
