package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.exception.*;

public class UserLogInBoundary {
    private RegistrationBean regBean;
    private UserBean userBean;
    private final LogInController controller;

    public UserLogInBoundary(){
        this.controller = new LogInController();
    }

    public void log(String email, String password) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException, NullPasswordException {
        LogInBean logBean = new LogInBean();
        logBean.setEmailBean(email);
        logBean.setPasswordBean(password);
        this.userBean = this.controller.logIn(logBean);
        this.userBean.getDataFromModel();
    }

    public void register(String username, String email, String password) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException, GmailException, NullPasswordException {
        this.regBean = new RegistrationBean();
        this.regBean.setUsernameBean(username);
        this.regBean.setEmailBean(email);
        this.regBean.setPasswordBean(password);
        this.controller.registration(this.regBean);
    }

    public void checkCode(String code) throws NumberFormatException, CheckFailedException, PersistencyErrorException{
        int insertedCode = Integer.parseInt(code);
        this.regBean.setCheckCode(insertedCode);
        this.userBean = this.controller.checkCode(this.regBean);
        this.userBean.getDataFromModel();
    }

    public UserBean getUserBean(){
        return this.userBean;
    }

    public LogInController getController(){
        return this.controller;
    }

}
