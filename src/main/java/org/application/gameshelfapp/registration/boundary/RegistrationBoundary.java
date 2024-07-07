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

    public void checkCode(String code) throws NumberFormatException, CheckFailedException, PersistencyErrorException{
        int insertedCode = Integer.parseInt(code);
        this.userBean = this.controller.checkCode(insertedCode);
        this.userBean.getDataFromModel();
    }

    public UserBean getUserBean(){
        return this.userBean;
    }
}
