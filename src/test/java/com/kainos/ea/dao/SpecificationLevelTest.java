package com.kainos.ea.dao;

import com.kainos.ea.models.JobRole;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

public class SpecificationLevelTest {
  private SpecificationLevel specificationLevel;
  @Test
  void jobRoleLevel_ReturnsJobRoleById_WhenCalledGetJobRole() throws SQLException {
    //Given
    int id = 1;
    specificationLevel = new SpecificationLevel();
    JobRole result;

    //When
    result = specificationLevel.getJobRole(id);

    //Then
    assertThat(result).isNotNull();
    assertFalse(result.getSpecification() == null);
    assertThat(result.getRoleName()).contains("Data Analyst");
  }
}