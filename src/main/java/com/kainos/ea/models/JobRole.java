package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRole {
    private int jobRoleId;
    private String roleName;
    private String specification;
    private String link;

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

    @JsonCreator
    public JobRole(
            @JsonProperty("roleName") String name,
            @JsonProperty("specification") String specification,
            @JsonProperty("link") String link)
    {
        this.setRoleName(name);
        this.setSpecification(specification);
        this.setLink(link);
    }
}