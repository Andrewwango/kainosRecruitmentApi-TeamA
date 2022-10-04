package com.kainos.ea.dao;

import com.kainos.ea.models.Training;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TrainingLevelTest {
    TrainingLevel training = Mockito.mock(TrainingLevel.class);
    DataBaseConnection databaseConnector = Mockito.mock(DataBaseConnection.class);
    Connection conn;
    private TrainingLevel trainingLevel;

    @Test
    void getTraining_shouldReturnAList() throws SQLException {
        List<Training> expectedResult = new ArrayList<>();
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(training.getTraining(1)).thenReturn(expectedResult);

        List<Training> result = training.getTraining(1);

        assertEquals(result, expectedResult);
    }
    @Test
    void getTraining_shouldThrowSQLException_whenProvoked() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(training.getTraining(1)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> training.getTraining(1));
    }
}