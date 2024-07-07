package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.User;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.registration.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.entities.*;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.registration.controller.RegistrationController;

public class LogInController {
    private RegistrationController regController;

    public UserBean logIn(LogInBean logBean) throws PersistencyErrorException, CheckFailedException, NullPasswordException {

        String logEmail = logBean.getEmailBean();
        String logPassword = logBean.getPasswordBean();

        AccessThroughLogIn logAccess = new AccessThroughLogIn(logEmail, logPassword, null);
        logAccess.encodePassword();

        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
        Access access = accessDAO.retrieveAccountByEmail(logAccess);
        logAccess.checkAccount(access);
        User user = new User(access.getUsername(), access.getEmail(), access.getTypeOfUser());
        UserBean userBean = new UserBean();
        userBean.setUser(user);
        return userBean;
    }

    public void registration(RegistrationBean regBean) throws PersistencyErrorException, CheckFailedException, GmailException, NullPasswordException {
        this.regController = new RegistrationController();
        this.regController.registration(regBean);
    }

    public UserBean checkCode(int code) throws CheckFailedException, PersistencyErrorException{
        return this.regController.checkCode(code);
    }
}
