package org.application.gameshelfapp.login.entities;


import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public interface AccessDAO {
    void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException;
    Access retrieveAccountByEmail(Access access) throws PersistencyErrorException;
    void checkAccount(AccessThroughRegistration access) throws PersistencyErrorException, CheckFailedException;

}
