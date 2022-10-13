package com.kainos.ea.integration;

import com.kainos.ea.WebServiceApplication;
import com.kainos.ea.WebServiceConfiguration;
import com.kainos.ea.models.JobRole;
import com.kainos.ea.models.JobRoleWithoutLink;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class RoleFeatureIntegrationTest {
    static final DropwizardAppExtension<WebServiceConfiguration> APP = new DropwizardAppExtension<>(
            WebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    public void getCapabilities_shouldReturnListOfCapabilities() {
        try {
            JobRoleWithoutLink jobRole = new JobRoleWithoutLink();
            jobRole.setCapabilityID(1);
            jobRole.setBandID(1);
            jobRole.setRoleName("DataAnalyst");
            jobRole.setResponsibility("Responsibility");
            jobRole.setSpecification("Spec");
            Entity<?> entity = Entity.entity(jobRole, MediaType.APPLICATION_JSON_TYPE);

            String response = APP.client().target("http://localhost:8080/api/editJobRole/58")
                    .request()
                    .put(Entity.entity(jobRole, MediaType.APPLICATION_JSON_TYPE)).readEntity(String.class);

            Assertions.assertTrue(response.length() > 0);
            System.out.println(response.length());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void getCapabilities_shouldReturnError() {
        try {
            JobRoleWithoutLink jobRole = new JobRoleWithoutLink();
            jobRole.setCapabilityID(1);
            jobRole.setBandID(0);
            jobRole.setRoleName("DataAnalyst");
            jobRole.setResponsibility("Responsibility");
            jobRole.setSpecification("Spec");
            Entity<?> entity = Entity.entity(jobRole, MediaType.APPLICATION_JSON_TYPE);

            int response = APP.client().target("http://localhost:8080/api/editJobRole/58")
                    .request()
                    .put(entity).getStatus();

            Assertions.assertEquals(response, 500);
        }
        catch(Exception e){
        }
    }
    }