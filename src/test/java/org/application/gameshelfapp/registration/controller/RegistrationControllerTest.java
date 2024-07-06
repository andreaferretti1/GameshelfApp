package org.application.gameshelfapp.registration.controller;

import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.registration.bean.RegistrationBean;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationControllerTest {

    @Test
    void registrationTest(){
        RegistrationController regController = new RegistrationController();
        RegistrationBean registrationBean = new RegistrationBean();
        try{
            registrationBean.setUsernameBean("nameTest");
            registrationBean.setEmailBean("fer.andrea35@gmail.com");
            registrationBean.setPasswordBean("testPassword");
            regController.registration(registrationBean);
        } catch(SyntaxErrorException | NullPasswordException | CheckFailedException | PersistencyErrorException |
                GmailException e){
            fail();
        }
    }
    @Test
    void regsitrationSameUsernameTest(){        //In the database there was tuple ('nameTest', 'email@gmail.com', 'testPassword', 'Customer')
        RegistrationController regController = new RegistrationController();
        RegistrationBean registrationBean = new RegistrationBean();
        try{
            registrationBean.setUsernameBean("nameTest");
            registrationBean.setEmailBean("email2@gmail.com");
            registrationBean.setPasswordBean("passwordTest");
            assertThrows(CheckFailedException.class, () -> regController.registration(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void registrationSameEmailTest(){       //In the database there was tuple ('nameTest', 'email@gmail.com', 'testPassword', 'Customer')
        RegistrationController regController = new RegistrationController();
        RegistrationBean registrationBean = new RegistrationBean();
        try{
            registrationBean.setUsernameBean("nameTest2");
            registrationBean.setEmailBean("email@gmail.com");
            registrationBean.setPasswordBean("testPassword");
            assertThrows(CheckFailedException.class, () -> regController.registration(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }
    @Test
    void setAndGetRegAccessTest(){
        RegistrationController regController = new RegistrationController();
        regController.setRegAccess(new AccessThroughRegistration(null, null, null, null));
        assertNotNull(regController.getRegAccess());
    }
}
