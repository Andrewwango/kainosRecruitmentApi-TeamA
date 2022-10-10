package com.kainos.ea.integration;


import com.kainos.ea.WebServiceApplication;
import com.kainos.ea.WebServiceConfiguration;
import com.kainos.ea.models.Band;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class BandIntegrationTest {
    static final DropwizardAppExtension<WebServiceConfiguration> APP = new DropwizardAppExtension<>(
            WebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getBandLevel_shouldReturnListofBand(){
        List<Band> response = APP.client().target("http://localhost:8080/api/viewBandLevel")
                .request()
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    void getBandLevelNames_shouldReturnListofBandNames(){
        List<Band> response = APP.client().target("http://localhost:8080/api/viewBandLevelNames")
                .request()
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);
    }
}
