package org.application.gameshelfapp.registration.controller;

import org.application.gameshelfapp.buyvideogames.entities.User;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.registration.bean.RegistrationBean;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;

public class RegistrationController {
    private AccessThroughRegistration regAccess;
    public void setRegAccess(AccessThroughRegistration regAccess){
        this.regAccess = regAccess;
    }
    public AccessThroughRegistration getRegAccess(){
        return this.regAccess;
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
