package com.kainos.ea.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobRoleWithoutLinkTest {

    @Test
    public void builderConstructorSetsCorrectValues() {
        JobRole jobRole = JobRole.builder()
                .roleName("server admin")
                .specification("fixing servers")
                .responsibility("responsible")
                .bandID(5455)
                .capabilityID(123445)
                .build();

        assertEquals("server admin", jobRole.getRoleName());
        assertEquals("fixing servers", jobRole.getSpecification());
        assertEquals("responsible", jobRole.getResponsibility());
        assertEquals(5455, jobRole.getBandID());
        assertEquals(123445, jobRole.getCapabilityID());
    }
}
