package edu.oregonstate.mist.catalogapitest

import com.fasterxml.jackson.databind.SerializationFeature

import edu.oregonstate.mist.catalogapitest.db.CourseDAO
import edu.oregonstate.mist.catalogapitest.resources.CourseResource

import edu.oregonstate.mist.catalogapitest.resources.SampleResource

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

class CatalogAPITestApplication extends Application<CatalogAPITestApplicationConfiguration>{

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        //bootstrap.getObjectMapper().enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    }

    @Override
    public void run(CatalogAPITestApplicationConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory()
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(),"jdbi")
        final CourseDAO theDAO = jdbi.onDemand(CourseDAO.class)

        environment.jersey().register(new SampleResource())
        environment.jersey().register(new CourseResource(theDAO) {})
    }

    public static void main(String[] arguments) throws Exception {
        new CatalogAPITestApplication().run(arguments);
    }
}
