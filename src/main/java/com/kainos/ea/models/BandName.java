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
public class BandName {

    private int bandID;
    private String bandName;
}