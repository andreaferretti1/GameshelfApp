package org.application.gameshelfapp.signemployee.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public interface SignEmployee {
    String register(String userName, String email,String password, String type) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException;
    UserBean getUserBean();
}
