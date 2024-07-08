package org.application.gameshelfapp.sellvideogames.dao.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsoleDAOQueries {
    private ConsoleDAOQueries(){}
    public static ResultSet getConsolesQuery(Connection conn) throws SQLException{
        String query = "SELECT Console FROM Console;";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.execute();
        return pstmt.getResultSet();
    }
}
