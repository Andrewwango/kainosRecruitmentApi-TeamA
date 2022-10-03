package com.kainos.ea;

import com.kainos.ea.models.JobRole;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@ExtendWith(DropwizardExtensionsSupport.class)

public class JobRoleIntegrationTest {

    static final DropwizardAppExtension<WebServiceConfiguration> APP = new DropwizardAppExtension<>(
            WebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getSpecification_shouldReturnSpecificationOfJobRole() {
        int id = 1;
        String response = APP.client().target("http://localhost:8080/api/job-specification/" + id)
                .request()
                .get(JobRole.class).getSpecification();

        Assertions.assertTrue(response != null);
    }
}