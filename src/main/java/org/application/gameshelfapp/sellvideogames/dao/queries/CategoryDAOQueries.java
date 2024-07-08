package org.application.gameshelfapp.sellvideogames.dao.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAOQueries {
    private CategoryDAOQueries(){}
    public static ResultSet getCategoryQuery(Connection conn) throws SQLException {
        String query = "SELECT Type FROM Category;";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.execute();
        return pstmt.getResultSet();
    }
}
