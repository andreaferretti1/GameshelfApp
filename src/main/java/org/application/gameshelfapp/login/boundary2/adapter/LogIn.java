package org.application.gameshelfapp.login.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;

public interface LogIn {

    UserBean getUserBean();
    String logIn(String email, String password) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException;
}
