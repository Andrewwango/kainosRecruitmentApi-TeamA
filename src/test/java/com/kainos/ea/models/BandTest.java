package com.kainos.ea.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BandTest {
    @Test
    public void checkBandObjectGetMethodsWork(){
        Band band = new Band(1,"Consultant","Workday Consultant");

        assertEquals(band.getBandName(),"Consultant");
        assertEquals(band.getRoleName(),"Workday Consultant");

    }

    @Test
    public void checkBandObjectSetterMethodsWork(){
        Band band = new Band(1, "Consultant","Workday Consultant");
        band.setBandName("Associate");
        band.setRoleName("Workday Associate");

        assertEquals(band.getBandName(),"Associate");
        assertEquals(band.getRoleName(),"Workday Associate");

    }
}
