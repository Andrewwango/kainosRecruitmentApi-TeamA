package com.kainos.ea.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {

    @Test
    public void builderConstructorSetsCorrectValues() {
        Training training = Training.builder()
                .bandName("associate")
                .trainingName("Enhancing your Communication Skills")
                .trainingDate("2022-10-17")
                .durationHours("3")
                .registration("Please complete a JIRA request")
                .build();

        assertEquals("associate", training.getBandName());
        assertEquals("Enhancing your Communication Skills", training.getTrainingName());
        assertEquals("2022-10-17", training.getTrainingDate());
        assertEquals("3", training.getDurationHours());
        assertEquals("Please complete a JIRA request", training.getRegistration());
    }
}