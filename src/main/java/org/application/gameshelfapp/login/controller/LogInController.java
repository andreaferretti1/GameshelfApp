package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.buyvideogames.entities.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.User;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.entities.*;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;



public class LogInController {

    private User user;
    private AccessThroughRegistration regAccess;
    private AccessThroughLogIn logAccess;
    PersistencyAbstractFactory factory;
    private AccessDAO accessDAO;

    public LogInController() throws PersistencyErrorException{

        this.factory = PersistencyAbstractFactory.getFactory();
        this.accessDAO = this.factory.createAccessDAO();
    }


    public void logIn(LogInBean logBean) throws PersistencyErrorException, CheckFailedException{


        String logEmail = logBean.getEmailBean();
        String logPassword = logBean.getPasswordBean();

        this.logAccess = new AccessThroughLogIn(logEmail, logPassword, null);
        this.logAccess.encodePassword();


        Access access = this.accessDAO.retrieveAccount(logAccess);
        this.logAccess.checkAccount(access);
        this.user = new User(access.getUsername(), access.getEmail(), access.getTypeOfUser());

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
        GoogleBoundary googleBoundary = new GoogleBoundary();
        googleBoundary.sendMail("check email", messageToSend, regEmail);
    }

    public void checkCode(RegistrationBean regBean) throws CheckFailedException, PersistencyErrorException{
        this.regAccess.checkCode(regBean.getCheckCode());
        this.accessDAO.saveAccount(this.regAccess);
        this.user = new User(regAccess.getUsername(), regAccess.getEmail(), regAccess.getTypeOfUser());
    }

    public UserBean getUser(){
        return new UserBean(this.user.getUsername(), this.user.getEmail(), this.user.getTypeOfUser());
    }


}
