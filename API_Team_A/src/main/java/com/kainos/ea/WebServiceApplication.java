package com.kainos.ea;

import com.kainos.ea.resources.WebService;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;



public class WebServiceApplication extends Application<WebServiceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WebServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "WebService";
    }

    @Override
    public void initialize(final Bootstrap<WebServiceConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle() {
            @Override
            public void run(Object configuration, Environment environment) throws Exception {
                super.run((Configuration) configuration, environment);
            }

            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(Configuration configuration) {
                WebServiceConfiguration newConfig = (WebServiceConfiguration) configuration;
                return newConfig.getSwagger();
            }
        });
    }

    @Override
    public void run(final WebServiceConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new WebService());
    }


}
