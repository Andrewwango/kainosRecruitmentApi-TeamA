package com.kainos.ea.dao;

import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.models.JobRole;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpecificationLevel {

    public static JobRole getJobRole(int jobRoleId){

        ResultSet resultSet;

        try{
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();

            resultSet = st.executeQuery("SELECT * FROM jobRoles WHERE jobRoleID = " + jobRoleId + ";");

            while (resultSet.next()) {
                JobRole jobRole = JobRole.builder()
                    .jobRoleId(resultSet.getInt("jobRoleID"))
                    .roleName(resultSet.getString("roleName"))
                    .specification(resultSet.getString("specification"))
                    .link(resultSet.getString("link"))
                    .bandID(resultSet.getInt("bandID"))
                    .capabilityID(resultSet.getInt("capabilityID"))
                    .build();

                jobRole.setJobRoleId(jobRoleId);
                return jobRole;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}