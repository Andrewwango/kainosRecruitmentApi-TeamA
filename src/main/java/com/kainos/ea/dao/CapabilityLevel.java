package com.kainos.ea.dao;

import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.models.Capability;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CapabilityLevel {

    public List<Capability> getCapabilities() throws SQLException{
        List<Capability> capList = new ArrayList();
        ResultSet resultSet = null;

        try{
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("SELECT roleName, capabilityName FROM jobRoles JOIN capabilities WHERE jobRoles.capabilityID=capabilities.capabilityID");

            while (resultSet.next()) {
                Capability cap = Capability.builder()
                    .capabilityName(resultSet.getString("capabilityName"))
                    .roleName(resultSet.getString("roleName"))
                    .build();

                capList.add(cap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return capList;
    }
}
