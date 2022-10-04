package com.kainos.ea.dao;

import com.kainos.ea.models.Training;
import com.kainos.ea.utils.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrainingLevel {

    public List<Training> getTraining(int bandID) throws SQLException{

        List<Training> trainingList = new ArrayList();
        ResultSet resultSet = null;

        try{
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection myConnection = dataBaseConnection.getConnection();
            Statement st = myConnection.createStatement();
            resultSet = st.executeQuery("SELECT bandName, bandTrainings.trainingName, trainingDate, durationHours, registration " +
                    "FROM band JOIN bandTrainings JOIN trainings WHERE band.bandID=bandTrainings.bandID AND" +
                    " bandTrainings.trainingName=trainings.trainingName AND bandTrainings.bandID = " + bandID);

            while (resultSet.next()) {
                Training training = Training.builder()
                        .bandName(resultSet.getString("bandName"))
                        .trainingName(resultSet.getString("trainingName"))
                        .trainingDate(resultSet.getString("trainingDate"))
                        .durationHours(resultSet.getString("durationHours"))
                        .registration(resultSet.getString("registration"))
                        .build();

                trainingList.add(training);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trainingList;
    }
}
