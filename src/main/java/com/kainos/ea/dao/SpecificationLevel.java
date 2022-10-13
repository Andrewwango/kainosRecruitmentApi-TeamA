package com.kainos.ea.dao;

import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.models.JobRole;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpecificationLevel {
    public static JobRole getJobRole(int jobRoleId) throws SQLException {
        JobRole jobRole = null;
      
        try {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * FROM jobRoles WHERE jobRoleID = " + jobRoleId + ";");

            while (rs.next()) {
                JobRole job = JobRole.builder()
                        .jobRoleId(rs.getInt("jobRoleID"))
                        .roleName(rs.getString("roleName"))
                        .specification(rs.getString("specification"))
                        .link(rs.getString("link"))
                        .bandID(rs.getInt("bandID"))
                        .capabilityID(rs.getInt("capabilityID"))
                        .responsibility(rs.getString("responsibility"))
                        .build();
                jobRole = job;
            }
        } catch (SQLException e) {
            throw e;
        }
        return jobRole;
    }
}