package org.application.gameshelfapp.login.boundary2.adapter;


import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.exception.*;

public class LogInAdapter extends LogIn {

    UserLogInBoundary boundary;
    public LogInAdapter() {
        this.boundary = new UserLogInBoundary();
    }

    @Override
    public String logIn(String email, String password) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException {
        this.boundary.log(email, password);
        this.userBean = this.boundary.getUserBean();
        return "You're logged.\n\nType <buy>\n";
    }

    @Override
    public String register(String username, String email, String password) throws PersistencyErrorException, CheckFailedException, GmailException, SyntaxErrorException, NullPasswordException {
        this.boundary.register(username, email, password);
        return "Please check your email and insert the code we sent you through email typing 'code <codeSent>'\n";
    }

    @Override
    public String checkCode(String code) throws PersistencyErrorException, CheckFailedException {
        this.boundary.checkCode(code);
        this.userBean = this.boundary.getUserBean();
        return "You're registered.\n\nType <buy>\n";
    }

    public LogInController getController(){
        return this.boundary.getController();
    }
}
