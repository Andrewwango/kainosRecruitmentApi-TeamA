package com.kainos.ea.dao;

import com.kainos.ea.models.Band;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static com.kainos.ea.database.DataBaseConnection.getConnection;

public class BandLevel {

    private static Connection myConnection;
    
    public static List<Band> getBand(){
        List<Band> baseList = new ArrayList();
        ResultSet resultSet = null;

        try{
            Connection myConnection = getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("select roleName, bandName from jobRoles join band where jobRoles.jobRoleID=band.jobRoleID");

            while (resultSet.next()) {
               Band dataBaseBand = new Band(
                        resultSet.getString("roleName"), resultSet.getString("bandName"));
                baseList.add(dataBaseBand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baseList;
    }
}
