package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.buyvideogames.entities.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.sql.*;

public class AccessDAOJDBC implements AccessDAO{


    @Override
    public void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException {
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = String.format("INSERT INTO User (Username, Email, Password, Type) VALUES (%s, %s, %s, %s);", regAccess.getUsername(), regAccess.getEmail(), regAccess.getEncodedPassword(), regAccess.getTypeOfUser());
            stmt.execute(query);
       } catch (SQLException e) {
            throw new PersistencyErrorException("Couldn't access to accounts.");
       }
    }

    @Override
    public Access retrieveAccount(Access access) throws PersistencyErrorException {
        Access checkAccess = null;
        try(Connection conn = SingletonConnectionPool.getInstance().getConnection();
            Statement stmt = conn.createStatement()){
            String query = "SELECT * FROM User WHERE Email = " + access.getEmail() + "OR Username = " + access.getEmail() + ";";
            boolean executed = stmt.execute(query);
            if(executed) {
                ResultSet rs = stmt.getResultSet();
                checkAccess = new Access(rs.getString("Username"), rs.getString("Email"), null, rs.getString("Type"));
                access.setEncodedPassword(rs.getString("password"));
                rs.close();
            }
        } catch(SQLException e){
            throw new PersistencyErrorException("Culdn't access to accounts.");
        }
        return checkAccess;
    }
}
