package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@Builder
@Getter

public class BiasRequest {
    private String percentage_bias;
    private List<String> biased_words_male;
    private List<String> biased_words_female;

    public BiasRequest (@JsonProperty("percentage_bias") String percentage_bias, @JsonProperty("percentage_bias") List<String> biased_words_male, @JsonProperty("percentage_bias") List<String> biased_words_female) {
        this.percentage_bias = percentage_bias;
        this.biased_words_male = biased_words_male;
        this.biased_words_female = biased_words_female;
    }

}
