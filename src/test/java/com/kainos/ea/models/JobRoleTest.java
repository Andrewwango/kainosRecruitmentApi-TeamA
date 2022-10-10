package com.kainos.ea.models;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JobRoleTest {

  @Test
  public void builderConstructorSetsCorrectValues() {
    JobRole jobRole = JobRole.builder()
        .jobRoleId(2000)
        .roleName("server admin")
        .specification("fixing servers")
        .link("https://linktospec")
        .bandID(5455)
        .capabilityID(123445)
            .responsibility("responsible")
        .build();

    assertEquals(2000, jobRole.getJobRoleId());
    assertEquals("server admin", jobRole.getRoleName());
    assertEquals("fixing servers", jobRole.getSpecification());
    assertEquals("https://linktospec", jobRole.getLink());
    assertEquals("responsible", jobRole.getResponsibility());
    assertEquals(5455, jobRole.getBandID());
    assertEquals(123445, jobRole.getCapabilityID());
  }
}