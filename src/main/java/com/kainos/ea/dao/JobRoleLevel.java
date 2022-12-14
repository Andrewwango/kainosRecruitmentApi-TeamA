package com.kainos.ea.dao;

import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.models.JobRole;

import javax.ws.rs.core.Response;
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
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " + "FROM jobRoles;");
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

                jobs.add(job);
            }
        } catch (SQLException e) {
            throw e;
        }
        return jobs;
    }

    public static List<JobRole> getJobRolesByCapability(int capabilityID) throws SQLException {
        List<JobRole> jobs = new ArrayList<>();

        try {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT * " + "FROM jobRoles WHERE capabilityID = " + capabilityID + ";");
            while (rs.next()) {
                JobRole job = JobRole.builder()
                        .jobRoleId(rs.getInt("jobRoleID"))
                        .roleName(rs.getString("roleName"))
                        .specification(rs.getString("specification"))
                        .link(rs.getString("link"))
                        .bandID(rs.getInt("bandID"))
                        .capabilityID(rs.getInt("capabilityID"))
                        .build();

                jobs.add(job);
            }
        } catch (SQLException e) {
            throw e;
        }
        return jobs;
    }

    public Response deleteJobRoles(List<String> jobIDs) throws SQLException {
        try {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();

            for (int i = 0; i < jobIDs.size(); i++) {
                Integer res = st.executeUpdate(
                        "DELETE FROM jobRoles WHERE jobRoleID = " + jobIDs.get(i) + ";" );
            }

            return Response.status(200).build();
        } catch (SQLException e) {
            throw e;
        }
    }
}