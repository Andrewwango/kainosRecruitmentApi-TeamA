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
public class Training {
    private String bandName;
    private String trainingName;
    private String trainingDate;
    private String durationHours;
    private String registration;
}
