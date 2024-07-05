package org.application.gameshelfapp.login.dao.queries;

import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessDAOQueries {

    private AccessDAOQueries(){}
    public static void insertAccountQuery(Connection conn, AccessThroughRegistration regAccess) throws SQLException{
        String query = "INSERT INTO User (Username, Email, Password, Type) VALUES (?, ?, ?, ?);";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, regAccess.getUsername());
        pstmt.setString(2, regAccess.getEmail());
        pstmt.setString(3, regAccess.getEncodedPassword());
        pstmt.setString(4, regAccess.getTypeOfUser());

        pstmt.execute();
    }

    public static ResultSet getAccountQuery(Connection conn, Access access) throws SQLException{
        String query = "SELECT Username, Email, Password, Type FROM User WHERE Email = ?;";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, access.getEmail());

        pstmt.execute();
        return pstmt.getResultSet();
    }

    public static ResultSet checkAccountQuery(Connection conn, AccessThroughRegistration regAccess) throws SQLException{
        String query = "SELECT Username, Email FROM User WHERE Email = ? OR Username = ?;";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, regAccess.getUsername());
        pstmt.setString(2, regAccess.getEmail());

        pstmt.execute();
        return pstmt.getResultSet();
    }

    public static ResultSet getRandomCustomersQuery(Connection conn) throws SQLException{
        String query = "SELECT Email FROM User WHERE Type = Customer ORDER BY RAND() LIMIT 20;";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.execute();
        return pstmt.getResultSet();
    }
}
