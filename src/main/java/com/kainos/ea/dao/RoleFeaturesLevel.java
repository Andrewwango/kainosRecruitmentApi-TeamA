package com.kainos.ea.dao;

import com.kainos.ea.models.JobRole;
import com.kainos.ea.models.JobRoleWithoutLink;
import com.kainos.ea.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleFeaturesLevel {
    private static Connection myConnection;

    public String editJobRole(int jobRoleID,JobRoleWithoutLink jobRole) throws SQLException {
        try{
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            String sql = "update jobRoles " +
                    "set roleName=? ,specification= ? ," +
                    " bandID=?, capabilityID=?,responsibility=?" +
                    " where jobRoleID=?;";

            System.out.println(sql);
            System.out.println(jobRole.getRoleName());
            System.out.println(jobRole.getSpecification());
            System.out.println(jobRole.getResponsibility());
            System.out.println(jobRole.getCapabilityID());
            System.out.println(jobRole.getBandID());
            PreparedStatement statement = myConnection.prepareStatement(sql);
            statement.setString(1, jobRole.getRoleName());
            statement.setString(2, jobRole.getSpecification());
            statement.setInt(3, jobRole.getBandID());
            statement.setInt(4, jobRole.getCapabilityID());
            statement.setString(5, jobRole.getResponsibility());
            statement.setInt(6, jobRoleID);
            System.out.println(statement);
            statement.executeUpdate();
            return "Data updated successfully";

        } catch (SQLException e) {
            throw e;

        }
    }
}
