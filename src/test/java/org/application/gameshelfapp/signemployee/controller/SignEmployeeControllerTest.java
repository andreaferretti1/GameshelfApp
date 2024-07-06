package org.application.gameshelfapp.signemployee.controller;

import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.registration.bean.RegistrationBean;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignEmployeeControllerTest {

    @Test
    void registerTest(){
        try{
            SignEmployeeController controller = new SignEmployeeController();
            RegistrationBean registrationBean = new RegistrationBean();
            registrationBean.setEmailBean("testmail@gmail.com");
            registrationBean.setUsernameBean("usernameTest");
            registrationBean.setPasswordBean("password");
            registrationBean.setTypeOfUser("Seller");
            controller.registerEmployee(registrationBean);
            AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(new AccessThroughLogIn("testmail@gmail.com", null, null));
            assertNotNull(access);
        } catch(PersistencyErrorException | SyntaxErrorException | NullPasswordException | CheckFailedException e){
            fail();
        }
    }

    @Test
    void registerEmailCheckFailedExceptionTest(){        //in the database there was tuple ('username', 'emailtest@gmail.com', 'passwordtest', 'seller')
        try{
            SignEmployeeController controller = new SignEmployeeController();
            RegistrationBean registrationBean = new RegistrationBean();
            registrationBean.setUsernameBean("name");
            registrationBean.setEmailBean("testmail@gmail.com");
            registrationBean.setPasswordBean("password");
            registrationBean.setTypeOfUser("Seller");
            assertThrows(CheckFailedException.class, () -> controller.registerEmployee(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void registerUsernameCheckFailedExceptionTest(){        //in the database there was tuple ('username', 'emailtest@gmail.com', 'passwordtest', 'seller')
        try{
            SignEmployeeController controller = new SignEmployeeController();
            RegistrationBean registrationBean = new RegistrationBean();
            registrationBean.setUsernameBean("username");
            registrationBean.setEmailBean("email@gmail.com");
            registrationBean.setPasswordBean("password");
            assertThrows(CheckFailedException.class, () -> controller.registerEmployee(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }
}