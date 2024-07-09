package org.application.gameshelfapp.signemployee.boundary;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminBoundaryTest {

    @Test
    void registerTest(){
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            AdminBoundary adminBoundary = new AdminBoundary(userBean);
            adminBoundary.register("usernameTest", "testmail@gmail.com", "password", "Seller");
            AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(new AccessThroughLogIn("testmail@gmail.com", null, null));
            assertNotNull(access);
        } catch(PersistencyErrorException | SyntaxErrorException | NullPasswordException | CheckFailedException |
                WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void registerEmailCheckFailedExceptionTest() {        //in the database there was tuple ('username', 'emailtest@gmail.com', 'passwordtest', 'Seller')
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            AdminBoundary adminBoundary = new AdminBoundary(userBean);
            assertThrows(CheckFailedException.class, () -> adminBoundary.register("name", "emailtest@gmail.com", "password", "Seller"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void registerUsernameCheckFailedExceptionTest() {        //in the database there was tuple ('username', 'emailtest@gmail.com', 'passwordtest', 'Seller')
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Admin");
            AdminBoundary adminBoundary = new AdminBoundary(userBean);
            assertThrows(CheckFailedException.class, () -> adminBoundary.register("username", "email@gmail.com", "password", null));
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}