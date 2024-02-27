package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public class UserLogInBoundary {

    private LogInBean logBean = null;
    private RegistrationBean regBean = null;
    private UserBean usrBean = null;
    private final LogInController controller;

    public UserLogInBoundary() throws PersistencyErrorException{
        this.controller = new LogInController();
    }

    public void log(String email, String password) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException {
        this.logBean = new LogInBean(email, password);
        this.controller.logIn(logBean);
        this.usrBean = this.controller.getUser();
    }

    public void register(String username, String email, String password, String typeOfUser) throws SyntaxErrorException, PersistencyErrorException, CheckFailedException, GmailException {

        this.regBean = new RegistrationBean(username, email, password, typeOfUser);
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

}
