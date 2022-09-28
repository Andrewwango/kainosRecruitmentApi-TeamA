package com.kainos.ea;
import com.kainos.ea.database.DataBaseConnection;
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
    public static void main(final String[] args) throws Exception {
        new WebServiceApplication().run(args);
    }
    @Override
    public String getName() {
        return "WebService";
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
        environment.jersey().register(new WebService());
        try {
            Connection con = DataBaseConnection.getConnection();
            Statement st = con.createStatement();
            st.execute("USE team_A");
        } catch (SQLException e) {
            e.printStackTrace(); // Bad practice alert!
        }
    }
}
