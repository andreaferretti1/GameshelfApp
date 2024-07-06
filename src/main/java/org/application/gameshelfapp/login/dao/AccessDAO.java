package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface AccessDAO {
    void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException;
    Access retrieveAccountByEmail(Access access) throws PersistencyErrorException;
    void checkAccount(AccessThroughRegistration access) throws PersistencyErrorException, CheckFailedException;
    List<Access> getRandomCustomers() throws PersistencyErrorException;
}
