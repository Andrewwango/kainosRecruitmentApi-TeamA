package com.kainos.ea;

import com.kainos.ea.dao.*;
import com.kainos.ea.utils.DataBaseConnection;
import com.kainos.ea.resource.JobResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class WebServiceApplication extends Application<WebServiceConfiguration> {
    private static Connection conn;
    private JobRoleLevel jobRoleLevel;
    private BandLevel bandLevel;
    private CapabilityLevel capabilityLevel;
    private SpecificationLevel specificationLevel;
    private TrainingLevel trainingLevel;
    private CompetenciesLevel competenciesLevel;
    private GenderBiasLevel genderBiasLevel;

    private RoleFeaturesLevel roleFeaturesLevel;

    public static void main(final String[] args) throws Exception {
        new WebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "JobResource";
    }

    @Override
    public void initialize(final Bootstrap<WebServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<WebServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(WebServiceConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final WebServiceConfiguration configuration,
                    final Environment environment) {
        jobRoleLevel = new JobRoleLevel();
        bandLevel = new BandLevel();
        capabilityLevel = new CapabilityLevel();
        specificationLevel = new SpecificationLevel();
        trainingLevel = new TrainingLevel();
        competenciesLevel = new CompetenciesLevel();
        roleFeaturesLevel = new RoleFeaturesLevel();
        genderBiasLevel = new GenderBiasLevel();
      
        environment.jersey().register(new JobResource(roleFeaturesLevel,jobRoleLevel, bandLevel, capabilityLevel, specificationLevel, competenciesLevel, trainingLevel, genderBiasLevel));
      
        try {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            Connection con = dataBaseConnection.getConnection();
            Statement st = con.createStatement();
            st.execute("USE team_A");
        } catch (SQLException e) {
            e.printStackTrace(); // Bad practice alert!
        }
    }
}
