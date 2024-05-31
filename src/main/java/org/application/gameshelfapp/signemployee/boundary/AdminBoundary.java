package org.application.gameshelfapp.signemployee.boundary;

import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.signemployee.controller.SignEmployeeController;

public class AdminBoundary {
    SignEmployeeController controller;

    public AdminBoundary(){
        this.controller = new SignEmployeeController();
    }
    public void register(RegistrationBean regBean) throws PersistencyErrorException, CheckFailedException, NullPasswordException {
        this.controller.registerEmployee(regBean);
    }
}
