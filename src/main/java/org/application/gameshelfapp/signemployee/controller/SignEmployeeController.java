package org.application.gameshelfapp.signemployee.controller;

import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class SignEmployeeController {
    public void registerEmployee(RegistrationBean regBean) throws NullPasswordException, PersistencyErrorException, CheckFailedException {
        AccessThroughRegistration regAccess = new AccessThroughRegistration(regBean.getUsernameBean(), regBean.getEmailBean(), regBean.getPasswordBean(), regBean.getTypeOfUser());
        regAccess.encodePassword();
        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();
        accessDAO.checkAccount(regAccess);
        accessDAO.saveAccount(regAccess);
    }
}
