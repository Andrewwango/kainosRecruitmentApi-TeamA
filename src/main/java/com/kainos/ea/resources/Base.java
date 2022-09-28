package com.kainos.ea.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class base {
    private String bandName;
    private String roleName;

    public base (@JsonProperty("roleName") String roleName,@JsonProperty("bandName") String bandName ){
        this.setBandName(bandName);
        this.setRoleName(roleName);
    }

    private void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandName(){
        return bandName;
    }

    public String getRoleName(){
        return roleName;
    }





}
