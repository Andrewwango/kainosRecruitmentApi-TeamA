package com.kainos.ea.dao;

import com.kainos.ea.models.Capability;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static com.kainos.ea.database.DataBaseConnection.getConnection;

public class CapabilityLevel {

    private static Connection myConnection;

    public static List<Capability> getCapabilities(){
        List<Capability> capList = new ArrayList();
        ResultSet resultSet = null;

        try{
            Connection myConnection = getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("SELECT roleName, capabilityName FROM jobRoles JOIN capabilities WHERE jobRoles.capabilityID=capabilities.capabilityID");

            while (resultSet.next()) {
                Capability cap = new Capability(
                        resultSet.getString("roleName"),
                        resultSet.getString("capabilityName"));
                capList.add(cap);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return capList;
    }
}
