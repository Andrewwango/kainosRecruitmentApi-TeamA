package com.kainos.ea.models;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BiasRequestTest {

  @Test
  public void builderConstructorSetsCorrectValues() {
    List<String> male = new ArrayList<>();
    male.add("he");
    List<String> female = new ArrayList<>();
    female.add("she");
    BiasRequest biasRequest = BiasRequest.builder()
        .percentage_bias("33.3%")
        .biased_words_male(male)
        .biased_words_female(female)
        .build();

    assertEquals("33.3%", biasRequest.getPercentage_bias());
    assertEquals(male, biasRequest.getBiased_words_male());
    assertEquals(female, biasRequest.getBiased_words_female());
  }
}