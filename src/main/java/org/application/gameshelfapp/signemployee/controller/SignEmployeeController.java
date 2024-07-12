package org.application.gameshelfapp.signemployee.controller;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.registration.bean.RegistrationBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class SignEmployeeController {

    public SignEmployeeController(UserBean userBean) throws WrongUserTypeException {
        if(userBean == null || !userBean.getTypeOfUser().equals("Admin")){
            throw new WrongUserTypeException("Only Admin users can access this operation\n");
        }
    }
    public void registerEmployee(RegistrationBean regBean) throws NullPasswordException, PersistencyErrorException, CheckFailedException {
        AccessThroughRegistration regAccess = new AccessThroughRegistration(regBean.getUsernameBean(), regBean.getEmailBean(), regBean.getPasswordBean(), null);
        regAccess.setUserType(regAccess.getTypeOfUser());
        regAccess.encodePassword();
        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
        accessDAO.checkAccount(regAccess);
        accessDAO.saveAccount(regAccess);
    }
}
