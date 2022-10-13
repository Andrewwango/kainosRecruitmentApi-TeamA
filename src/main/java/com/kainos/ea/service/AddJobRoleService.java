package com.kainos.ea.service;

import com.kainos.ea.dao.AddJobRoleLevel;
import com.kainos.ea.exception.InvalidJobRoleException;
import com.kainos.ea.models.AddJobRole;
import com.kainos.ea.models.JobRole;
import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.validator.JobRoleValidator;

import java.sql.SQLException;

public class AddJobRoleService {
    private DataBaseConnection databaseConnector;
    private JobRoleValidator addJobRoleValidator;
    public AddJobRoleLevel addJobRoleLevel;

    public AddJobRoleService(DataBaseConnection databaseConnector, AddJobRoleLevel addJobRoleLevel) {
        this.databaseConnector = databaseConnector;
        this.addJobRoleLevel = addJobRoleLevel;
    }


    public int addJobRole(AddJobRole jobRole) throws SQLException, InvalidJobRoleException {
        if (addJobRoleValidator.isValidJobRole(jobRole)) {
            return addJobRoleLevel.addJobRole(jobRole, databaseConnector.getConnection());
        }
        throw new InvalidJobRoleException("Invalid job role");
    }
}
