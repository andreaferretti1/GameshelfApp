package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class AccessDAOJDBC implements AccessDAO{


    @Override
    public void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException {

    }

    @Override
    public Access retrieveAccount(Access access) throws PersistencyErrorException {
        return null;
    }
}
