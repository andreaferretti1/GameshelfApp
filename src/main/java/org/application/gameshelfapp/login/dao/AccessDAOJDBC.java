package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.dao.queries.AccessDAOQueries;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.*;

public class AccessDAOJDBC implements AccessDAO {

    @Override
    public void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException {
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            AccessDAOQueries.insertAccountQuery(conn, regAccess);
       } catch (SQLException e) {
            throw new PersistencyErrorException("Couldn't access to accounts.");
       }
    }

    @Override
    public Access retrieveAccountByEmail(Access access) throws PersistencyErrorException {
        Access checkAccess;

        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){

                ResultSet rs = AccessDAOQueries.getAccountQuery(conn, access);
                checkAccess = new AccessThroughLogIn(rs.getString("Username"), rs.getString("Email"), rs.getString("Type"));
                checkAccess.setEncodedPassword(rs.getString("password"));
                rs.close();
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't access to accounts.");
        }
        return checkAccess;
    }

    @Override
    public void checkAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException, CheckFailedException {

        try(Connection conn = SingletonConnectionPool.getInstance().getConnection()){
            ResultSet rs = AccessDAOQueries.checkAccountQuery(conn, regAccess);
            if(rs.next()) throw new CheckFailedException("Username or email already exist");
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't register your account");
        }
    }
}
