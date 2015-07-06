package edu.oregonstate.mist.catalogapitest

import edu.oregonstate.mist.catalogapitest.resources.SampleResource

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

//My imports
/*
import io.dropwizard.auth.basic.BasicAuthProvider
import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.db.DataSourceFactory
import io.dropwizard.jdbi.DBIFactory
import org.skife.jdbi.v2.DBI
*/

class CatalogAPITestApplication extends Application<Configuration>{

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {}

    @Override
    public void run(Configuration configuration, Environment environment) {
        environment.jersey().register(new SampleResource())
    }

    public static void main(String[] arguments) throws Exception {
        new CatalogAPITestApplication().run(arguments)
    }
}
