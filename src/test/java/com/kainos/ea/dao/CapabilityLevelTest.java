package com.kainos.ea.dao;

import com.kainos.ea.models.Capability;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CapabilityLevelTest {
    CapabilityLevel cap = Mockito.mock(CapabilityLevel.class);
    DataBaseConnection databaseConnector = Mockito.mock(DataBaseConnection.class);
    Connection conn;
    private CapabilityLevel capabilityLevel;

    @Test
    void getCapabilities_shouldReturnAList() throws SQLException {
        List<Capability> expectedResult = new ArrayList<>();
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(cap.getCapabilities()).thenReturn(expectedResult);

        List<Capability> result = cap.getCapabilities();

        assertEquals(result, expectedResult);
    }
    @Test
    void getCapabilities_shouldThrowSQLException_whenProvoked() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(cap.getCapabilities()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> cap.getCapabilities());
    }

    @Test
    void getCapabilitiesNames_shouldReturnAList() throws SQLException {
        List<Capability> expectedResult = new ArrayList<>();
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(cap.getCapabilitiesNames()).thenReturn(expectedResult);

        List<Capability> result = cap.getCapabilitiesNames();

        assertEquals(result, expectedResult);
    }
    @Test
    void getCapabilitiesNames_shouldThrowSQLException_whenProvoked() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(cap.getCapabilitiesNames()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> cap.getCapabilitiesNames());
    }
}