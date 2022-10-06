package com.kainos.ea.dao;

import com.kainos.ea.models.Band;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BandLevelTest {
    DataBaseConnection dataBaseConnector = Mockito.mock(DataBaseConnection.class);

    BandLevel bandLevel = Mockito.mock(BandLevel.class);

    Connection myConnection;

    @Test
    public void getBandLevelFromDatabaseShouldReturnValuesAsDatabaseConnectionIsOk() throws SQLException {
        List<Band> expectedResult = new ArrayList();
        Mockito.when(bandLevel.getBand()).thenReturn(expectedResult);
        List<Band> result = bandLevel.getBand();

        assertEquals(expectedResult,result);

    }

    @Test
    public void getBandLevelFromDatabaseShouldThrowSQLException() throws SQLException {
        Mockito.when(dataBaseConnector.getConnection()).thenReturn(myConnection);
        Mockito.when(bandLevel.getBand()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> bandLevel.getBand());

    }

    @Test
    void capabilityLevel_ReturnsListOfBandNames_WhenCalledGetBandNames() throws SQLException {
        //Given
        bandLevel = new BandLevel();
        List<Band> result = new ArrayList<>();

        //When
        result = bandLevel.getBandNames();

        //Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(6);
        assertThat(result).hasAtLeastOneElementOfType(Band.class);
    }
}
