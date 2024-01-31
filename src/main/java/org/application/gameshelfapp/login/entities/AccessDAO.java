package org.application.gameshelfapp.login.entities;


import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public interface AccessDAO {
    void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException;
    Access retrieveAccount(Access access) throws PersistencyErrorException;

}
