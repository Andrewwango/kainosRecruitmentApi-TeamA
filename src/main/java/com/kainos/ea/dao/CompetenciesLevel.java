package com.kainos.ea.dao;

import com.kainos.ea.models.Competencies;
import com.kainos.ea.models.JobRole;
import com.kainos.ea.test.utils.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompetenciesLevel {
    public List<Competencies> getCompetencies(int bandID) throws SQLException {
        List<Competencies> compList = new ArrayList();
        ResultSet resultSet = null;

        try {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT category, description FROM band JOIN competencies WHERE band.bandID=competencies.bandID and band.bandID = " + bandID + ";");

            while (rs.next()) {
                Competencies comp = Competencies.builder()
                        .category(rs.getString("category"))
                        .description(rs.getString("description"))
                        .build();
                compList.add(comp);
            }
        } catch (SQLException e) {
            throw e;
        }
        return compList;
    }
}

