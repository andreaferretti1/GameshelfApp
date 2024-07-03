package org.application.gameshelfapp.signemployee.boundary;

import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.signemployee.controller.SignEmployeeController;

public class AdminBoundary {
    SignEmployeeController controller;
    UserBean userBean;
    public AdminBoundary(){
        this.controller = new SignEmployeeController();
    }

    public UserBean getUserBean(){
        return this.userBean;
    }

    public void setUserBean(UserBean userBean){
        this.userBean = userBean;
    }

    public void register(String usernameBean, String emailBean, String passwordBean, String typeOfCustomerBean) throws PersistencyErrorException, CheckFailedException, NullPasswordException, SyntaxErrorException {
        RegistrationBean registrationBean = new RegistrationBean();
        registrationBean.setUsernameBean(usernameBean);
        registrationBean.setEmailBean(emailBean);
        registrationBean.setPasswordBean(passwordBean);
        registrationBean.setTypeOfUser(typeOfCustomerBean);
        this.controller.registerEmployee(registrationBean);
    }
}
