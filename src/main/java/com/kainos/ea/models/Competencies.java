package com.kainos.ea.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Builder
@Getter
public class Competencies {
    private String category;
    private String description;
}
