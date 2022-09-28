package com.kainos.ea.resources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.kainos.ea.WebServiceApplication.getConnection;

public class Capabilities {
    private static Connection myConnection;

    public static List<Capability> getCapabilities(){
        List<Capability> capList = new ArrayList();
        ResultSet resultSet = null;

        try{
            Connection myConnection = getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("select roleName, capabilityName from jobRoles join capabilities where jobRoles.jobRoleID=capabilities.jobRoleID");

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
