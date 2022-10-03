package com.kainos.ea.dao;

import com.kainos.ea.database.DataBaseConnection;
import com.kainos.ea.models.JobRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class JobRoleLevel {
    public static List<JobRole> getJobRoles() throws SQLException {
        List<JobRole> jobs = new ArrayList<>();
        try {
            DataBaseConnection databaseConnecter = new DataBaseConnection();
            Connection myConnection = databaseConnecter.getConnection();
            Statement st = myConnection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " + "FROM jobRoles;");
            while (rs.next()) {
                JobRole job = new JobRole(
                        rs.getString("roleName"),
                        rs.getString("specification"),
                        rs.getString("link")
                );
                jobs.add(job);
            }
        } catch (SQLException e) {
            throw e;
        }
        return jobs;
    }
}