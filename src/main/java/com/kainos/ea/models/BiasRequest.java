package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
@Builder
@Getter
@AllArgsConstructor

public class BiasRequest {
    private String percentage_bias;
    private List<String> biased_words_male;
    private List<String> biased_words_female;

}
