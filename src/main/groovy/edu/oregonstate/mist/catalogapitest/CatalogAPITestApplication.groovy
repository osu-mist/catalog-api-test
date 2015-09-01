package edu.oregonstate.mist.catalogapitest

import edu.oregonstate.mist.catalogapitest.db.CourseDAO
import edu.oregonstate.mist.catalogapitest.db.InstructorDAO
import edu.oregonstate.mist.catalogapitest.resources.CourseResource
import edu.oregonstate.mist.catalogapitest.resources.InstructorResource
import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.jdbi.DBIFactory
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import org.skife.jdbi.v2.DBI

/**
 * Main application class.
 */
class CatalogAPITestApplication extends Application<CatalogAPITestApplicationConfiguration>{

    /**
     * Initializes the application bootstrap.
     *
     * @param bootstrap
     */
    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        //bootstrap.getObjectMapper().enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    }

    /**
     * Parses command-line arguments and runs the application.
     *
     * @param configuration
     * @param environment
     */
    @Override
    public void run(CatalogAPITestApplicationConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory()
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(),"jdbi")

        final CourseDAO courseDAO = jdbi.onDemand(CourseDAO.class)
        final InstructorDAO instructorDAO = jdbi.onDemand(InstructorDAO.class)

        environment.jersey().register(new CourseResource(courseDAO))
        environment.jersey().register(new InstructorResource(instructorDAO))
    }

    /**
     * Instantiates the application class with command-line arguments.
     *
     * @param arguments
     * @throws Exception
     */
    public static void main(String[] arguments) throws Exception {
        new CatalogAPITestApplication().run(arguments)
    }
}
