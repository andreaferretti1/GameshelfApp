package org.application.gameshelfapp.login.boundary2.adapter;


import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.*;

public class LogInAdapter implements LogIn {

    UserLogInBoundary boundary;
    public LogInAdapter() {
        this.boundary = new UserLogInBoundary();
    }

    @Override
    public String logIn(String email, String password) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException {
        this.boundary.log(email, password);
        return "You're logged.\n\nType <buy>\n";
    }
    @Override
    public UserBean getUserBean(){
        return this.boundary.getUserBean();
    }
}
