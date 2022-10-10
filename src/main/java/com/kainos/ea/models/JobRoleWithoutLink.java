package com.kainos.ea.models;
import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobRoleWithoutLink {
    private String roleName;
    private String specification;
    private String responsibility;
    private int bandID;
    private int capabilityID;
}
