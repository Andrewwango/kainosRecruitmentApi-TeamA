package com.kainos.ea.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kainos.ea.models.Capability;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kainos.ea.models.JobRole;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JobRoleLevelTest {

  private JobRoleLevel jobRoleLevel;
  JobRoleLevel job = Mockito.mock(JobRoleLevel.class);
  DataBaseConnection databaseConnector = Mockito.mock(DataBaseConnection.class);
  Connection conn;
 

  @Test
  void jobRoleLevel_ReturnsListOfJobRoles_WhenCalled() throws SQLException {
    //Given
    jobRoleLevel = new JobRoleLevel();
    List<JobRole> result = new ArrayList<>();

    //When
    result = jobRoleLevel.getJobRoles();

    //Then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(20);
    assertThat(result).hasAtLeastOneElementOfType(JobRole.class);
  }

  @Test
  void jobRoleLevel_ReturnsListOfJobRolesByCapability_WhenCalledGetJobRolesByCapability() throws SQLException {
    //Given
    int id = 1;
    jobRoleLevel = new JobRoleLevel();
    List<JobRole> result;

    //When
    result = jobRoleLevel.getJobRolesByCapability(id);

    //Then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(9);
    assertThat(result).hasAtLeastOneElementOfType(JobRole.class);
  }

  @Test
  void deleteJobRoles_shouldThrowSQLException_whenProvoked() throws SQLException {
    Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
    List<String> exapmle = null;
    Mockito.when(job.deleteJobRoles(exapmle)).thenThrow(SQLException.class);

    assertThrows(SQLException.class,
            () -> job.deleteJobRoles(exapmle));
  }

  @Test
  void deleteJobRoles_shouldReturn200ResponseCodeWhenSuccessful() throws SQLException {
    List<String> exapmle = null;
    Response expectedResult = Response.status(200).build();
    Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
    Mockito.when(job.deleteJobRoles(exapmle)).thenReturn(expectedResult);

    Response result = job.deleteJobRoles(exapmle);

    assertEquals(result, expectedResult);
  }
}