package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class JobRole {
    private int jobRoleId;
    private String roleName;
    private String specification;
    private String link;
    private int bandID;
    private int capabilityID;
}