package com.kainos.ea.dao;

import com.kainos.ea.database.DataBaseConnection;
import com.kainos.ea.models.JobRole;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpecificationLevel {

    public static JobRole getJobRole(int jobRoleId){

        ResultSet resultSet;

        try{
            DataBaseConnection databaseConnecter = new DataBaseConnection();
            Connection myConnection = databaseConnecter.getConnection();
            Statement st = myConnection.createStatement();

            resultSet = st.executeQuery("SELECT * FROM jobRoles WHERE jobRoleID = " + jobRoleId + ";");

            while (resultSet.next()) {
                JobRole jobRole = new JobRole(
                        resultSet.getString("roleName"),
                        resultSet.getString("specification"),
                        resultSet.getString("link")
                );
                jobRole.setJobRoleId(jobRoleId);
                return jobRole;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}