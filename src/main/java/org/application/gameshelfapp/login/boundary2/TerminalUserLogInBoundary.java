package org.application.gameshelfapp.login.boundary2;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.adapter.LogIn;
import org.application.gameshelfapp.login.boundary2.adapter.LogInAdapter;
import org.application.gameshelfapp.login.exception.*;

public class TerminalUserLogInBoundary implements TerminalBoundary{
    LogIn logInAdapter;
    public TerminalUserLogInBoundary(){
        this.logInAdapter = new LogInAdapter();
    }
    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException, GmailException, ArrayIndexOutOfBoundsException{
        if (command[0].equals("login")) {
            return this.logInAdapter.logIn(command[1], command[2]);
        }
        return "You inserted the wrong command.\n";
    }

    @Override
    public UserBean getUserBean(){
        return this.logInAdapter.getUserBean();
    }
}
