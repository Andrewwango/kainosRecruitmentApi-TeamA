package com.kainos.ea.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CapabilityTest {

    @Test
    public void builderConstructorSetsCorrectValues() {
        Capability capability = Capability.builder()
                .capabilityID(1)
                .capabilityName("Trainee")
                .roleName("server admin")
                .build();

        assertEquals(1, capability.getCapabilityID());
        assertEquals("Trainee", capability.getCapabilityName());
        assertEquals("server admin", capability.getRoleName());
    }
}