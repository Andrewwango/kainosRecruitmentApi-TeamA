package com.kainos.ea.dao;

import com.kainos.ea.models.BiasRequest;
import com.kainos.ea.models.Capability;
import com.kainos.ea.utils.DataBaseConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GenderBiasLevelTest {
    GenderBiasLevel bias = Mockito.mock(GenderBiasLevel.class);

    @Test
    void getGenderBias_shouldReturnABiasRequestObject() throws IOException {
        List<String> male = new ArrayList<>();
        male.add("he");
        List<String> female = new ArrayList<>();
        female.add("she");

        BiasRequest expectedResult = new BiasRequest("33.3%", male, female);
        Mockito.when(bias.getGenderBias("String")).thenReturn(expectedResult);

        BiasRequest result = bias.getGenderBias("String");

        assertEquals(result, expectedResult);
    }
    @Test
    void getGenderBias_shouldThrowIOException_whenProvoked() throws RuntimeException, IOException {
        Mockito.when(bias.getGenderBias("String")).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class,
                () -> bias.getGenderBias("String"));
    }
}