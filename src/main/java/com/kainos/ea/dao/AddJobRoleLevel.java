package com.kainos.ea.dao;

import com.kainos.ea.exception.InvalidJobRoleException;
import com.kainos.ea.models.AddJobRole;

import java.sql.*;

public class AddJobRoleLevel {
    public int addJobRole(AddJobRole jobRole, Connection c) throws SQLException, InvalidJobRoleException {
        int jobRoleID = -1;

        try {
            String addJobRoleQuery = "INSERT INTO jobRoles (roleName, specification, link, bandID, capabilityID, responsibility)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedSt = c.prepareStatement(addJobRoleQuery, Statement.RETURN_GENERATED_KEYS);
            preparedSt.setString(1, jobRole.getRoleName());
            preparedSt.setString(2, jobRole.getSpecification());
            preparedSt.setString(3, jobRole.getLink());
            preparedSt.setInt(4, jobRole.getBandID());
            preparedSt.setInt(5, jobRole.getCapabilityID());
            preparedSt.setString(6, jobRole.getResponsibility());


            int affectedRows = preparedSt.executeUpdate();

            try (ResultSet rs = preparedSt.getGeneratedKeys()) {
                if (rs.next()) {
                    jobRoleID = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return jobRoleID;
    }
}
