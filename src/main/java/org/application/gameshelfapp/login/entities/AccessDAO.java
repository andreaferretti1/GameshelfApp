package org.application.gameshelfapp.login.entities;


import org.application.gameshelfapp.login.exception.PersistencyAccountException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public interface AccessDAO {
    void saveAccount(TypeOfAccess type, Access access) throws Exception;
    Access retrieveAccountByEmail(TypeOfAccess type, String email) throws PersistencyErrorException, PersistencyAccountException;

}
