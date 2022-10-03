package com.kainos.ea.dao;

import com.kainos.ea.models.JobRole;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

@ExtendWith(MockitoExtension.class)
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