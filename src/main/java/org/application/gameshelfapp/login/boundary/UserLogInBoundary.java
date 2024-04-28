package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.exception.*;

public class UserLogInBoundary {

    private LogInBean logBean = null;
    private RegistrationBean regBean = null;
    private UserBean usrBean = null;
    private final LogInController controller;

    public UserLogInBoundary() throws PersistencyErrorException{
        this.controller = new LogInController();
    }

    public void log(String email, String password) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException, NullPasswordException {
        this.logBean = new LogInBean();
        this.logBean.setEmailBean(email);
        this.logBean.setPasswordBean(password);
        this.controller.logIn(logBean);
        this.usrBean = this.controller.getUser();
    }

    public void register(String username, String email, String password, String typeOfUser) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException, GmailException, NullPasswordException {

        this.regBean = new RegistrationBean();
        this.regBean.setUsernameBean(username);
        this.regBean.setEmailBean(email);
        this.regBean.setPasswordBean(password);
        this.regBean.setTypeOfUser(typeOfUser);
        this.controller.registration(this.regBean);

    }

    public void checkCode(String code) throws NumberFormatException, CheckFailedException, PersistencyErrorException{

        int insertedCode = Integer.parseInt(code);
        this.regBean.setCheckCode(insertedCode);
        this.controller.checkCode(this.regBean);
        this.usrBean = this.controller.getUser();
    }

    public UserBean getUserBean(){
        return this.usrBean;
    }

    public LogInController getController(){
        return this.controller;
    }

}
