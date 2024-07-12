package org.application.gameshelfapp.signemployee.boundary2;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.signemployee.boundary2.adapter.SignEmployee;
import org.application.gameshelfapp.signemployee.boundary2.adapter.SignEmployeeAdapter;

public class TerminalAdminBoundary implements TerminalBoundary {
    SignEmployee signEmployeeAdapter;
    public static final String START_COMMAND = "\nType <sign employee, username, email, password, Seller/Admin>";
    public TerminalAdminBoundary(UserBean userBean) throws WrongUserTypeException { this.signEmployeeAdapter = new SignEmployeeAdapter(userBean);}
    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException {
        if (command[0].equals("sign employee")){
            return this.signEmployeeAdapter.register(command[1],command[2],command[3],command[4]);
        } else {
            return "\nIncorrect command\n\n Type <sign employee, userName, email, password, Seller/Admin";
        }
    }

    @Override
    public UserBean getUserBean(){return this.signEmployeeAdapter.getUserBean(); }
}
