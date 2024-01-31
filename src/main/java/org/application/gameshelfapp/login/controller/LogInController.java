package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.buyvideogames.entities.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.User;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.boundary.FacebookLogInBoundary;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.entities.*;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.PersistencyAccountException;

import java.io.IOException;


public class LogInController {
    private final UserLogInBoundary logInBoundary;
    private User user;

    private FacebookLogInBoundary facebookBoundary;
    private GoogleBoundary googleBoundary;
    private AccessThroughRegistration regAccess;
    private AccessThroughLogIn logAccess;
    PersistencyAbstractFactory factory;
    private AccessDAO accessDAO;

    public LogInController(UserLogInBoundary userLogInBoundary){
        this.logInBoundary = userLogInBoundary;
        this.factory = factory.getFactory();
        this.accessDAO = this.factory.createAccessDAO();
    }


    public void logIn(LogInBean logBean) throws PersistencyErrorException, PersistencyAccountException, CheckFailedException{

        String logUsername = logBean.getUsernameBean();
        String logEmail = logBean.getEmailBean();
        String logPassword = logBean.getPasswordBean();

         this.logAccess = new AccessThroughLogIn(logUsername, logEmail, logPassword, null);
         this.logAccess.encodePassword();


         Access user = this.accessDAO.retrieveAccount(logAccess);
         this.logAccess.checkAccount(user);

    }

    public void registration(RegistrationBean regBean) throws PersistencyErrorException, CheckFailedException, GmailException {

        String regUsername = regBean.getUsernameBean();
        String regEmail = regBean.getEmailBean();
        String regPassword = regBean.getPasswordBean();
        String typeOfUser = regBean.getTypeOfUser();
        this.regAccess = new AccessThroughRegistration(regUsername, regEmail, regPassword, typeOfUser);
        this.regAccess.encodePassword();
        Access account = this.accessDAO.retrieveAccount(regAccess);
        this.regAccess.checkAccount(account);
        String messageToSend = this.regAccess.getMessageToSend();
        this.googleBoundary = new GoogleBoundary();
        this.googleBoundary.sendMail("check email", messageToSend, regEmail);
    }

    public void checkCode(RegistrationBean regBean) throws CheckFailedException, PersistencyErrorException{
        this.regAccess.checkCode(regBean.getCheckCode());
        this.accessDAO.saveAccount(this.regAccess);
        this.user = new User(regBean.getUsernameBean(), regBean.getEmailBean(), regBean.getTypeOfUser());
    }


}
