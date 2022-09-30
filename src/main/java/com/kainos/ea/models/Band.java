package com.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Band {
    private String bandName;
    private String roleName;

    public Band(@JsonProperty("roleName") String roleName, @JsonProperty("bandName") String bandName ){
        this.setBandName(bandName);
        this.setRoleName(roleName);
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandName(){
        return bandName;
    }

    public String getRoleName(){
        return roleName;
    }
}
