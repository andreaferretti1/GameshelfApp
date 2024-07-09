package org.application.gameshelfapp.signemployee.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.signemployee.boundary.AdminBoundary;

public class SignEmployeeAdapter implements SignEmployee{
    private  final AdminBoundary boundary;
    public SignEmployeeAdapter(UserBean userBean) throws WrongUserTypeException { this.boundary = new AdminBoundary(userBean);}
    @Override
    public String register(String userName, String email, String password, String type) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException {
        this.boundary.register(userName, email, password, type);
        return "Successfully registered in the system\n";
    }
    @Override
    public UserBean getUserBean(){ return this.boundary.getUserBean();}
}
