package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.User;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.entities.*;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class LogInController {
    private AccessThroughRegistration regAccess;
    public void setRegAccess(AccessThroughRegistration regAccess){
        this.regAccess = regAccess;
    }
    public AccessThroughRegistration getRegAccess(){
        return this.regAccess;
    }
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
        this.regAccess = new AccessThroughRegistration(regBean.getUsernameBean(), regBean.getEmailBean(), regBean.getPasswordBean(), "Customer");
        this.regAccess.encodePassword();
        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
        accessDAO.checkAccount(this.regAccess);
        this.regAccess.generateCode();
        GoogleBoundary googleBoundary = new GoogleBoundary();
        String message = "Your code is " + this.regAccess.getCodeGenerated() + ".";
        googleBoundary.setMessageToSend(message);
        googleBoundary.sendMail("check email", regAccess.getEmail());
    }

    public UserBean checkCode(RegistrationBean regBean) throws CheckFailedException, PersistencyErrorException{
        this.regAccess.checkCode(regBean.getCheckCode());
        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
        accessDAO.saveAccount(this.regAccess);
        User user = new User(regAccess.getUsername(), regAccess.getEmail(), regAccess.getTypeOfUser());
        UserBean userBean = new UserBean();
        userBean.setUser(user);
        return userBean;
    }
}
