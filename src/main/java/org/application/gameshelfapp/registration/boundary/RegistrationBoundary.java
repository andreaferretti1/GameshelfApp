package org.application.gameshelfapp.registration.boundary;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.registration.bean.RegistrationBean;
import org.application.gameshelfapp.registration.controller.RegistrationController;

public class RegistrationBoundary {

    private UserBean userBean;
    private final RegistrationController controller;

    public RegistrationBoundary(){
        this.controller = new RegistrationController();
    }

    public void register(String username, String email, String password) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException, GmailException, NullPasswordException {
        RegistrationBean regBean = new RegistrationBean();
        regBean.setUsernameBean(username);
        regBean.setEmailBean(email);
        regBean.setPasswordBean(password);
        this.controller.registration(regBean);
    }

    public void checkCode(int code) throws NumberFormatException, CheckFailedException, PersistencyErrorException{
        this.userBean = this.controller.checkCode(code);
        this.userBean.getDataFromModel();
    }

    public UserBean getUserBean(){
        return this.userBean;
    }
}
