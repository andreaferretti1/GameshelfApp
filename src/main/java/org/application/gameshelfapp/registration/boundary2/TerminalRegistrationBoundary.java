package org.application.gameshelfapp.registration.boundary2;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.registration.boundary2.adapter.Registration;
import org.application.gameshelfapp.registration.boundary2.adapter.RegistrationAdapter;

public class TerminalRegistrationBoundary implements TerminalBoundary {

    private final Registration adapter;
    public static final String START_COMMAND = "Type <register, username, email, password>";
    public TerminalRegistrationBoundary(){
        this.adapter = new RegistrationAdapter();
    }

    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException, GmailException, ArrayIndexOutOfBoundsException{
        switch(command[0]){
            case "register" -> {
                return this.adapter.register(command[1], command[2], command[3]);
            }
            case "code" -> {
                return this.adapter.checkCode(command[1]);
            }
            default -> {
                return "You inserted the wrong command.\n";
            }
        }
    }

    @Override
    public UserBean getUserBean(){
        return this.adapter.getUserBean();
    }
}
