package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Capability {
    private String capabilityName;
    private String roleName;

    public Capability (@JsonProperty("roleName") String roleName, @JsonProperty("capabilityName") String capabilityName ){
        this.setCapabilityName(capabilityName);
        this.setRoleName(roleName);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public void setCapabilityName(String capabilityName) {
        this.capabilityName = capabilityName;
    }
    @Override
    public String toString() {
        return new String("Job role: " + this.roleName + ", Capability: " + this.capabilityName);
    }
}
