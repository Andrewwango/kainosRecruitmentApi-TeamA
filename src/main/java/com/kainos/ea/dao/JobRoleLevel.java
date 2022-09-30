package com.kainos.ea.dao;

import com.kainos.ea.models.JobRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.kainos.ea.database.DataBaseConnection.getConnection;

public class JobRoleLevel {
    public static List<JobRole> getJobRoles() throws SQLException {
        List<JobRole> jobs = new ArrayList<>();
        try {
            Connection myConnection = getConnection();
            Statement st = myConnection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " + "FROM jobRoles;");
            while (rs.next()) {
                JobRole job = new JobRole(
                        rs.getInt("jobRoleID"),
                        rs.getString("roleName"),
                        rs.getString("specification"),
                        rs.getString("link"),
                        rs.getInt("bandID"),
                        rs.getInt("capabilityID")
                );
                jobs.add(job);
            }
        } catch (SQLException e) {
            throw e;
        }
        return jobs;
    }
}