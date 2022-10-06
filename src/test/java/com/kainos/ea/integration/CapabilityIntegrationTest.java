package com.kainos.ea.integration;

import com.kainos.ea.WebServiceApplication;
import com.kainos.ea.WebServiceConfiguration;
import com.kainos.ea.models.Capability;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
class CapabilityIntegrationTest {

    static final DropwizardAppExtension<WebServiceConfiguration> APP = new DropwizardAppExtension<>(
            WebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getCapabilities_shouldReturnListOfCapabilities() {
        List<Capability> response = APP.client().target("http://localhost:8080/api/viewCapabilities")
                .request()
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    void getCapabilities_shouldReturnListOfCapabilitiesNames() {
        List<Capability> response = APP.client().target("http://localhost:8080/api/viewCapabilitiesNames")
                .request()
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);
    }
}