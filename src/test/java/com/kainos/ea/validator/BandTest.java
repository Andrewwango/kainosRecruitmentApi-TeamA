package com.kainos.ea.validator;

import com.kainos.ea.models.Band;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BandTest {
    @Test
    public void checkBandObjectGetMethodsWork(){
        Band band = new Band("Workday Consultant","Consultant");
        assertEquals(band.getBandName(),"Consultant");
        assertEquals(band.getRoleName(),"Workday Consultant");

    }

    @Test
    public void checkBandObjectSetterMethodsWork(){
        Band band = new Band("Workday Consultant","Consultant");
        band.setBandName("Associate");
        band.setRoleName("Workday Associate");
        assertEquals(band.getBandName(),"Associate");
        assertEquals(band.getRoleName(),"Workday Associate");

    }


}
