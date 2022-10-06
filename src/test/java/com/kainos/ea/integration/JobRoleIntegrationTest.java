package com.kainos.ea.integration;

import com.kainos.ea.WebServiceApplication;
import com.kainos.ea.WebServiceConfiguration;
import com.kainos.ea.models.JobRole;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobRoleIntegrationTest {

    static final DropwizardAppExtension<WebServiceConfiguration> APP = new DropwizardAppExtension<>(
            WebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getSpecification_shouldReturnSpecificationOfJobRole() {
        int id = 1;
        JobRole response = APP.client().target("http://localhost:8080/api/job-specification/" + id)
                .request()
                .get(JobRole.class);

        Assertions.assertTrue(response != null);
    }

    @Test
    void getJobRolesByCapability_shouldReturnListOfJobRolesByCapability() {
        int capabilityId = 1;
        List<JobRole> response = APP.client().target("http://localhost:8080/api/job-roles-by-capability/" + capabilityId)
                .request()
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);

    }
}