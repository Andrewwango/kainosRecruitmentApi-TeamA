package com.kainos.ea.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CapabilityTest {

    @Test
    public void builderConstructorSetsCorrectValues() {
        Capability capability = Capability.builder()
                .capabilityName("Trainee")
                .roleName("server admin")
                .build();

        assertEquals("Trainee", capability.getCapabilityName());
        assertEquals("server admin", capability.getRoleName());
    }
}