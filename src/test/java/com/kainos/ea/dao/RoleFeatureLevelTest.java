package com.kainos.ea.dao;

import com.kainos.ea.models.JobRole;
import com.kainos.ea.models.JobRoleWithoutLink;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class RoleFeatureLevelTest {
    RoleFeaturesLevel roleFeaturesLevel = Mockito.mock(RoleFeaturesLevel.class);
    DataBaseConnection databaseConnector = Mockito.mock(DataBaseConnection.class);

    JobRoleWithoutLink jobRole = new JobRoleWithoutLink();
    Connection conn;
    RoleFeaturesLevel featuresLevel = new RoleFeaturesLevel();

    @Test
    public void editJobROle_shouldReturnAStringIfSuccessful() throws SQLException {
        String  expectedResult = "Data updated successfully";
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        jobRole.setBandID(5);
        jobRole.setCapabilityID(1);
        jobRole.setSpecification("As a Data Analyst (Associate) in Kainos, you’ll be responsible for matching the needs of data insight with understanding of the available data. Data analysts work closely with customers to produce insight products including reports, dashboards and visualisations but also contribute to project understanding of existing data structures so that inputs and outputs are fully understood.");
        jobRole.setRoleName("Data Analyst - edited");
        jobRole.setResponsibility("You’ll be accountable for successful delivery of contemporary data solutions across multiple customers.");
        Mockito.when(roleFeaturesLevel.editJobRole(1,jobRole)).thenReturn(expectedResult);

        String result = roleFeaturesLevel.editJobRole(1,jobRole);

        assertEquals("Data updated successfully",result);
    }
    @Test
    public void editJobRole_shouldThrowSQLException_whenProvoked() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(roleFeaturesLevel.editJobRole(1,jobRole)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> roleFeaturesLevel.editJobRole(1,jobRole));
    }
}


