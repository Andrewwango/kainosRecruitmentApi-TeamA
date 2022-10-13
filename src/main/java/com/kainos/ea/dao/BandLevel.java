package com.kainos.ea.dao;

import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.models.Band;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BandLevel {

    private static Connection myConnection;
    
    public List<Band> getBand() throws SQLException{
        List<Band> baseList = new ArrayList();
        ResultSet resultSet = null;

        try{
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("SELECT jobRoles.bandID, roleName, bandName FROM jobRoles JOIN band WHERE jobRoles.bandID = band.bandID;");

            while (resultSet.next()) {
               Band dataBaseBand = Band.builder()
                       .bandID(resultSet.getInt("bandID"))
                   .bandName(resultSet.getString("bandName"))
                   .roleName(resultSet.getString("roleName"))
                   .build();
                baseList.add(dataBaseBand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baseList;
    }

    public List<Band> getBandNames() throws SQLException{
        List<Band> baseList = new ArrayList();
        ResultSet resultSet = null;

        try{
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("SELECT DISTINCT * FROM band;");

            while (resultSet.next()) {
                Band dataBaseBand = Band.builder()
                        .bandID(resultSet.getInt("bandID"))
                        .bandName(resultSet.getString("bandName"))
                        .build();
                baseList.add(dataBaseBand);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baseList;
    }
}
