package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRole {
    private int jobRoleId;
    private String roleName;
    private String specification;
    private String link;
    private int bandID;
    private int capabilityID;

    public int getJobRoleId() {
        return jobRoleId;
    }

    public void setJobRoleId(int jobRoleId) {
        this.jobRoleId = jobRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getBandID() { return bandID; }

    public void setBandID(int bandID) { this.bandID = bandID; }

    public int getCapabilityID() { return capabilityID; }

    public void setCapabilityID(int capabilityID) { this.capabilityID = capabilityID; }

    @JsonCreator
    public JobRole(
            @JsonProperty("jobRoleID") int jobRoleID,
            @JsonProperty("roleName") String name,
            @JsonProperty("specification") String specification,
            @JsonProperty("link") String link,
            @JsonProperty("bandID") int bandID,
            @JsonProperty("capabilityID") int capabilityID)
    {
        this.setJobRoleId(jobRoleID);
        this.setRoleName(name);
        this.setSpecification(specification);
        this.setLink(link);
        this.setBandID(bandID);
        this.setCapabilityID(capabilityID);
    }
}