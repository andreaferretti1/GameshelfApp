package org.application.gameshelfapp.login.boundary2.adapter;


import org.application.gameshelfapp.buyvideogames.boundary2.TerminalCustomerBoundary;
import org.application.gameshelfapp.confirmsale.boundary2.TerminalSellerBoundary;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.boundary2.TerminalSellerAddGamesBoundary;

public class LogInAdapter implements LogIn {

    UserLogInBoundary boundary;
    public LogInAdapter() {
        this.boundary = new UserLogInBoundary();
    }

    @Override
    public String logIn(String email, String password) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException {
        this.boundary.log(email, password);
        return this.displayMessage();
    }
    @Override
    public UserBean getUserBean(){
        return this.boundary.getUserBean();
    }
    private String displayMessage(){
        switch(this.boundary.getUserBean().getTypeOfUser()){
            case "Customer" -> {
                return TerminalCustomerBoundary.START_COMMAND;
            }
            case "Seller", "Admin" -> {
                return TerminalSellerAddGamesBoundary.START_COMMAND + TerminalSellerBoundary.START_COMMAND;
            }
            default -> {
                return "\nWrong user type\n\n";
            }
        }
    }
}
