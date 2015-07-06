package edu.oregonstate.mist.catalogapitest

import edu.oregonstate.mist.catalogapitest.resources.SampleResource

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment

import io.dropwizard.hibernate.HibernateBundle
import io.dropwizard.db.DataSourceFactory

class CatalogAPITestApplication extends Application<Configuration>{

    /* create managed database connection pool
        per http://dropwizard.io/manual/hibernate.html#configuration */
    private final HibernateBundle<CatalogAPITestApplicationConfiguration> hibernate =
            new HibernateBundle<CatalogAPITestApplicationConfiguration>(Course) {
                @Override
                public DataSourceFactory getDataSourceFactory(CatalogAPITestApplicationConfiguration configuration) {
                    return configuration.dataSourceFactory
                }
            }

    @Override
    public void initialize(Bootstrap<CatalogAPITestApplicationConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate)
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        environment.jersey().register(new SampleResource())
    }

    public static void main(String[] arguments) throws Exception {
        new CatalogAPITestApplication().run(arguments)
    }
}
