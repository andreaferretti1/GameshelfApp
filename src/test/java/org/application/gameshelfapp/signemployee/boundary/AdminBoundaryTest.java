package org.application.gameshelfapp.signemployee.boundary;

import org.application.gameshelfapp.buyvideogames.entities.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminBoundaryTest {

    @Test
    void registerTest(){
        try{
            AdminBoundary adminBoundary = new AdminBoundary();
            RegistrationBean registrationBean = new RegistrationBean();
            registrationBean.setEmailBean("testmail@gmail.com");
            registrationBean.setUsernameBean("usernameTest");
            registrationBean.setPasswordBean("password");
            registrationBean.setTypeOfUser("Seller");
            adminBoundary.register(registrationBean);
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
            AdminBoundary adminBoundary = new AdminBoundary();
            RegistrationBean registrationBean = new RegistrationBean();
            registrationBean.setUsernameBean("name");
            registrationBean.setEmailBean("emailtest@gmail.com");
            registrationBean.setPasswordBean("password");
            registrationBean.setTypeOfUser("seller");
            assertThrows(CheckFailedException.class, () -> adminBoundary.register(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }

    @Test
    void registerUsernameCheckFailedExceptionTest(){        //in the database there was tuple ('username', 'emailtest@gmail.com', 'passwordtest', 'seller')
        try{
            AdminBoundary adminBoundary = new AdminBoundary();
            RegistrationBean registrationBean = new RegistrationBean();
            registrationBean.setUsernameBean("username");
            registrationBean.setEmailBean("email@gmail.com");
            registrationBean.setPasswordBean("password");
            assertThrows(CheckFailedException.class, () -> adminBoundary.register(registrationBean));
        } catch(SyntaxErrorException e){
            fail();
        }
    }
}