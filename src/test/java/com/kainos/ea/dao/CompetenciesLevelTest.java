package com.kainos.ea.dao;

import com.kainos.ea.exception.DatabaseConnectionException;
import com.kainos.ea.models.Capability;
import com.kainos.ea.models.Competencies;
import com.kainos.ea.models.JobRole;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class CompetenciesLevelTest {
    CompetenciesLevel comp = Mockito.mock(CompetenciesLevel.class);
    DataBaseConnection databaseConnector = Mockito.mock(DataBaseConnection.class);
    Connection conn;
    private CompetenciesLevel competenciesLevel;

    @Test
    void getCompetencies_shouldReturnAList() throws SQLException {
        int id = 4;
        List<Competencies> expectedResult = new ArrayList<>();
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(comp.getCompetencies(id)).thenReturn(expectedResult);

        List<Competencies> result = comp.getCompetencies(id);

        assertEquals(result, expectedResult);
    }
    @Test
    void getCompetencies_shouldThrowSQLException_whenProvoked() throws SQLException {
        int id = 4;
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(comp.getCompetencies(id)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> comp.getCompetencies(id));
    }
}
