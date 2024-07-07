package org.application.gameshelfapp.registration.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;

public interface Registration {
    String register(String username, String email, String password) throws PersistencyErrorException, CheckFailedException, GmailException, SyntaxErrorException, NullPasswordException;
    String checkCode(String code) throws PersistencyErrorException, CheckFailedException;
    UserBean getUserBean();
}
