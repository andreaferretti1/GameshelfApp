package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public class LogInBean extends AccessBean{

    public LogInBean(String email, String password) throws SyntaxErrorException {
        super(email, password);
    }

}
