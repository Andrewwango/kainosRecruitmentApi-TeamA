package com.kainos.ea.service;

import com.kainos.ea.dao.BandLevel;
import com.kainos.ea.database.DataBaseConnection;
import com.kainos.ea.models.Band;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BandLevelTest {

    DataBaseConnection dataBaseConnector = Mockito.mock(DataBaseConnection.class);

    BandLevel bandLevel = Mockito.mock(BandLevel.class);

    Connection myConnection;

    @Test
    public void getBandLevelFromDatabaseShouldReturnValuesAsDatabaseConnectionIsOk(){
        Band band = new Band("Workday Consultant","Consultant");
        List<Band> expectedResult = new ArrayList();
        expectedResult.add(band);
        Mockito.when(dataBaseConnector.getConnection()).thenReturn(myConnection);
        Mockito.when(bandLevel.getBand()).thenReturn(expectedResult);

        List<Band> result = bandLevel.getBand();

        assertEquals(expectedResult,result);

    }

    @Test
    public void getBandLevelFromDatabaseShouldThrowSQLException(){

        Mockito.when(dataBaseConnector.getConnection()).thenReturn(myConnection);
        Mockito.when(bandLevel.getBand()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> bandLevel.getBand());

    }




}
