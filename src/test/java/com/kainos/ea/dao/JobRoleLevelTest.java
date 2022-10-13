package com.kainos.ea.dao;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import com.kainos.ea.models.JobRole;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JobRoleLevelTest {

  private JobRoleLevel jobRoleLevel;

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
}