package org.application.gameshelfapp.login.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;

public abstract class LogIn {

    protected UserBean userBean;
    public UserBean getUserBean(){
        return this.userBean;
    }
    public abstract String logIn(String email, String password) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException;
    public abstract String register(String username, String email, String password) throws PersistencyErrorException, CheckFailedException, GmailException, SyntaxErrorException, NullPasswordException;
    public abstract String checkCode(String code) throws PersistencyErrorException, CheckFailedException;
}
