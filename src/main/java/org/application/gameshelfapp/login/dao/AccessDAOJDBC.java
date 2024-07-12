package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.dao.queries.AccessDAOQueries;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessDAOJDBC implements AccessDAO {

    @Override
    public void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException {
        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            AccessDAOQueries.insertAccountQuery(conn, regAccess);
            SingletonConnectionPool.getInstance().releaseConnection(conn);
       } catch (SQLException e) {
            throw new PersistencyErrorException("Couldn't access to accounts.");
       }
    }

    @Override
    public Access retrieveAccountByEmail(Access access) throws PersistencyErrorException {
        Access checkAccess = null;

        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            ResultSet rs = AccessDAOQueries.getAccountQuery(conn, access);
            while(rs.next()) {
                checkAccess = new AccessThroughRegistration(rs.getString("Username"), rs.getString("Email"), null, rs.getString("Type"));
                checkAccess.setEncodedPassword(rs.getString("password"));
            }
            rs.close();
            SingletonConnectionPool.getInstance().releaseConnection(conn);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't access to accounts.");
        }
        return checkAccess;
    }

    @Override
    public void checkAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException, CheckFailedException {

        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            ResultSet rs = AccessDAOQueries.checkAccountQuery(conn, regAccess);
            if(rs.next()) throw new CheckFailedException("Username or email already exist");
            SingletonConnectionPool.getInstance().releaseConnection(conn);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't register your account");
        }
    }

    @Override
    public List<Access> getRandomCustomers() throws PersistencyErrorException {
        List<Access> winners = new ArrayList<>();
        try{
            Connection conn = SingletonConnectionPool.getInstance().getConnection();
            ResultSet rs = AccessDAOQueries.getRandomCustomersQuery(conn);
            while(rs.next()){
                Access user = new AccessThroughLogIn(rs.getString("Email"), null, "Customer");
                winners.add(user);
            }
            SingletonConnectionPool.getInstance().releaseConnection(conn);
        } catch(SQLException e){
            throw new PersistencyErrorException("Couldn't get account to send email");
        }
        return winners;
    }
}
