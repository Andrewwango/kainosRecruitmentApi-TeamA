package com.kainos.ea.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompetenciesTest {
    @Test
    public void checkCompetenciesObjectGetMethodsWork(){
        Competencies competencies = new Competencies("Effective meetings","Effectively manages diary, coming prepared for meetings and actively listen.");

        assertEquals(competencies.getCategory(),"Effective meetings");
        assertEquals(competencies.getDescription(),"Effectively manages diary, coming prepared for meetings and actively listen.");

    }

    @Test
    public void checkCompetenciesObjectSetterMethodsWork(){
        Competencies competencies = new Competencies("Effective meetings","Effectively manages diary, coming prepared for meetings and actively listen.");
        competencies.setCategory("Aware of the consequences of own behaviour and how this may affect others within");
        competencies.setDescription("Working within teams");

        assertEquals(competencies.getCategory(),"Aware of the consequences of own behaviour and how this may affect others within");
        assertEquals(competencies.getDescription(),"Working within teams");

    }
}
