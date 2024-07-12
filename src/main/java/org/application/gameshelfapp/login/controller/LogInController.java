package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.buyvideogames.entities.User;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class LogInController {
    public UserBean logIn(LogInBean logBean) throws PersistencyErrorException, CheckFailedException, NullPasswordException {

        String logEmail = logBean.getEmailBean();
        String logPassword = logBean.getPasswordBean();

        AccessThroughLogIn logAccess = new AccessThroughLogIn(logEmail, logPassword, null);
        logAccess.encodePassword();

        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
        Access access = accessDAO.retrieveAccountByEmail(logBean.getEmailBean());
        logAccess.checkAccount(access);
        User user = new User(access.getUsername(), access.getEmail(), access.getTypeOfUser());
        UserBean userBean = new UserBean();
        userBean.setUser(user);
        return userBean;
    }
}
