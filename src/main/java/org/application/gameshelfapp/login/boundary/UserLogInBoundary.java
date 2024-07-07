package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public class UserLogInBoundary {
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

    public UserBean getUserBean(){
        return this.userBean;
    }
    public LogInController getController(){
        return this.controller;
    }

}
